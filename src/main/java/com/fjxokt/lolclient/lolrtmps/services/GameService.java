package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.LoLClientController;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.exceptions.PlayerAlreadyInGameException;
import com.fjxokt.lolclient.lolrtmps.model.BotParticipant;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameConfig;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.StartChampSelectDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class GameService {
    
        public static PlatformGameLifecycleDTO retrieveInProgressSpectatorGameInfo(LoLRTMPSClient client, String summonerName) {
            
                TypedObject progressTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "retrieveInProgressSpectatorGameInfo", new Object[]{ summonerName });
                
                if (progressTo == null) {
                        return null;
                }
                
                return new PlatformGameLifecycleDTO(progressTo);
	}
	
	public static ResultMessage acceptPoppedGame(LoLRTMPSClient client, Boolean answer) {
            
                boolean accepted = LoLClientService.executeService(client, "gameService", "acceptPoppedGame", new Object[] { answer });
                
                if(accepted){
                    return ResultMessage.OK;
                } else {
                    return ResultMessage.ERROR;
                }
	}

	public static List<PracticeGameSearchResult> listAllPracticeGames(LoLRTMPSClient client) {
            
                Object[] gamesList = LoLClientService.getServiceResponseDataBodyArray(client, "gameService", "listAllPracticeGames", new Object[]{});
		
                if(gamesList == null){
                    return null;
                }
                
                List<Object> gamesAsObjects = Arrays.asList(gamesList);
                List<PracticeGameSearchResult> gamesAsSearchResults = new ArrayList<PracticeGameSearchResult>(gamesAsObjects.size());
                
                for(Object o : gamesAsObjects){
                    TypedObject game = (TypedObject)o;
                    if(game != null){
                        gamesAsSearchResults.add(new PracticeGameSearchResult(game));
                    }
                }
                
                return gamesAsSearchResults;
	}
	
	public static ResultMessage joinGame(LoLClientController controller, Double gameId, String pwd) {           
            
                controller.setState(GameState.WAITING);
                TypedObject gameJoinTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "gameService", "joinGame", new Object[]{ gameId, pwd });
                
                if(gameJoinTo == null){
                    return ResultMessage.ERROR;
                }
                
                while(true){
                    TypedObject queueTo = controller.pollQueue(GameDTO.getTypeClass());
                    GameDTO gameDTO = new GameDTO(queueTo);
                    if (gameDTO.getId().equals(gameId)) {
                        controller.setGame(gameDTO);
                        System.out.println("We got our game object!");
                        MessagingManager.getInst().joinRoom(gameDTO); 
                        // TODO: connect chat room
                        break;
                    }
                }
                
                // update state
                controller.setState(GameState.TEAM_SELECT);
                controller.notifyGameUpdated(controller.getGame(), ClientEventType.JOINING_TEAM_SELECT);
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage switchPlayerToObserver(LoLRTMPSClient client, Double gameId) {
                Object switchResult = LoLClientService.getServiceResponseDataBody(client, "gameService", "switchPlayerToObserver", new Object[]{ gameId });
                
                if (switchResult == null) {
                        return ResultMessage.ERROR;
                }
                
                Boolean didSwitch = (Boolean) switchResult;
                
                if(didSwitch){
                    return ResultMessage.OK;
                }
                else{
                    return ResultMessage.ERROR;
                }
	}
	
	// whatTeam = 100 for team one and 200 for team two
	public static ResultMessage switchObserverToPlayer(LoLRTMPSClient client, Double gameId, Integer whatTeam) {
            
                Object switchResult = LoLClientService.getServiceResponseDataBody(client, "gameService", "switchObserverToPlayer", new Object[]{ gameId, whatTeam });
                
                if (switchResult == null) {
                        return ResultMessage.ERROR;
                }
                
                Boolean didSwitch = (Boolean) switchResult;
                
                if(didSwitch){
                    return ResultMessage.OK;
                }
                else{
                    return ResultMessage.ERROR;
                }
	}
	
	public static GameDTO observeGame(LoLClientController controller, Double gameId) {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "gameService", "observeGame", new Object[]{ gameId, null });
                
                if (observeTo == null) {
                        return null;
                }
                
                // get our game object
		controller.setGame(new GameDTO(observeTo));
                
                // TODO: connect chat room
		MessagingManager.getInst().joinRoom(controller.getGame());
                
                // update state
                controller.setState(GameState.TEAM_SELECT);
                controller.notifyGameUpdated(controller.getGame(), ClientEventType.JOINING_TEAM_SELECT);
                
                return controller.getGame(); 
	}
	
	public static ResultMessage quitGame(LoLClientController controller) {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "gameService", "quitGame", new Object[]{});
                
                if (observeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                // get our game object
		controller.setGame(new GameDTO(observeTo));
                
                // TODO: connect chat room
		MessagingManager.getInst().leaveRoom(controller.getGame());
                
                // update state
                System.out.println("quit ok");
                controller.setState(GameState.IDLE);
                controller.notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
                
                return ResultMessage.OK; 
	}
	
	public static ResultMessage declineObserverReconnect(LoLClientController controller) {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "gameService", "declineObserverReconnect", new Object[]{});
                
                if (observeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                // if ok, we can change state to back to normal
                controller.setState(GameState.IDLE);
                controller.notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
                return ResultMessage.OK;
	}
	
	
	// name = "fjxokt game", gameMode = "CLASSIC", pwd = "", allowSpectators = "ALL"
	public static GameDTO createPracticeGame(LoLClientController controller, String name, String gameMode, GameMap map, Integer maxPlayers, Integer minPlayers, String pwd, String allowSpectators)
				throws PlayerAlreadyInGameException {
            
                PracticeGameConfig gameConf = new PracticeGameConfig(name, map, gameMode, pwd, maxPlayers, allowSpectators, 1);
                TypedObject gameTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "gameService", "createPracticeGame", new Object[]{ gameConf.getTypedObject() });
		
                if(gameTo == null){
                    throw new PlayerAlreadyInGameException(controller.getRTMPSClient().getErrorMessage(gameTo));
                }
                
                // now create the game object
                controller.setGame(new GameDTO(gameTo));

                controller.setState(GameState.TEAM_SELECT);
                controller.notifyGameUpdated(controller.getGame(), ClientEventType.JOINING_TEAM_SELECT);

                // TODO: connect chat room
                MessagingManager.getInst().joinRoom(controller.getGame());

                return controller.getGame();
	}
	
	public static StartChampSelectDTO startChampionSelection(LoLRTMPSClient client, Double gameId, Integer numPlayers) {
		// TODO: figure out what is this num
            
                TypedObject selectResult = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "startChampionSelection", new Object[] { gameId, numPlayers });
		
                if(selectResult == null){
                    return null;
                }
                
                return new StartChampSelectDTO(selectResult);
	}
	
	public static ResultMessage switchTeams(LoLRTMPSClient client, Double gameId) {
                Object switchResult = LoLClientService.getServiceResponseDataBody(client, "gameService", "switchTeams", new Object[]{gameId});
                
                if (switchResult == null) {
                        return ResultMessage.ERROR;
                }
                
                Boolean didSwitch = (Boolean) switchResult;
                
                if(didSwitch){
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	public static ResultMessage selectChampion(LoLRTMPSClient client, Integer championId) {
            
		System.out.println("selectChampion() called");
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectChampion", new Object[]{championId});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage selectChampionSkin(LoLRTMPSClient client, Integer championId, Integer skinId) {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectChampionSkin", new Object[]{championId, skinId});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                                
                return ResultMessage.OK;
	}
	
	public static ResultMessage selectSpells(LoLRTMPSClient client, Integer spell1, Integer spell2) {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectSpells", new Object[]{spell1, spell2});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage selectBotChampion(LoLRTMPSClient client, Integer championId, BotParticipant bot) {
                
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectBotChampion", new Object[] { championId, bot });
                                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage removeBotChampion(LoLRTMPSClient client, Integer championId, BotParticipant bot) {
            
                TypedObject removeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "removeBotChampion", new Object[] {  championId, bot.getTypedObject() });
                                
                if (removeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage banUserFromGame(LoLRTMPSClient client, Double gameId, Integer accountId) {
            
                TypedObject banTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "banUserFromGame", new Object[] { gameId, accountId });
                                
                if (banTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}

	public static ResultMessage banObserverFromGame(LoLRTMPSClient client, Double gameId, Integer accountId) {
            
                TypedObject banTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "banObserverFromGame", new Object[] { gameId, accountId });
                                
                if (banTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage sendChampionTradeMessage(LoLRTMPSClient client, ChampionTradeMessage msg) {
            
                TypedObject tradeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "sendChampionTradeMessage", new Object[] { msg.getTypedObject() });
                                
                if (tradeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage tradeChampion(LoLRTMPSClient client, String playerName) {
            
                TypedObject tradeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "tradeChampion", new Object[] { playerName });
                                
                if (tradeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage championSelectCompleted(LoLRTMPSClient client) {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "championSelectCompleted", new Object[] {});
                                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public static ResultMessage setClientReceivedGameMessage(LoLRTMPSClient client, Double gameId, GameState state) {
		System.out.println("setClientReceivedGameMessage() called");
                boolean isSet = LoLClientService.executeService(client, "gameService", "setClientReceivedGameMessage", new Object[] { gameId, state.getName() });
		
                if(isSet){
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	public static ResultMessage setClientReceivedMaestroMessage(LoLRTMPSClient client, Double gameId, GameState state) {
                
                System.out.println("setClientReceivedMaestroMessage() called");
            
                boolean success = LoLClientService.executeService(client, "gameService", "setClientReceivedMaestroMessage", new Object[] {gameId, state.getName()});
                                
                if (success) {
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	public static ResultMessage getLatestGameTimerState(LoLRTMPSClient client, Double gameId, GameState state, Integer numPlayers) {
            
                System.out.println(gameId + " : " + state.getName() + " : " + numPlayers);
            
                boolean success = LoLClientService.executeService(client, "gameService", "getLatestGameTimerState", new Object[] {gameId, state.getName(), numPlayers});
                                
                if (success) {
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
}
