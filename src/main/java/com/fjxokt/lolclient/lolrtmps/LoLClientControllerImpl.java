package com.fjxokt.lolclient.lolrtmps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.exceptions.PlayerAlreadyInGameException;
import com.fjxokt.lolclient.lolrtmps.model.AggregatedStats;
import com.fjxokt.lolclient.lolrtmps.model.AllSummonerData;
import com.fjxokt.lolclient.lolrtmps.model.BotParticipant;
import com.fjxokt.lolclient.lolrtmps.model.BroadcastNotification;
import com.fjxokt.lolclient.lolrtmps.model.ChampionStatInfo;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.ClientLoginKickNotification;
import com.fjxokt.lolclient.lolrtmps.model.ClientSystemStatesNotification;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.fjxokt.lolclient.lolrtmps.model.GameNotification;
import com.fjxokt.lolclient.lolrtmps.model.GameQueueConfig;
import com.fjxokt.lolclient.lolrtmps.model.LoginDataPacket;
import com.fjxokt.lolclient.lolrtmps.model.MatchMakerParams;
import com.fjxokt.lolclient.lolrtmps.model.PlayerLifetimeStats;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameConfig;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.model.RecentGames;
import com.fjxokt.lolclient.lolrtmps.model.Rune;
import com.fjxokt.lolclient.lolrtmps.model.RuneCombiner;
import com.fjxokt.lolclient.lolrtmps.model.RuneQuantity;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.StoreAccountBalanceNotification;
import com.fjxokt.lolclient.lolrtmps.model.SummonerRuneInventory;
import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.fjxokt.lolclient.lolrtmps.model.dto.AllPublicSummonerDataDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.LeagueListDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerCredentialsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.StartChampSelectDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerActiveBoostsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerIconInventoryDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerLeaguesDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamAggregatedStatsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ChampionTradeMessageType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameMode;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameNotificationType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.PlayerBaseLevel;
import com.fjxokt.lolclient.lolrtmps.model.utils.QueueType;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.model.utils.TerminatedCondition;
import com.fjxokt.lolclient.lolrtmps.services.LoLClientService;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.gvaneyck.rtmp.JSON;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.Arrays;

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
	
	protected void notifyGameUpdated(GameDTO game, ClientEventType state) {
		notifyGameUpdated(game, state, null, null, null, null);
	}
	
	protected void notifyClientUpdate(ClientEventType state, SearchingForMatchNotification search) {
		notifyGameUpdated(game, state, null, search, null, null);
	}
	
	protected void notifyClientUpdate(ClientEventType state) {
		notifyGameUpdated(null, state, null, null, null, null);
	}
	
	protected void notifyClientUpdate(ClientEventType state, StoreAccountBalanceNotification balance) {
		notifyGameUpdated(null, state, null, null, null, balance);
	}
	
	protected void notifyClientUpdate(ClientEventType state, ChampionTradeMessage msg) {
		notifyGameUpdated(null, state, null, null, msg, null);
	}
	
	protected void notifyGameUpdated(GameDTO game, ClientEventType state, EndOfGameStats stats, 
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
	
	///////////////////////////////////////////////////
	// login Service
	///////////////////////////////////////////////////

	public ResultMessage login() {
		try {
			// login
			client.connectAndLogin();
		} catch (IOException e) {
			e.printStackTrace();
			return ResultMessage.LOGIN_FAILED;
		}
		setState(GameState.IDLE);
		notifyClientUpdate(ClientEventType.LOGGED_IN);
		return ResultMessage.OK;
	}
	
	public void logout() {
		if (client != null) {
			client.close();			
		}
		setState(GameState.DISCONNECTED);
		notifyClientUpdate(ClientEventType.LOGGED_OUT);
		System.out.println("logout !");
	}
	
	public boolean isLoggedIn() {
		return client.isLoggedIn();
	}

	public String getRegion() {
		return client.getRegion();
	}
	
	public Integer getAccountId() {
		return client.getAccountID();
	}
	
	public String getStoreUrl() {
            
                Object urlObject = LoLClientService.getServiceResponseDataBody(client, "loginService", "getStoreUrl", new Object[]{});
                
                if(urlObject == null){
                    return "";
                }
                
                return (String) urlObject;
	}
	
	///////////////////////////////////////////////////
	// summonerTeam Service
	///////////////////////////////////////////////////
	
	public PlayerDTO createPlayer() {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "createPlayer", new Object[] {});
                
                if (teamTo == null) {
                    return null;
                }

                return new PlayerDTO(teamTo);
	}
	
	public TeamDTO createTeam(String teamName, String teamTag) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "createTeam", new Object[] { teamName, teamTag });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
	}
	
	public TeamDTO invitePlayer(Double summonerId, TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "invitePlayer", new Object[] { summonerId, teamId.getTypedObject() });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
                
	}
	
	public TeamDTO findTeamById(TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamById", new Object[] { teamId.getTypedObject() });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
	}
	
	public TeamDTO findTeamByTag(String tag) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamByTag", new Object[] { tag });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
 
	}
	
	public TeamDTO findTeamByName(String name) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamByName", new Object[] { name });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
	}
	
	public TeamDTO disbandTeam(TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "disbandTeam", new Object[] { teamId.getTypedObject() });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
	}
	
	public Boolean isNameValidAndAvailable(String teamName) {
                Object isValid = LoLClientService.getServiceResponseDataBody(client, "summonerTeamService", "isNameValidAndAvailable", new Object[] { teamName});
                
                if (isValid == null) {
                        return null;
                }

                return (Boolean)isValid;
	}
	
	public Boolean isTagValidAndAvailable(String tagName) {
                Object isValid = LoLClientService.getServiceResponseDataBody(client, "summonerTeamService", "isTagValidAndAvailable", new Object[] { tagName});
                
                if (isValid == null) {
                        return null;
                }

                return (Boolean)isValid;
	}
	
	///////////////////////////////////////////////////
	// clientFacade Service
	///////////////////////////////////////////////////
	
	public LoginDataPacket getLoginDataPacketForUser() {
            
                TypedObject loginTo = LoLClientService.getTypedServiceResponseDataBody(client, "clientFacadeService", "getLoginDataPacketForUser", new Object[] {});
		
                if(loginTo == null){
                    return null;
                }
                
                LoginDataPacket login = new LoginDataPacket(loginTo);
			
                // check if a game in running
                PlatformGameLifecycleDTO gameLifeCycle = login.getReconnectInfo();
                if (gameLifeCycle != null) {
                        System.out.println("You have a game in progress : " + gameLifeCycle);
                        game = gameLifeCycle.getGame();
                        setState(GameState.GAME_IN_PROGRESS);
                        notifyGameUpdated(gameLifeCycle.getGame(), ClientEventType.GAME_IN_PROGRESS);
                }

                return login;
	}
	
	public Integer[] callKudos(String commandName, Double summonerId) {
            
                TypedObject input = new TypedObject();
                input.put("commandName", "TOTALS");
                input.put("summonerId", summonerId);
                
                TypedObject kudosTo = LoLClientService.getTypedServiceResponseDataBody(client, "clientFacadeService", "callKudos", new Object[] {input.toString()});
                
                if(kudosTo == null){
                    return null;
                }
                
                String value = kudosTo.getTO("data").getTO("body").getString("value");
                
                TypedObject kudosResult = (TypedObject)JSON.parse(value);
                
                if(kudosResult == null){
                    return null;
                }
                
                Object[] kudosResults = kudosResult.getArray("totals");
                
                if(kudosResults == null){
                    return null;
                }
                
                return Arrays.asList(kudosResults).toArray(new Integer[kudosResults.length]);
	}
	
	///////////////////////////////////////////////////
	// summoner Service
	///////////////////////////////////////////////////
	
	public AllSummonerData createDefaultSummoner(String summonerName) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "createDefaultSummoner", new Object[] { summonerName });
                
                if (summonerTo == null) {
                        return null;
                }

                return new AllSummonerData(summonerTo);
	}
	
	public ResultMessage saveSeenTutorialFlag() {
            TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "createDefaultSummoner", new Object[] {});
                
            if (summonerTo == null) {
                    return ResultMessage.ERROR;
            }

            return ResultMessage.OK;
	}

	public ResultMessage updateProfileIconId(Integer iconId) {
            TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "updateProfileIconId", new Object[] {iconId});
                
            if (summonerTo == null) {
                    return ResultMessage.ERROR;
            }

            return ResultMessage.OK;
	}

	public PublicSummoner getSummonerByName(String name) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "getSummonerByName", new Object[] { name });
                
                if (summonerTo == null) {
                        return null;
                }

                return new PublicSummoner(summonerTo);
	}
	
	public String getSummonerInternalNameByName(String summonerName) {
                Object summonerTo = LoLClientService.getServiceResponseDataBody(client, "summonerService", "getSummonerInternalNameByName", new Object[] { summonerName });
                
                if (summonerTo == null) {
                        return null;
                }

                return (String) summonerTo;
	}
	
	public List<String> getSummonerNames(Integer[] summonerIds) {
            
                Object[] summonerNames = LoLClientService.getServiceResponseDataBodyArray(client, "summonerService", "getSummonerNames", new Object[] { summonerIds });
		
                if(summonerNames == null){
                    return null;
                }
                
                List<Object> summonerNamesAsObjects = Arrays.asList(summonerNames);
                List<String> summonerNamesAsStrings = new ArrayList<String>(summonerNamesAsObjects.size());
                
                for(Object o : summonerNamesAsObjects){
                    summonerNamesAsStrings.add((String)o);
                }
                
                return summonerNamesAsStrings;
	}
	
	public AllPublicSummonerDataDTO getAllPublicSummonerDataByAccount(Integer accountId) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "getAllPublicSummonerDataByAccount", new Object[] { accountId });
                
                if (summonerTo == null) {
                        return null;
                }

                return new AllPublicSummonerDataDTO(summonerTo);
	}
	
	///////////////////////////////////////////////////
	// leagues Service Proxy
	///////////////////////////////////////////////////
	
	public SummonerLeaguesDTO getAllMyLeagues() {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllMyLeagues", new Object[] {});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public SummonerLeaguesDTO getAllLeaguesForPlayer(Double summonerId) {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllLeaguesForPlayer", new Object[] {summonerId});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public SummonerLeaguesDTO getLeaguesForTeam(String teamId) {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllLeaguesForPlayer", new Object[] {teamId});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public LeagueListDTO getChallengerLeague(QueueType type) {
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getChallengerLeague", new Object[] {type.getType() });
                
                if (leagueTo == null) {
                        return null;
                }

                return new LeagueListDTO(leagueTo);
	}
	
	///////////////////////////////////////////////////
	// summonerIcon Service
	///////////////////////////////////////////////////
	
	public SummonerIconInventoryDTO getSummonerIconInventory(Double summonerId) {
            
                TypedObject iconTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerIconService", "getSummonerIconInventory", new Object[] { summonerId });
                
                if (iconTo == null) {
                        return null;
                }

                return new SummonerIconInventoryDTO(iconTo);
	}
	
	///////////////////////////////////////////////////
	// playerStats Service
	///////////////////////////////////////////////////
	
	public ResultMessage processEloQuestionaire(PlayerBaseLevel level) {
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "processEloQuestionaire", new Object[] { level.getLevel() });
                
                if (playerStatTo == null) {
                        return ResultMessage.ERROR;
                }

                return ResultMessage.OK;
	}
	
	public PlayerLifetimeStats retrievePlayerStatsByAccountId(Integer accountId) {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "retrievePlayerStatsByAccountId", new Object[] { accountId, "CURRENT"  });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new PlayerLifetimeStats(playerStatTo);
	}
	
	public List<ChampionStatInfo> retrieveTopPlayedChampions(Integer accountId, GameMode mode) {
            
                Object[] championStats = LoLClientService.getServiceResponseDataBodyArray(client, "playerStatsService", "retrieveTopPlayedChampions", new Object[] { accountId, mode.getName() });
		
                if(championStats == null){
                    return null;
                }
                
                List<Object> championStatsAsObjects = Arrays.asList(championStats);
                List<ChampionStatInfo> championStatsAsStats = new ArrayList<ChampionStatInfo>(championStatsAsObjects.size());
                
                for(Object o : championStatsAsObjects){
                    TypedObject champStat = (TypedObject)o;
                    if(champStat != null){
                        championStatsAsStats.add(new ChampionStatInfo(champStat));
                    }
                }
                
                return championStatsAsStats;
	}
	
	public AggregatedStats getAggregatedStats(Integer accountId, GameMode mode) {
                
		// TODO: check if it's always "CURRENT"
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getAggregatedStats", new Object[] { accountId, mode.getName(), "CURRENT"  });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new AggregatedStats(playerStatTo);
	}
	
	public List<TeamAggregatedStatsDTO> getTeamAggregatedStats(String teamId) {
            
                Object[] teamStats = LoLClientService.getServiceResponseDataBodyArray(client, "playerStatsService", "getTeamAggregatedStats", new Object[] { teamId });
		
                if(teamStats == null){
                    return null;
                }
                
                List<Object> teamStatsAsObjects = Arrays.asList(teamStats);
                List<TeamAggregatedStatsDTO> teamStatsAsStats = new ArrayList<TeamAggregatedStatsDTO>(teamStatsAsObjects.size());
                
                for(Object o : teamStatsAsObjects){
                    TypedObject teamStat = (TypedObject)o;
                    if(teamStat != null){
                        teamStatsAsStats.add(new TeamAggregatedStatsDTO(teamStat));
                    }
                }
                
                return teamStatsAsStats;
	}
	
	public EndOfGameStats getTeamEndOfGameStats(TeamId teamId, Double gameId) {
                
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getTeamEndOfGameStats", new Object[] { teamId.getTypedObject(), gameId });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new EndOfGameStats(playerStatTo);
	}
	
	public RecentGames getRecentGames(Integer accountId) {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getRecentGames", new Object[] { accountId });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new RecentGames(playerStatTo);
	}
	
	///////////////////////////////////////////////////
	// inventory Service
	///////////////////////////////////////////////////
	
	public SummonerActiveBoostsDTO getSumonerActiveBoosts() {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "inventoryService", "getSumonerActiveBoosts", new Object[] {});
                
                if (playerStatTo == null) {
                        return null;
                }

                return new SummonerActiveBoostsDTO(playerStatTo);
	}

	public List<ChampionDTO> getAvailableChampions() {
            
                Object[] championsResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "getAvailableChampions", new Object[] {});
		
                if(championsResult == null){
                    return null;
                }
                
                List<Object> championsAsObjects = Arrays.asList(championsResult);
                List<ChampionDTO> championAsChamps = new ArrayList<ChampionDTO>(championsAsObjects.size());
                
                for(Object o : championsAsObjects){
                    TypedObject champ = (TypedObject)o;
                    if(champ != null){
                        championAsChamps.add(new ChampionDTO(champ));
                    }
                }
                
                return championAsChamps;
	}
	
	public List<String> retrieveInventoryTypes() {
            
                Object[] inventoryResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "retrieveInventoryTypes", new Object[] {});
		
                if(inventoryResult == null){
                    return null;
                }
                
                List<Object> inventoryAsObjects = Arrays.asList(inventoryResult);
                List<String> inventoryAsString = new ArrayList<String>(inventoryAsObjects.size());
                
                for(Object type : inventoryAsObjects){
                        inventoryAsString.add((String)type);
                }
                
                return inventoryAsString;
	}
	
	public List<RuneCombiner> getAllRuneCombiners() {
            
                Object[] championsResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "getAllRuneCombiners", new Object[] {});
		
                if(championsResult == null){
                    return null;
                }
                
                List<Object> runesAsObjects = Arrays.asList(championsResult);
                List<RuneCombiner> runesAsCombiners = new ArrayList<RuneCombiner>(runesAsObjects.size());
                
                for(Object o : runesAsObjects){
                    TypedObject combiner = (TypedObject)o;
                    if(combiner != null){
                        runesAsCombiners.add(new RuneCombiner(combiner));
                    }
                }
                
                return runesAsCombiners;
	}
	
	public List<RuneQuantity> useRuneCombiner(Integer runeCombinerId, List<Rune> runes) {
            
                // change list to object[]
                Object[] robjs = new Object[runes.size()];
                for (int i=0; i< runes.size(); i++) {
                        Rune rune = runes.get(i);
                        robjs[i] = (rune == null) ? null : rune.getTypedObject();
                }
                
                Object[] runesResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "useRuneCombiner", new Object[] { runeCombinerId, robjs });
		
                if(runesResult == null){
                    return null;
                }
                
                List<Object> runesAsObjects = Arrays.asList(runesResult);
                List<RuneQuantity> runesAsQuantities = new ArrayList<RuneQuantity>(runesAsObjects.size());
                
                for(Object o : runesAsObjects){
                    TypedObject quantity = (TypedObject)o;
                    if(quantity != null){
                        runesAsQuantities.add(new RuneQuantity(quantity));
                    }
                }
                
                return runesAsQuantities;
	}
	
	///////////////////////////////////////////////////
	// GameMap Service
	///////////////////////////////////////////////////
	
	public List<GameMap> getGameMapList() {
            
                Object[] mapsList = LoLClientService.getServiceResponseDataBodyArray(client, "gameMapService", "getGameMapList", new Object[]{});
		
                if(mapsList == null){
                    return null;
                }
                
                List<Object> mapsAsObjects = Arrays.asList(mapsList);
                List<GameMap> mapsAsGameMaps = new ArrayList<GameMap>(mapsAsObjects.size());
                
                for(Object o : mapsAsObjects){
                    TypedObject quantity = (TypedObject)o;
                    if(quantity != null){
                        mapsAsGameMaps.add(new GameMap(quantity));
                    }
                }
                
                return mapsAsGameMaps;
	}
	
	///////////////////////////////////////////////////
	// masteryBook Service
	///////////////////////////////////////////////////
	
	public MasteryBookDTO getMasteryBook(Double summonerId) {
                TypedObject masteryTo = LoLClientService.getTypedServiceResponseDataBody(client, "masteryBookService", "getMasteryBook", new Object[] { summonerId });
                
                if (masteryTo == null) {
                        return null;
                }

                return new MasteryBookDTO(masteryTo);
	}
	
	public MasteryBookDTO saveMasteryBook(MasteryBookDTO book) {
                TypedObject masteryTo = LoLClientService.getTypedServiceResponseDataBody(client, "masteryBookService", "saveMasteryBook", new Object[] { book.getTypedObject() });
                
                if (masteryTo == null) {
                        return null;
                }
                
                return new MasteryBookDTO(masteryTo);
	}
	
	///////////////////////////////////////////////////
	// spellBook Service
	///////////////////////////////////////////////////
	
	public SpellBookDTO getSpellBook(Double summonerId) {
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService", "getSpellBook", new Object[] { summonerId });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookDTO(spellTo);
	}
	
	public SpellBookPageDTO selectDefaultSpellBookPage(SpellBookPageDTO page) {
                
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService", "selectDefaultSpellBookPage", new Object[] { page.getTypedObject() });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookPageDTO(spellTo);
	}
	
	public SpellBookDTO saveSpellBook(SpellBookDTO book) {
            
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService","saveSpellBook", new Object[] { book.getTypedObject() });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookDTO(spellTo);
	}
	
	///////////////////////////////////////////////////
	// summonerRune Service
	///////////////////////////////////////////////////
	
	public SummonerRuneInventory getSummonerRuneInventory(Double summonerId) {
            
                TypedObject runeTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerRuneService", "getSummonerRuneInventory", new Object[] { summonerId });
                
                if (runeTo == null) {
                        return null;
                }
                
                return new SummonerRuneInventory(runeTo);
	}
	
	///////////////////////////////////////////////////
	// matchmakerService Service
	///////////////////////////////////////////////////

	public List<GameQueueConfig> getAvailableQueues() {
            
                Object[] queuesList = LoLClientService.getServiceResponseDataBodyArray(client, "matchmakerService", "getAvailableQueues", new Object[]{});
		
                if(queuesList == null){
                    return null;
                }
                
                List<Object> queuesAsObjects = Arrays.asList(queuesList);
                List<GameQueueConfig> queuesAsConfigs = new ArrayList<GameQueueConfig>(queuesAsObjects.size());
                
                for(Object o : queuesAsObjects){
                    TypedObject config = (TypedObject)o;
                    if(config != null){
                        queuesAsConfigs.add(new GameQueueConfig(config));
                    }
                }
                
                return queuesAsConfigs;
	}
	
	public SearchingForMatchNotification attachToQueue(MatchMakerParams params) {
            
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(client, "matchmakerService", "attachToQueue", new Object[] { params.getTypedObject() });
                
                if (matchTo == null) {
                        return null;
                }
                
                SearchingForMatchNotification res = new SearchingForMatchNotification(matchTo);
		notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
                
                return res;
	}
	
	public SearchingForMatchNotification attachTeamToQueue(MatchMakerParams params) {
                
                // TODO: look TextEdit with a error for DODGE QUEUE
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(client, "matchmakerService", "attachToQueue", new Object[] { params.getTypedObject() });
                
                if (matchTo == null) {
                        return null;
                }
                
                SearchingForMatchNotification res = new SearchingForMatchNotification(matchTo);
		notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
                
                return res;
	}
	
	public ResultMessage cancelFromQueueIfPossible(Double queueId) {
            
                Object cancelResult = LoLClientService.getServiceResponseDataBody(client, "matchmakerService", "cancelFromQueueIfPossible", new Object[] { queueId });
                
                if (cancelResult == null) {
                        return ResultMessage.ERROR;
                }
                
                Boolean cancelHappened = (Boolean) cancelResult;
                
                if(cancelHappened){
                    return ResultMessage.OK;
                }
                else{
                    return ResultMessage.ERROR;
                }
	}
	
	public ResultMessage acceptInviteForMatchmakingGame(String invitationId) {
            
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(client, "matchmakerService", "acceptInviteForMatchmakingGame", new Object[] { invitationId });
                
                if (matchTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	///////////////////////////////////////////////////
	// game Service
	///////////////////////////////////////////////////
	
	public PlatformGameLifecycleDTO retrieveInProgressSpectatorGameInfo(String summonerName) {
            
                TypedObject progressTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "retrieveInProgressSpectatorGameInfo", new Object[]{ summonerName });
                
                if (progressTo == null) {
                        return null;
                }
                
                return new PlatformGameLifecycleDTO(progressTo);
	}
	
	public ResultMessage acceptPoppedGame(Boolean answer) {
            
                boolean accepted = LoLClientService.executeService(client, "gameService", "acceptPoppedGame", new Object[] { answer });
                
                if(accepted){
                    return ResultMessage.OK;
                } else {
                    return ResultMessage.ERROR;
                }
	}

	public List<PracticeGameSearchResult> listAllPracticeGames() {
            
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
	
	public ResultMessage joinGame(Double gameId, String pwd) {           
            
                setState(GameState.WAITING);
                TypedObject gameJoinTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "joinGame", new Object[]{ gameId, pwd });
                
                if(gameJoinTo == null){
                    return ResultMessage.ERROR;
                }
                
                TypedObject queueTo = queue.poll();
                
                while(true){
                        TypedObject queueBody = queueTo.getTO("data").getTO("body");
                        // if it's not a GameDTO object, we keep waiting
                        if (queueBody.type.equals(GameDTO.getTypeClass())) {
                                // create the game objwct
                                GameDTO gameDTO = new GameDTO(queueBody);
                                // check we got the correct game
                                if (gameDTO.getId().equals(gameId)) {
                                        game = gameDTO;
                                        System.out.println("We got our game object!");
                                        System.out.println(game);
                                        break;
                                }
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            System.out.println("Sleep interrupted");
                        }
                }
                
                // TODO: connect chat room
                MessagingManager.getInst().joinRoom(game);

                // update state
                setState(GameState.TEAM_SELECT);
                notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
                
                return ResultMessage.OK;
	}
	
	public ResultMessage switchPlayerToObserver(Double gameId) {
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
	public ResultMessage switchObserverToPlayer(Double gameId, Integer whatTeam) {
            
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
	
	public GameDTO observeGame(Double gameId) {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "observeGame", new Object[]{ gameId, null });
                
                if (observeTo == null) {
                        return null;
                }
                
                // get our game object
		game = new GameDTO(observeTo);
                
                // TODO: connect chat room
		MessagingManager.getInst().joinRoom(game);
                
                // update state
                setState(GameState.TEAM_SELECT);
                notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
                
                return game; 
	}
	
	public ResultMessage quitGame() {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "quitGame", new Object[]{});
                
                if (observeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                // get our game object
		game = new GameDTO(observeTo);
                
                // TODO: connect chat room
		MessagingManager.getInst().leaveRoom(game);
                
                // update state
                System.out.println("quit ok");
                setState(GameState.IDLE);
                notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
                
                return ResultMessage.OK; 
	}
	
	public ResultMessage declineObserverReconnect() {
            
                TypedObject observeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "declineObserverReconnect", new Object[]{});
                
                if (observeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                // if ok, we can change state to back to normal
                setState(GameState.IDLE);
                notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
                return ResultMessage.OK;
	}
	
	
	// name = "fjxokt game", gameMode = "CLASSIC", pwd = "", allowSpectators = "ALL"
	public GameDTO createPracticeGame(String name, String gameMode, GameMap map, Integer maxPlayers, Integer minPlayers, String pwd, String allowSpectators)
				throws PlayerAlreadyInGameException {
            
                PracticeGameConfig gameConf = new PracticeGameConfig(name, map, gameMode, pwd, maxPlayers, allowSpectators, 1);
                TypedObject gameTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "createPracticeGame", new Object[]{ gameConf.getTypedObject() });
		
                if(gameTo == null){
                    throw new PlayerAlreadyInGameException(client.getErrorMessage(gameTo));
                }
                
                // now create the game object
                game = new GameDTO(gameTo);
                System.out.println(game);

                setState(GameState.TEAM_SELECT);
                notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);

                // TODO: connect chat room
                MessagingManager.getInst().joinRoom(game);

                return game;
	}
	
	public StartChampSelectDTO startChampionSelection(Double gameId, Integer numPlayers) {
		// TODO: figure out what is this num
            
                TypedObject selectResult = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "startChampionSelection", new Object[] { game.getId(), game.getOptimisticLock() });
		
                if(selectResult == null){
                    return null;
                }
                
                return new StartChampSelectDTO(selectResult);
	}
	
	public ResultMessage switchTeams(Double gameId) {
                Object switchResult = LoLClientService.getServiceResponseDataBody(client, "gameService", "switchTeams", new Object[]{game.getId()});
                
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
	
	public ResultMessage selectChampion(Integer championId) {
            
		System.out.println("selectChampion() called");
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectChampion", new Object[]{championId});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage selectChampionSkin(Integer championId, Integer skinId) {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectChampionSkin", new Object[]{championId, skinId});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                                
                return ResultMessage.OK;
	}
	
	public ResultMessage selectSpells(Integer spell1, Integer spell2) {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectSpells", new Object[]{spell1, spell2});
                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage selectBotChampion(Integer championId, BotParticipant bot) {
                
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "selectBotChampion", new Object[] { championId, bot });
                                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage removeBotChampion(Integer championId, BotParticipant bot) {
            
                TypedObject removeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "removeBotChampion", new Object[] {  championId, bot.getTypedObject() });
                                
                if (removeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage banUserFromGame(Double gameId, Integer accountId) {
            
                TypedObject banTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "banUserFromGame", new Object[] { gameId, accountId });
                                
                if (banTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}

	public ResultMessage banObserverFromGame(Double gameId, Integer accountId) {
            
                TypedObject banTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "banObserverFromGame", new Object[] { gameId, accountId });
                                
                if (banTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage sendChampionTradeMessage(ChampionTradeMessage msg) {
            
                TypedObject tradeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "sendChampionTradeMessage", new Object[] { msg.getTypedObject() });
                                
                if (tradeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage tradeChampion(String playerName) {
            
                TypedObject tradeTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "tradeChampion", new Object[] { playerName });
                                
                if (tradeTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage championSelectCompleted() {
            
                TypedObject selectTo = LoLClientService.getTypedServiceResponseDataBody(client, "gameService", "championSelectCompleted", new Object[] {});
                                
                if (selectTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
	
	public ResultMessage setClientReceivedGameMessage(Double gameId, GameState state) {
		System.out.println("setClientReceivedGameMessage() called");
                boolean isSet = LoLClientService.executeService(client, "gameService", "setClientReceivedGameMessage", new Object[] { gameId, state.getName() });
		
                if(isSet){
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	public ResultMessage setClientReceivedMaestroMessage(Double gameId, GameState state) {
                
                System.out.println("setClientReceivedMaestroMessage() called");
            
                boolean success = LoLClientService.executeService(client, "gameService", "setClientReceivedMaestroMessage", new Object[] {gameId, state.getName()});
                                
                if (success) {
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	public ResultMessage getLatestGameTimerState(Double gameId, GameState state, Integer numPlayers) {
            
                System.out.println(gameId + " : " + state.getName() + " : " + numPlayers);
            
                boolean success = LoLClientService.executeService(client, "gameService", "getLatestGameTimerState", new Object[] {gameId, state.getName(), numPlayers});
                                
                if (success) {
                    return ResultMessage.OK;
                } else{
                    return ResultMessage.ERROR;
                }
	}
	
	/////////////
	// CALLBACK
	/////////////
	
	private void goToChampSelectState() {
		System.out.println("Joining champ selection room");
		setState(GameState.CHAMP_SELECT);
		
		ResultMessage res = setClientReceivedGameMessage(game.getId(), GameState.CHAMP_SELECT_CLIENT);
		System.out.println("state1: " + res.getMessage());

		res = getLatestGameTimerState(game.getId(), GameState.CHAMP_SELECT, game.getGameTypeConfigId());
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
				tradeChampion(msg.getSenderInternalSummonerName());
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
			if (setClientReceivedGameMessage(game.getId(), GameState.GAME_START_CLIENT).equals(ResultMessage.OK)) {
				setState(GameState.GAME_START_CLIENT);
				if (setClientReceivedMaestroMessage(game.getId(), GameState.GameClientConnectedToServer).equals(ResultMessage.OK)) {
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
			setClientReceivedGameMessage(gameLifeCycle.getGame().getId(), GameState.GAME_START_CLIENT);
			setClientReceivedMaestroMessage(gameLifeCycle.getGame().getId(), GameState.GameReconnect);
			setClientReceivedMaestroMessage(gameLifeCycle.getGame().getId(), GameState.GameClientConnectedToServer);
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
	
	private void setState(GameState newState) {
		System.out.println("going from state " + status + " to " + newState);
		status = newState;
	}
	
}