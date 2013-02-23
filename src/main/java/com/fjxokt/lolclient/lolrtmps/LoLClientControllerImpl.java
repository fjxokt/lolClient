package com.fjxokt.lolclient.lolrtmps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.BroadcastNotification;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.ClientLoginKickNotification;
import com.fjxokt.lolclient.lolrtmps.model.ClientSystemStatesNotification;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.GameNotification;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.StoreAccountBalanceNotification;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerCredentialsDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ChampionTradeMessageType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameNotificationType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.model.utils.TerminatedCondition;
import com.fjxokt.lolclient.lolrtmps.services.GameService;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

abstract class LoLClientControllerImpl implements LoLClientController {
	
	protected LoLRTMPSClient client;
	private Queue<TypedObject> queue;
	private GameState status;
	private List<ClientListener> gameListeners;
	private List<ClientListener> addQueueListeners;
	private List<ClientListener> remQueueListeners;
	private volatile boolean isNotifying;
	private ClientEventType lastClientEventSent;	
	protected GameDTO game;
        
        
	public LoLClientControllerImpl() {
                gameListeners = new ArrayList<ClientListener>();
                addQueueListeners = new ArrayList<ClientListener>();
	        remQueueListeners = new ArrayList<ClientListener>();
		queue = new LinkedList<TypedObject>();
		setState(GameState.DISCONNECTED);
		game = null;
	}
        
        public LoLRTMPSClient getRTMPSClient(){
            return client;
        }
	
	public void addGameListener(ClientListener l) {
		if (isNotifying) {
			addQueueListeners.add(l);
		}
		else {
			if (!gameListeners.contains(l)) {
				gameListeners.add(l);
			}
		}
	}
	
	public void removeGameListener(ClientListener l) {
		if (isNotifying) {
			remQueueListeners.add(l);
		}
		else {
			gameListeners.remove(l);
		}
	}
	
	public void notifyGameUpdated(GameDTO game, ClientEventType state) {
		notifyGameUpdated(game, state, null, null, null, null);
	}
	
	public void notifyClientUpdate(ClientEventType state, SearchingForMatchNotification search) {
		notifyGameUpdated(game, state, null, search, null, null);
	}
	
	public void notifyClientUpdate(ClientEventType state) {
		notifyGameUpdated(null, state, null, null, null, null);
	}
	
	public void notifyClientUpdate(ClientEventType state, StoreAccountBalanceNotification balance) {
		notifyGameUpdated(null, state, null, null, null, balance);
	}
	
	public void notifyClientUpdate(ClientEventType state, ChampionTradeMessage msg) {
		notifyGameUpdated(null, state, null, null, msg, null);
	}
	
	public void notifyGameUpdated(GameDTO game, ClientEventType state, EndOfGameStats stats, 
			SearchingForMatchNotification search, ChampionTradeMessage msg,
			StoreAccountBalanceNotification balance) {
		lastClientEventSent = state;
		if (!isNotifying) {
			isNotifying = true;
			ClientEvent e = new ClientEvent(game, state, stats, search, msg, balance);
			Iterator<ClientListener> it = gameListeners.iterator();
			while (it.hasNext()) {
				ClientListener cur = it.next();
				if (cur != null) {
					cur.clientStateUpdated(e);
				}
				else {
					System.out.println("Removing a null ClientListener");
					it.remove();
				}
			}
			isNotifying = false;
		}
		updateListener();
	}
	
	private void updateListener() {
		for (ClientListener l : remQueueListeners) {
			gameListeners.remove(l);
		}
		remQueueListeners.clear();
		for (ClientListener l : addQueueListeners) {
			if (!gameListeners.contains(l)) {
				gameListeners.add(l);
			}
		}
		addQueueListeners.clear();
	}
	
	public ClientEventType getLastClientEventSent() {
		return lastClientEventSent;
	}
	
	public void initUser(String region, String clientVersion, String user, String pass) {
		client = new LoLRTMPSClient(region, clientVersion, user, pass);
		client.setReceiveHandler(this);
	}
	
	public String getUser() {
		return client.getUser();
	}
	
	public String getPassword() {
		return client.getPassword();
	}
        
	/////////////
	// CALLBACK
	/////////////
	
	private void goToChampSelectState() {
		System.out.println("Joining champ selection room");
		setState(GameState.CHAMP_SELECT);
		
		ResultMessage res = GameService.setClientReceivedGameMessage(client, game.getId(), GameState.CHAMP_SELECT_CLIENT);
		System.out.println("state1: " + res.getMessage());

		res = GameService.getLatestGameTimerState(client, game.getId(), GameState.CHAMP_SELECT, game.getGameTypeConfigId());
		System.out.println("state2: " + res.getMessage());
		
		// change chat
		MessagingManager.getInst().joinRoom(game);
				
		notifyGameUpdated(game, ClientEventType.JOINING_CHAMP_SELECT);
	}

	public void callback(TypedObject result) {
		System.out.println(result);
		queue.add(result);
		
		String type = result.getTO("data").getTO("body").type;
		
		////////////////////////////////////////////////////////////////////////////////
		// GameDTO Object
		////////////////////////////////////////////////////////////////////////////////
		if (type.equals(GameDTO.getTypeClass())) {//GAME DTO START
			
			//TypedObject r = queue.poll();
			if (result.getTO("data").getTO("body") == null) {
				System.out.println("BIG ERRRRRRRRRRRROOORRRR");
			}
			// used to know if team switch of join/left
			//int oldNbPlayers = (game == null) ? 0 : game.getNumPlayers();
			// update our game object
			game = new GameDTO(result.getTO("data").getTO("body"));
			// get the new game state
			GameState newState = GameState.getStateFromString(game.getGameState());
			
                        switch(status){
                            case IDLE:
                                //IDLE START
                                switch(newState){
                                    case TEAM_SELECT:
                                        System.out.println("from status IDLE to JOINING_TEAM_SELECT");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
                                        break;
                                    case JOINING_CHAMP_SELECT:
                                        setState(GameState.JOINING_CHAMP_SELECT);
					notifyGameUpdated(game, ClientEventType.JOINING_MATCHMAKING);
                                        break;
                                }
                                break; //IDLE END
                            case TEAM_SELECT:
                                //TEAM_SELECT START
                                switch(newState){
                                    case TEAM_SELECT:
                                        System.out.println("from status IDLE to JOINING_TEAM_SELECT");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
                                        break;
                                    case CHAMP_SELECT:
                                        goToChampSelectState();
                                        break;
                                    case TERMINATED:
                                        TerminatedCondition reason = TerminatedCondition.getStateFromString(game.getTerminatedCondition());
					System.out.println("Game terminated: reason: " + reason);
					setState(GameState.IDLE);
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
                                        break;
                                }
                                break;//TEAM_SELECT END
                            case JOINING_CHAMP_SELECT:
                                //JOINING_CHAMP_SELECT START
                                switch(newState){
                                    case JOINING_CHAMP_SELECT:
                                        System.out.println("update status of participants: " + game.getStatusOfParticipants());
					notifyGameUpdated(game, ClientEventType.MATCHMAKING_UPDATE);
                                        break;
                                    case CHAMP_SELECT:
                                        goToChampSelectState();
                                        break;
                                    case TERMINATED:
                                        TerminatedCondition reason = TerminatedCondition.getStateFromString(game.getTerminatedCondition());
					System.out.println("Game terminated: reason: " + reason);
					setState(GameState.IDLE);
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
                                        break;                                        
                                }
                                break;//JOINING_CHAMP_SELECT END
                            case CHAMP_SELECT://CHAMP_SELECT START
                                
                                switch(newState){
                                    case TEAM_SELECT:
                                        System.out.println("Someone left the champ selection room, back to team select");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.RETURNING_TEAM_SELECT);
                                        break;
                                    case CHAMP_SELECT:
                                        System.out.println("Someone picking a champ ???");
					notifyGameUpdated(game, ClientEventType.CHAMP_SELECT_UPDATE);
                                        break;
                                    case POST_CHAMP_SELECT:
                                        System.out.println("Champions have been locked, waiting for the go...");
					setState(newState);
					notifyGameUpdated(game, ClientEventType.JOINING_POST_CHAMPION_SELECT);
                                        break;
                                }
                                
                                break;//CHAMP_SELECT START END
                                
                            case POST_CHAMP_SELECT://POST_CHAMP_SELECT START
                                switch(newState){
                                    case TEAM_SELECT:
                                        System.out.println("Someone left the champ selection room, back to team select");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.RETURNING_TEAM_SELECT);
                                        break;
                                    case START_REQUESTED:
                                        System.out.println("start requested, time to send our last messages...");
					setState(GameState.START_REQUESTED);
					// we wait for the credentials to be send
                                        break;
                                    default:
                                        // what other status can we get ?
					// apparently its just if people change their summoner spells
					System.out.println("CHECK THIS PART IF I GET DISPLAYED !");
					notifyGameUpdated(game, ClientEventType.POST_CHAMPION_SELECT);
                                        break;
                                }
                                break;//POST_CHAMP_SELECT END
                            case GameClientConnectedToServer://Intentional Double case
                            case GAME_IN_PROGRESS://GAME_IN_PROGRESS START
                                GameState state = GameState.getStateFromString(game.getGameState());
                                
                                switch(state){
                                    case TERMINATED:
                                        // game over, we will receive stats message (ok not ?? don't think so)
					setState(GameState.POST_GAME);
                                        break;
                                    default:
                                        // read the readon in gamedto object
					TerminatedCondition reason = TerminatedCondition.getStateFromString(game.getTerminatedCondition());
					System.out.println("Game ended: reason: " + game.getTerminatedCondition() + " : " + reason);
					// TODO: apparently its whem someone leave riiiiight before it's too late, show go back in CHAMP_SELECT
					// but not sure: what about if I was using matchmaking ???
					// should i go back in queue from here ?
					// now i'll just notify back to normal
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
                                        break;
                                }
                                break;//GAME_IN_PROGRESS END
                        }
		}//GAME DTO END
		////////////////////////////////////////////////////////////////////////////////
		// GameNotification object
		////////////////////////////////////////////////////////////////////////////////
		// TODO: handle that!
		else if (type.equals(GameNotification.getTypeClass())) {
			GameNotification notif = new GameNotification(result.getTO("data").getTO("body"));
			// get the type of notif
			GameNotificationType notifType = GameNotificationType.getStateFromString(notif.getType());
			// if we were in team select
                        
                        switch(status){
                            case TEAM_SELECT:
                                if (notifType.equals(GameNotificationType.PLAYER_BANNED_FROM_GAME)) {
					System.out.println("you have been banned from game, return to idle state...");
					// leave chat
					MessagingManager.getInst().leaveRoom(game);
					// update state
					setState(GameState.IDLE);
					notifyClientUpdate(ClientEventType.BANNED_FROM_GAME);
				}
                                break;
                            case IDLE:
                                // someone left the team invite, means the searching for match is over
				if (notifType.equals(GameNotificationType.PLAYER_QUIT)) {
					System.out.println("Someone left the team, back to idle. TODOOO!!!");
					// TODO
				}
                                break;
                        }
		}
		////////////////////////////////////////////////////////////////////////////////
		// SearchingForMatchNotification object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(SearchingForMatchNotification.getTypeClass())) {
			// get the notif
			SearchingForMatchNotification notif = new SearchingForMatchNotification(result.getTO("data").getTO("body"));
			notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, notif);
		}
		////////////////////////////////////////////////////////////////////////////////
		// ChampionTradeMessage object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(ChampionTradeMessage.getTypeClass())) {
			ChampionTradeMessage msg = new ChampionTradeMessage(result.getTO("data").getTO("body"));
			ChampionTradeMessageType msgType = ChampionTradeMessageType.getTypeFromString(msg.getChampionTradeMessageType());
                        
                        switch(msgType){
                            case CHAMPION_TRADE_REQUEST:
                                notifyClientUpdate(ClientEventType.TRADE_CHAMP_REQUEST, msg);
                                break;
                            case CHAMPION_TRADE_ACCEPT:
                                // TODO: call tradeChampion with the name of the player we accepted
				GameService.tradeChampion(client, msg.getSenderInternalSummonerName());
                                break;
                            case CHAMPION_TRADE_CANCELLED:
                                notifyClientUpdate(ClientEventType.TRADE_CHAMP_CANCEL, msg);
                                break;
                            case CHAMPION_TRADE_NOT_ALLOWED:
                                notifyClientUpdate(ClientEventType.TRADE_CHAMP_NOT_ALLOWED, msg);
                                break;
                        }
		}
		////////////////////////////////////////////////////////////////////////////////
		// PlayerCredentialsDTO Object (received when the game is ready to go)
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(PlayerCredentialsDTO.getTypeClass())) {
			// get the notif object
			PlayerCredentialsDTO credentials = new PlayerCredentialsDTO(result.getTO("data").getTO("body"));
			// we have everything we need, contact game and maestro
			System.out.println(credentials);
			
			// send our two messages (check if correct)
			if (GameService.setClientReceivedGameMessage(client, game.getId(), GameState.GAME_START_CLIENT).equals(ResultMessage.OK)) {
				setState(GameState.GAME_START_CLIENT);
				if (GameService.setClientReceivedGameMessage(client, game.getId(), GameState.GameClientConnectedToServer).equals(ResultMessage.OK)) {
					setState(GameState.GameClientConnectedToServer);
					notifyGameUpdated(game, ClientEventType.GAME_CLIENT_CONNECTED_TO_SERVER);
				}
			}
			
			// leave the chat, it's time to play now
			MessagingManager.getInst().leaveRoom(game);

			// we can now start the game
			// we will receive a GameDTO notif when game end or crash
		}
		////////////////////////////////////////////////////////////////////////////////
		// PlatformGameLifecycleDTO Object - When do we get this one ???
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(PlatformGameLifecycleDTO.getTypeClass())) {
			System.out.println("FIX MEEEEEEEEEEEEEEEEEEEEEE !!!!!!");
			// reconnect possible
			PlatformGameLifecycleDTO gameLifeCycle = new PlatformGameLifecycleDTO(result.getTO("data").getTO("body"));
			// set game
			game = gameLifeCycle.getGame();
			// status
			setState(GameState.GAME_IN_PROGRESS);
			notifyGameUpdated(gameLifeCycle.getGame(), ClientEventType.GAME_IN_PROGRESS);
			// call required methods
			// TODO: all this should probably go in a reconnectToGame() method
			GameService.setClientReceivedGameMessage(client, gameLifeCycle.getGame().getId(), GameState.GAME_START_CLIENT);
			GameService.setClientReceivedMaestroMessage(client, gameLifeCycle.getGame().getId(), GameState.GameReconnect);
			GameService.setClientReceivedMaestroMessage(client, gameLifeCycle.getGame().getId(), GameState.GameClientConnectedToServer);
		}
		////////////////////////////////////////////////////////////////////////////////
		// EndOfGameStats Object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(EndOfGameStats.getTypeClass())) {
			// get out stats
			EndOfGameStats stats = new EndOfGameStats(result.getTO("data").getTO("body"));
			System.out.println(stats);
			setState(GameState.IDLE);
			notifyGameUpdated(null, ClientEventType.END_OF_GAME_STATS, stats, null, null, null);
		}
		////////////////////////////////////////////////////////////////////////////////
		// StoreAccountBalanceNotification object (after playing a game or buying a config for instance)
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(StoreAccountBalanceNotification.getTypeClass())) {
			StoreAccountBalanceNotification balance = new StoreAccountBalanceNotification(result.getTO("data").getTO("body"));
			notifyClientUpdate(ClientEventType.BALANCE_NOTIF, balance);
		}
		////////////////////////////////////////////////////////////////////////////////
		// ClientLoginKickNotification Object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(ClientLoginKickNotification.getTypeClass())) {
			System.out.println("You have been kicked, need to relogin");
			// TODO: wrong state, need to exit the lobby
			setState(GameState.DISCONNECTED);
			notifyClientUpdate(ClientEventType.LOGGED_OUT);
		}
		////////////////////////////////////////////////////////////////////////////////
		// BroadcastNotification Object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(BroadcastNotification.getTypeClass())) {
			BroadcastNotification notif = new BroadcastNotification(result.getTO("data").getTO("body"));
			System.out.println("JUST RECEIVED A NOTIFICATION!!: " + notif.getJson());
			// TODO:
			/*
			 * "Json": "{\"broadcastMessages\":[
			 * {\"id\":0,
			 * \"active\":true,
			 * \"content\":\"We are investigating possible games not starting again. See Service Status Forum for updates.\",
			 * \"messageKey\":\"generic\",
			 * \"severity\":\"info\"}]}"
			 */
		}
		////////////////////////////////////////////////////////////////////////////////
		// ClientSystemStatsNotification Object
		////////////////////////////////////////////////////////////////////////////////
		else if (type.equals(ClientSystemStatesNotification.getTypeClass())) {
			// TODO: we receive the objet as a json object
			/*
			 * "Json": "{\"championTradeThroughLCDS\":true,\"practiceGameEnabled\":true,\"advancedTutorialEnabled\":true,\"minNumPlayersForPracticeGame\":1,\"practiceGameTypeConfigIdList\":[1,2,4,6],\"freeToPlayChampionIdList\":[15,23,36,42,55,57,90,92,115,117],\"inactiveChampionIdList\":[],\"inactiveSpellIdList\":[5,9,16,20],\"inactiveTutorialSpellIdList\":[17,20],\"inactiveClassicSpellIdList\":[17],\"inactiveOdinSpellIdList\":[5,9,12],\"inactiveAramSpellIdList\":[2,5,9,10,11,12,17],\"enabledQueueIdsList\":[16,52,7,17,8,25,2,14],\"unobtainableChampionSkinIDList\":[1001,1002,1007,4001,6002,7003,8003,9002,9004,10001,10003,10005,12001,12003,13001,13004,13005,13006,14001,15002,15005,17001,17003,18002,18006,19001,19002,19005,20001,20002,21004,23001,24001,24003,24004,26001,26004,27001,28002,29001,29002,30001,31001,32001,32002,32004,33001,34001,35003,35004,36001,36002,36004,37003,38001,41004,42001,42002,44001,45002,51003,53001,53002,53005,54001,55002,55004,56004,57003,74004,76001,76002,76005,78001,78002,78003,78004,79001,79003,81001,81002,82001,84002,84003,85002,86002,89003,90001,96002,96004,96007,98001,98002,104003],\"archivedStatsEnabled\":true,\"queueThrottleDTO\":{\"generic\":false,\"mode\":\"\",\"queueId\":-1,\"level\":-1},\"gameMapEnabledDTOList\":[{\"gameMapId\":7,\"minPlayers\":1},{\"gameMapId\":8,\"minPlayers\":1},{\"gameMapId\":1,\"minPlayers\":1},{\"gameMapId\":10,\"minPlayers\":1}],\"storeCustomerEnabled\":true,\"socialIntegrationEnabled\":false,\"runeUniquePerSpellBook\":false,\"tribunalEnabled\":true,\"observerModeEnabled\":true,\"spectatorSlotLimit\":2,\"clientHeartBeatRateSeconds\":120,\"observableGameModes\":[\"NORMAL\",\"ODIN_UNRANKED\",\"NORMAL_3x3\",\"CAP_1x1\",\"CAP_5x5\",\"ARAM_UNRANKED_1x1\",\"ARAM_UNRANKED_2x2\",\"ARAM_UNRANKED_3x3\",\"ARAM_UNRANKED_5x5\",\"ARAM_UNRANKED_6x6\",\"RANKED_SOLO_3x3\",\"RANKED_SOLO_5x5\",\"RANKED_PREMADE_3x3\",\"RANKED_PREMADE_5x5\",\"RANKED_SOLO_1x1\",\"ODIN_UNRANKED\",\"ODIN_RANKED_SOLO\",\"ODIN_RANKED_TEAM\",\"RANKED_TEAM_3x3\",\"RANKED_TEAM_5x5\",\"NORMAL_3x3\"],\"observableCustomGameModes\":\"ALL\",\"teamServiceEnabled\":true,\"leagueServiceEnabled\":true,\"modularGameModeEnabled\":false,\"riotDataServiceDataSendProbability\":1.0,\"displayPromoGamesPlayedEnabled\":false,\"masteryPageOnServer\":false,\"maxMasteryPagesOnServer\":20,\"tournamentSendStatsEnabled\":true,\"replayServiceAddress\":\"\",\"kudosEnabled\":true,\"buddyNotesEnabled\":true,\"localeSpecificChatRoomsEnabled\":false,\"replaySystemStates\":{\"replayServiceEnabled\":false,\"replayServiceAddress\":\"\",\"endOfGameEnabled\":true,\"matchHistoryEnabled\":true,\"getReplaysTabEnabled\":true}}"
              },
			 */
		}
	}
	
	public void setState(GameState newState) {
		System.out.println("going from state " + status + " to " + newState);
		status = newState;
	}
        
        public TypedObject pollQueue(String typeClass){
            
            if(typeClass == null){
                return null;
            }
            
            TypedObject queueTo = queue.poll();
            
            
            while(true){
                        TypedObject queueBody = queueTo.getTO("data").getTO("body");
                        // if it's not a GameDTO object, we keep waiting
                        if (queueBody.type.equals(typeClass)) {
                                return queueBody;
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            System.out.println("Sleep interrupted");
                        }
                }
        }
        
        public void setGame(GameDTO game){
            this.game = game;
        }
        
        public GameDTO getGame(){
            return game;
        }
	
}