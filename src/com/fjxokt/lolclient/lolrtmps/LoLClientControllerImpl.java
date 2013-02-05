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
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerCredentialsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.StartChampSelectDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerActiveBoostsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerIconInventoryDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerLeaguesDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ChampionTradeMessageType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameNotificationType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.PlayerBaseLevel;
import com.fjxokt.lolclient.lolrtmps.model.utils.QueueType;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.model.utils.TerminatedCondition;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

abstract class LoLClientControllerImpl implements LoLClientController {
	
	protected LoLRTMPSClient client;
	private Queue<TypedObject> queue;
	private GameState status;
	private List<ClientListener> gameListeners = new ArrayList<ClientListener>();
	private List<ClientListener> addQueueListeners = new ArrayList<ClientListener>();
	private List<ClientListener> remQueueListeners = new ArrayList<ClientListener>();
	private volatile boolean isNotifying;
	private ClientEventType lastClientEventSent;
	
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
	
	// Game data
	//private LoginDataPacket loginDataPacket;
	//private PlatformGameLifecycleDTO gameLifeCycle;
	/*private List<GameQueueConfig> availableQueues = null;
	private List<ChampionDTO> availableChampions = null;
	private SummonerRuneInventory summonerRuneInventory;
	private MasteryBookDTO masteryBook;
	private PlayerDTO player;*/
	protected GameDTO game;
	
	public LoLClientControllerImpl() {
		queue = new LinkedList<TypedObject>();
		setState(GameState.DISCONNECTED);
		game = null;
	}
	
	@Override
	public void initUser(String region, String clientVersion, String user, String pass) {
		client = new LoLRTMPSClient(region, clientVersion, user, pass);
		client.setReceiveHandler(this);
	}
	
	@Override
	public String getUser() {
		return client.getUser();
	}
	
	@Override
	public String getPassword() {
		return client.getPassword();
	}
	
	///////////////////////////////////////////////////
	// login Service
	///////////////////////////////////////////////////

	@Override
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
	
	@Override
	public void logout() {
		if (client != null) {
			client.close();			
		}
		setState(GameState.DISCONNECTED);
		notifyClientUpdate(ClientEventType.LOGGED_OUT);
		System.out.println("logout !");
	}
	
	@Override
	public boolean isLoggedIn() {
		return client.isLoggedIn();
	}

	@Override
	public String getRegion() {
		return client.getRegion();
	}
	
	@Override
	public Integer getAccountId() {
		return client.getAccountID();
	}
	
	@Override
	public String getStoreUrl() {
		try {
			int id = client.invoke("loginService", "getStoreUrl", new Object[]{});
			TypedObject result = client.getResult(id);
			String url = result.getTO("data").getString("body");
			 
			client.join(id);
			return url;
			 
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	///////////////////////////////////////////////////
	// summonerTeam Service
	///////////////////////////////////////////////////
	
	@Override
	public PlayerDTO createPlayer() {
		try {
			int id = client.invoke("summonerTeamService", "createPlayer", new Object[]{});
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result.get("result").equals("_error")) {
				System.out.println(result);
				throw new IOException(client.getErrorMessage(result));
			}
			 
			TypedObject po = result.getTO("data").getTO("body");
			if (po == null) {
				return null;
			}
			
			return new PlayerDTO(po);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public 	TeamDTO createTeam(String teamName, String teamTag) {
		try {
			int id = client.invoke("summonerTeamService", "createTeam", new Object[] { teamName, teamTag });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			// TODO: throws exceptions related to error (TeanTagAlreadyExists and TeamNameAlreadyExists)
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("createTeam() ERROR: " + result);
				return null;
			}
			
			TypedObject teamTo = result.getTO("data").getTO("body");
			if (teamTo == null) {
				return null;
			}
			
			return new TeamDTO(teamTo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public 	TeamDTO invitePlayer(Double summonerId, TeamId teamId) {
		try {
			int id = client.invoke("summonerTeamService", "invitePlayer", new Object[] { summonerId, teamId.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("invitePlayer() error: " + result);
				return null;
			}
			
			TypedObject teamTo = result.getTO("data").getTO("body");
			if (teamTo == null) {
				return null;
			}
			
			return new TeamDTO(teamTo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public	TeamDTO findTeamById(TeamId teamId) {
		try {
			int id = client.invoke("summonerTeamService", "findTeamById", new Object[] { teamId.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("findTeamById() error: " + result);
				return null;
			}
			
			TypedObject teamTo = result.getTO("data").getTO("body");
			if (teamTo == null) {
				return null;
			}
			
			return new TeamDTO(teamTo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public	TeamDTO disbandTeam(TeamId teamId) {
		try {
			int id = client.invoke("summonerTeamService", "disbandTeam", new Object[] { teamId.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("disbandTeam() error: " + result);
				return null;
			}
			
			TypedObject teamTo = result.getTO("data").getTO("body");
			if (teamTo == null) {
				return null;
			}
			
			return new TeamDTO(teamTo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public	Boolean isNameValidAndAvailable(String teamName) {
		try {
			int id = client.invoke("summonerTeamService", "isNameValidAndAvailable", new Object[] { teamName });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("isNameValidAndAvailable() error: " + result);
				return null;
			}
			
			Boolean isValid = result.getTO("data").getBool("body");
			if (isValid == null) {
				return null;
			}
			
			return isValid;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Boolean isTagValidAndAvailable(String tagName) {
		try {
			int id = client.invoke("summonerTeamService", "isTagValidAndAvailable", new Object[] { tagName });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("isTagValidAndAvailable() error: " + result);
				return null;
			}
			
			Boolean isValid = result.getTO("data").getBool("body");
			if (isValid == null) {
				return null;
			}
			
			return isValid;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// clientFacade Service
	///////////////////////////////////////////////////
	
	@Override
	public LoginDataPacket getLoginDataPacketForUser() {
		try {
			int id = client.invoke("clientFacadeService", "getLoginDataPacketForUser", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").getTO("body") == null) {
				return null;
			}
			
			// create object
			TypedObject to = result.getTO("data").getTO("body");
			LoginDataPacket login = new LoginDataPacket(to);
			
			// check if a game in running
			PlatformGameLifecycleDTO gameLifeCycle = login.getReconnectInfo();
			if (gameLifeCycle != null) {
				System.out.println("You have a game in progress : " + gameLifeCycle);
				game = gameLifeCycle.getGame();
				setState(GameState.GAME_IN_PROGRESS);
				notifyGameUpdated(gameLifeCycle.getGame(), ClientEventType.GAME_IN_PROGRESS);
			}
			
			return login;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// summoner Service
	///////////////////////////////////////////////////
	
	@Override
	public AllSummonerData createDefaultSummoner(String summonerName) {
		try {
			int id = client.invoke("summonerService", "createDefaultSummoner", new Object[] { summonerName });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				return null;
			}
			
			TypedObject summonerTo = result.getTO("data").getTO("body");
			if (summonerTo == null) {
				return null;
			}
			
			return new AllSummonerData(summonerTo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public	ResultMessage saveSeenTutorialFlag() {
		try {
			int id = client.invoke("summonerService", "saveSeenTutorialFlag", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}

	@Override
	public ResultMessage updateProfileIconId(Integer iconId) {
		try {
			int id = client.invoke("summonerService", "updateProfileIconId", new Object[] { iconId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}

	
	@Override
	public PublicSummoner getSummonerByName(String name) {
		int id;
		try {
			id = client.invoke("summonerService", "getSummonerByName", new Object[] { name });			
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				System.out.println("getSummonerByName() error: " + result);
				return null;
			}

			TypedObject to = result.getTO("data").getTO("body");
			if (to == null) {
				System.out.println("no summoner with name: " + name);
				return null;
			}
		
			return new PublicSummoner(to);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getSummonerInternalNameByName(String summonerName) {
		try {
			int id = client.invoke("summonerService", "getSummonerInternalNameByName", new Object[] { summonerName });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getSummonerInternalNameByName() error: " + result);
				return null;
			}
			
			return result.getTO("data").getString("body");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> getSummonerNames(Integer[] summonerIds) {
		try {
			int id = client.invoke("summonerService", "getSummonerNames", new Object[] { summonerIds });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getSummonerNames() error: " + result);
				return null;
			}
			
			Object[] res = result.getTO("data").getArray("body");
			if (res != null) {
				List<String> names = new ArrayList<String>();
				for (Object o : res) {
					names.add((String)o);
				}
				return names;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public	AllPublicSummonerDataDTO getAllPublicSummonerDataByAccount(Integer accountId) {
		int id;
		try {
			id = client.invoke("summonerService", "getAllPublicSummonerDataByAccount", new Object[] { accountId });			
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getAllPublicSummonerDataByAccount() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new AllPublicSummonerDataDTO(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// leagues Service Proxy
	///////////////////////////////////////////////////
	
	@Override
	public SummonerLeaguesDTO getAllMyLeagues() {
		int id;
		try {
			id = client.invoke("leaguesServiceProxy", "getAllMyLeagues", new Object[] {});			
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getAllMyLeagues() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new SummonerLeaguesDTO(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SummonerLeaguesDTO getAllLeaguesForPlayer(Double summonerId) {
		int id;
		try {
			id = client.invoke("leaguesServiceProxy", "getAllLeaguesForPlayer", new Object[] { summonerId });			
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getAllLeaguesForPlayer() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new SummonerLeaguesDTO(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public LeagueListDTO getChallengerLeague(QueueType type) {
		int id;
		try {
			id = client.invoke("leaguesServiceProxy", "getChallengerLeague", new Object[] { type.getType() });			
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getChallengerLeague() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new LeagueListDTO(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// summonerIcon Service
	///////////////////////////////////////////////////
	
	@Override
	public SummonerIconInventoryDTO getSummonerIconInventory(Double summonerId) {
		try {
			int id = client.invoke("summonerIconService", "getSummonerIconInventory", new Object[] { summonerId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				return null;
			}
			
			TypedObject iconsRes = result.getTO("data").getTO("body");
			if (iconsRes == null) {
				return null;
			}
			
			return new SummonerIconInventoryDTO(iconsRes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// playerStats Service
	///////////////////////////////////////////////////
	
	@Override
	public ResultMessage processEloQuestionaire(PlayerBaseLevel level) {
		try {
			int id = client.invoke("playerStatsService", "processEloQuestionaire", new Object[] { level.getLevel() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public PlayerLifetimeStats retrievePlayerStatsByAccountId(Integer accountId) {
		try {
			// TODO: check if it's always "CURRENT"
			int id = client.invoke("playerStatsService", "retrievePlayerStatsByAccountId",
					new Object[] { accountId, "CURRENT" });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("retrievePlayerStatsByAccountId() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new PlayerLifetimeStats(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ChampionStatInfo> retrieveTopPlayedChampions(Integer accountId, String gameType /* "CLASSIC" */) {
		try {
			int id = client.invoke("playerStatsService", "retrieveTopPlayedChampions",
					new Object[] { accountId, gameType });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("retrieveTopPlayedChampions() error: " + result);
				return null;
			}
			
			Object[] data = result.getTO("data").getArray("body");
			if (data == null) {
				return null;
			}
			
			List<ChampionStatInfo> list = new ArrayList<ChampionStatInfo>();
			for (Object o : data) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					list.add(new ChampionStatInfo(ts));
				}
			}
			
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public AggregatedStats getAggregatedStats(Integer accountId, String gameMode /*CLASSIC*/) {
		try {
			// TODO: check if it's always "CURRENT"
			int id = client.invoke("playerStatsService", "getAggregatedStats",
					new Object[] { accountId, gameMode, "CURRENT" });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getAggregatedStats() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new AggregatedStats(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public RecentGames getRecentGames(Integer accountId) {
		try {
			// TODO: check if it's always "CURRENT"
			int id = client.invoke("playerStatsService", "getRecentGames", new Object[] { accountId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("getRecentGames() error: " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new RecentGames(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// inventory Service
	///////////////////////////////////////////////////
	
	@Override
	public SummonerActiveBoostsDTO getSumonerActiveBoosts() {
		try {
			int id = client.invoke("inventoryService", "getSumonerActiveBoosts", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error getSummonerActiveBoosts(): " + result);
				return null;
			}
			
			TypedObject data = result.getTO("data").getTO("body");
			if (data == null) {
				return null;
			}
			
			return new SummonerActiveBoostsDTO(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ChampionDTO> getAvailableChampions() {

		List<ChampionDTO> listChamps = new ArrayList<ChampionDTO>();

		try {
			int id = client.invoke("inventoryService", "getAvailableChampions", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error getAvailableChampions(): " + result);
				return listChamps;
			}
			
			Object[] champions = result.getTO("data").getArray("body");
			if (champions == null) {
				System.out.println("getAvailableChampions() error");
				return null;
			}
			
			// retrieve all champions
			for (Object o : champions) {
				TypedObject to = (TypedObject) o;
				if (to != null) {
					listChamps.add(new ChampionDTO(to));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listChamps;
	}
	
	@Override
	public List<String> retrieveInventoryTypes() {
		try {
			int id = client.invoke("inventoryService", "retrieveInventoryTypes", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
						
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error retrieveInventoryTypes(): " + result);
				return null;
			}
			
			Object[] objs = result.getTO("data").getArray("body");
			if (objs == null) {
				return null;
			}
			
			List<String> list = new ArrayList<String>();
			for (Object o : objs) {
				list.add((String)o);
			}
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<RuneCombiner> getAllRuneCombiners() {
		try {
			int id = client.invoke("inventoryService", "getAllRuneCombiners", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
						
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error getAllRuneCombiners(): " + result);
				return null;
			}
			
			Object[] objs = result.getTO("data").getArray("body");
			if (objs == null) {
				return null;
			}
			
			List<RuneCombiner> list = new ArrayList<RuneCombiner>();
			for (Object o : objs) {
				TypedObject tr = (TypedObject)o;
				if (tr != null) {
					list.add(new RuneCombiner(tr));
				}
			}
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<RuneQuantity> useRuneCombiner(Integer runeCombinerId, List<Rune> runes) {
		try {
			int id = client.invoke("inventoryService", "useRuneCombiner", 
					new Object[] { runeCombinerId, runes.toArray()});
			TypedObject result = client.getResult(id);
			client.join(id);
						
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error useRuneCombiner(): " + result);
				return null;
			}
			
			Object[] objs = result.getTO("data").getArray("body");
			if (objs == null) {
				return null;
			}
			
			List<RuneQuantity> list = new ArrayList<RuneQuantity>();
			for (Object o : objs) {
				TypedObject tr = (TypedObject)o;
				if (tr != null) {
					list.add(new RuneQuantity(tr));
				}
			}
			return list;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// GameMap Service
	///////////////////////////////////////////////////
	
	@Override
	public List<GameMap> getGameMapList() {
		List<GameMap> maps = new ArrayList<GameMap>();
		
		try {
			int id = client.invoke("gameMapService", "getGameMapList", new Object[]{});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("gameMapService() error 1: " + result);
				return maps;
			}
			
			Object[] objs = result.getTO("data").getArray("body");
			if (objs != null) {
				for (Object o : objs) {
					TypedObject tm = (TypedObject)o;
					if (tm != null) {
						maps.add(new GameMap(tm));
					}
				}
			}
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return maps;
	}
	
	///////////////////////////////////////////////////
	// masteryBook Service
	///////////////////////////////////////////////////
	
	@Override
	public MasteryBookDTO getMasteryBook(Double summonerId) {
		int id;
		try {
			id = client.invoke("masteryBookService", "getMasteryBook", new Object[] { summonerId });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null) {
				return null;
			}
			
			TypedObject to = result.getTO("data").getTO("body");
			if (to == null) {
				System.out.println("error retrieving mastery book for summoner " + summonerId);
				return null;
			}
			
			return new MasteryBookDTO(to);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public MasteryBookPageDTO selectDefaultMasteryBookPage(MasteryBookPageDTO page) {
		try {
			int id = client.invoke("masteryBookService", "selectDefaultMasteryBookPage", new Object[] { page.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("selectDefaultMasteryBookPage() error 1: " + result);
				return null;
			}
			
			TypedObject masteryPageRes = result.getTO("data").getTO("body");
			if (masteryPageRes == null) {
				System.out.println("selectDefaultMasteryBookPage() error 2: " + result);
				return null;
			}
			
			return new MasteryBookPageDTO(masteryPageRes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public MasteryBookDTO saveMasteryBook(MasteryBookDTO book) {
		try {
			int id = client.invoke("masteryBookService", "saveMasteryBook", new Object[] { book.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("saveMasteryBook() error 1: " + result);
				return null;
			}
			
			TypedObject masteryBookRes = result.getTO("data").getTO("body");
			if (masteryBookRes == null) {
				System.out.println("saveMasteryBook() error 2: " + result);
			}
			
			return new MasteryBookDTO(masteryBookRes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// spellBook Service
	///////////////////////////////////////////////////
	
	@Override
	public SpellBookDTO getSpellBook(Double summonerId) {
		int id;
		try {
			id = client.invoke("spellBookService", "getSpellBook", new Object[] { summonerId });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null) {
				return null;
			}
			
			TypedObject to = result.getTO("data").getTO("body");
			if (to == null) {
				System.out.println("error retrieving spell book for summoner " + summonerId);
				return null;
			}
			
			return new SpellBookDTO(to);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public SpellBookPageDTO selectDefaultSpellBookPage(SpellBookPageDTO page) {
		try {
			int id = client.invoke("spellBookService", "selectDefaultSpellBookPage", new Object[] { page.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("selectDefaultSpellBookPage() error 1: " + result);
				return null;
			}
			
			TypedObject spellPageRes = result.getTO("data").getTO("body");
			if (spellPageRes == null) {
				System.out.println("selectDefaultSpellBookPage() error 1: " + result);
				return null;
			}
			
			return new SpellBookPageDTO(spellPageRes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public 	SpellBookDTO saveSpellBook(SpellBookDTO book) {
		try {
			int id = client.invoke("spellBookService", "saveSpellBook", new Object[] { book.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("saveSpellBook() error 1: " + result);
				return null;
			}
			
			TypedObject spellBookRes = result.getTO("data").getTO("body");
			if (spellBookRes == null) {
				System.out.println("saveSpellBook() error 2: " + result);
			}
			
			return new SpellBookDTO(spellBookRes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	// summonerRune Service
	///////////////////////////////////////////////////
	
	@Override
	public SummonerRuneInventory getSummonerRuneInventory(Double summonerId) {
		try {
			int id = client.invoke("summonerRuneService", "getSummonerRuneInventory", new Object[] { summonerId });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null) {
				return null;
			}
			
			TypedObject to = result.getTO("data").getTO("body");
			if (to == null) {
				System.out.println("error retrieving summoner rune inventory for summoner " + summonerId);
				return null;
			}
			
			return new SummonerRuneInventory(to);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////
	// matchmakerService Service
	///////////////////////////////////////////////////

	@Override
	public List<GameQueueConfig> getAvailableQueues() {
		List<GameQueueConfig> listQueues = new ArrayList<GameQueueConfig>();
		
		try {
			int id = client.invoke("matchmakerService", "getAvailableQueues", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			// get queues list
			Object[] queues = result.getTO("data").getArray("body");
			
			if (queues == null) {
				System.out.println("getAvailableQueues() error");
				return null;
			}
			
			for (Object o : queues) {
				// create and add queueconfig object
				TypedObject to = (TypedObject)o;
				GameQueueConfig queue = new GameQueueConfig(to);
				listQueues.add(queue);
			}
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listQueues;
	}
	
	@Override
	public SearchingForMatchNotification attachToQueue(MatchMakerParams params) {
		try {
			// TODO: look TextEdit with a error for DODGE QUEUE
			int id = client.invoke("matchmakerService", "attachToQueue", new Object[] { params.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error attaching to queue: " + result);
				return null;
			}
			
			result = result.getTO("data").getTO("body");
			SearchingForMatchNotification res = new SearchingForMatchNotification(result);
			notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
		
			return res;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SearchingForMatchNotification attachTeamToQueue(MatchMakerParams params) {
		try {
			// TODO: look TextEdit with a error for DODGE QUEUE
			int id = client.invoke("matchmakerService", "attachTeamToQueue", new Object[] { params.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error attaching team to queue: " + result);
				return null;
			}
			
			result = result.getTO("data").getTO("body");
			SearchingForMatchNotification res = new SearchingForMatchNotification(result);
			notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
		
			return res;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResultMessage cancelFromQueueIfPossible(Double queueId) {
		try {
			int id = client.invoke("matchmakerService", "cancelFromQueueIfPossible", new Object[] { queueId });
			TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error") || result.getTO("data").get("body") == null) {
				System.out.println("Error cancelling queue: " + result);
				return ResultMessage.ERROR;
			}
			
			Boolean res = result.getTO("data").getBool("body");		
			return (res) ? ResultMessage.OK : ResultMessage.ERROR;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage acceptInviteForMatchmakingGame(String invitationId) {
		try {
			int id = client.invoke("matchmakerService", "acceptInviteForMatchmakingGame", new Object[] { invitationId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("acceptInviteForMatchmakingGame() msg: " + result);

			if (result == null || result.get("result").equals("_error")) {
				System.out.println("acceptInviteForMatchmakingGame() error: " + result);
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	///////////////////////////////////////////////////
	// game Service
	///////////////////////////////////////////////////
	
	@Override
	public 	PlatformGameLifecycleDTO retrieveInProgressSpectatorGameInfo(String summonerName) {
		try {
			int id = client.invoke("gameService", "retrieveInProgressSpectatorGameInfo", new Object[]{ summonerName });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("retrieveInProgressSpectatorGameInfo() message: " + result);
			
			 if (result == null || result.get("result") == null || result.get("result").equals("_error")) {
				System.out.println("retrieveInProgressSpectatorGameInfo() error 1");
				return null;
			 }
			 
			// check the result
			TypedObject obj = result.getTO("data").getTO("body");
			if (obj == null) {
				System.out.println("retrieveInProgressSpectatorGameInfo() error 2");
				return null;
			}
			
			return new PlatformGameLifecycleDTO(obj);
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResultMessage acceptPoppedGame(Boolean answer) {
		try {
			int id = client.invoke("gameService", "acceptPoppedGame", new Object[] { answer });
			// TODO: maybe I shoudn't wait and cancel the id, it seems to block sometimes
			client.cancel(id);
			/*TypedObject result = client.getResult(id);
			client.join(id);

			if (result == null || result.get("result").equals("_error")) {
				System.out.println("Error answering to queue");
				return ResultMessage.ERROR;
			}*/
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}

	@Override
	public List<PracticeGameSearchResult> listAllPracticeGames() {
		List<PracticeGameSearchResult> listPractGames = new ArrayList<PracticeGameSearchResult>();
		
		try {
			int id = client.invoke("gameService", "listAllPracticeGames", new Object[] {});
			TypedObject result = client.getResult(id);
			client.join(id);
			// get queues list
			Object[] games = result.getTO("data").getArray("body");
			
			if (games == null) {
				System.out.println("listAllPracticeGames() error");
				return null;
			}
			
			for (Object o : games) {
				TypedObject to = (TypedObject)o;
				PracticeGameSearchResult game = new PracticeGameSearchResult(to);
				listPractGames.add(game);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listPractGames;
	}
	
	@Override
	public ResultMessage joinGame(Double gameId, String pwd) {
		try {
			setState(GameState.WAITING);
			int id = client.invoke("gameService", "joinGame", new Object[]{ gameId, pwd });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("joinGame() message: " + result);
			
			if (result == null) {
				return ResultMessage.ERROR;
			}
			else if (result.get("result").equals("_error")) {
				 return ResultMessage.getResultMessageFromError(result);
			}
			
			// now we need to receive a message from it, but how?!
			System.out.println(result);
			System.out.println("join ok, now we need to receive a message");
			 
			TypedObject to = null;
			do {
				to = queue.poll();
				if (to == null) {
					System.out.println("sleep 20 ms");
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// if we got a message
				else {
					String type = to.getTO("data").getTO("body").type;
					// if it's not a GameDTO object, we keep waiting
					if (!type.equals(GameDTO.getTypeClass())) {
						to = null;
					}
					else {
						// create the game objwct
						GameDTO g = new GameDTO(to.getTO("data").getTO("body"));
						// check we got the correct game
						if (g.getId().equals(gameId)) {
							game = g;
							System.out.println("We got our game object!");
							System.out.println(game);
						}
						// wrong id, we didn't get the correct message
						else {
							to = null;
						}
					}
				}
			} while (to == null);
			
			// TODO: connect chat room
			MessagingManager.getInst().joinRoom(game);
			 
			// update state
			setState(GameState.TEAM_SELECT);
			notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ResultMessage.OK;
	}
	
	@Override
	public	ResultMessage switchPlayerToObserver(Double gameId) {
		try {
			int id = client.invoke("gameService", "switchPlayerToObserver", new Object[]{ gameId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("switchPlayerToObserver() message: " + result);
			
			 if (result == null || result.get("result").equals("_error")) {
				System.out.println("switchPlayerToObserver() error 1");
				return ResultMessage.ERROR;
			 }
			 
			// check the result
			Boolean res = result.getTO("data").getBool("body");
			return res ? ResultMessage.OK : ResultMessage.ERROR;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// whatTeam = 100 for team one and 200 for team two
	@Override
	public	ResultMessage switchObserverToPlayer(Double gameId, Integer whatTeam) {
		try {
			// TODO: check the second param !!
			int id = client.invoke("gameService", "switchObserverToPlayer", new Object[]{ gameId, whatTeam });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("switchObserverToPlayer() message: " + result);
			
			 if (result == null || result.get("result").equals("_error")) {
				System.out.println("switchObserverToPlayer() error 1");
				return ResultMessage.ERROR;
			 }
			 
			// check the result
			Boolean res = result.getTO("data").getBool("body");
			return res ? ResultMessage.OK : ResultMessage.ERROR;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public GameDTO observeGame(Double gameId) {
		try {
			int id = client.invoke("gameService", "observeGame", new Object[]{ gameId, null });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("observeGame() message: " + result);
			
			 if (result == null || result.get("result").equals("_error")) {
				System.out.println("observeGame() error 1");
				return null;
			 }
			 
			// get queues list
			TypedObject to = result.getTO("data").getTO("body");
			
			if (to == null) {
				System.out.println("observeGame() error 2");
				return null;
			}
	
			// get our game object
			game = new GameDTO(to);
			
			// TODO: connect chat room
			MessagingManager.getInst().joinRoom(game);
			 
			// update state
			setState(GameState.TEAM_SELECT);
			notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
		
			return game;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResultMessage quitGame() {
		try {
			int id = client.invoke("gameService", "quitGame", new Object[]{});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("quitGame() message: " + result);
			
			 if (result == null || result.get("result").equals("_error")) {
				 return ResultMessage.ERROR;
			 }
			 
			 // leave the chat
			 MessagingManager.getInst().leaveRoom(game);
			 
			 // good, we joined the game, now we need to receive a message from it, but how?!
			 System.out.println("quit ok");
			 setState(GameState.IDLE);
			 notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
			 return ResultMessage.OK;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public 	ResultMessage declineObserverReconnect() {
		try {
			int id = client.invoke("gameService", "declineObserverReconnect", new Object[]{});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("declineObserverReconnect() message: " + result);
			
			 if (result == null || result.get("result").equals("_error")) {
				 return ResultMessage.ERROR;
			 }
			 
			 // if ok, we can change state to back to normal
			 setState(GameState.IDLE);
			 notifyGameUpdated(null, ClientEventType.RETURNING_LOBBY);
			 return ResultMessage.OK;
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	
	// name = "fjxokt game", gameMode = "CLASSIC", pwd = "", allowSpectators = "ALL"
	@Override
	public GameDTO createPracticeGame(String name, String gameMode, GameMap map, Integer maxPlayers, Integer minPlayers, String pwd, String allowSpectators)
				throws PlayerAlreadyInGameException {
		try {
			// create a game config object
			PracticeGameConfig gameConf = new PracticeGameConfig(name, map, gameMode, pwd, maxPlayers, allowSpectators, 1);
			// call the service with it
			int id = client.invoke("gameService", "createPracticeGame", new Object[]{ gameConf.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null) {
				System.out.println("createPracticeGame() error 1");
				return null;
			}
			
			// TODO: can be error Too many players as well
			if (result.get("result") != null && result.get("result").equals("_error")) {
				throw new PlayerAlreadyInGameException(client.getErrorMessage(result));
			}
			
			// get queues list
			TypedObject to = result.getTO("data").getTO("body");
			
			if (to == null) {
				System.out.println("createPracticeGame() error 2");
				return null;
			}
			
			// now create the game object
			game = new GameDTO(to);
			System.out.println(game);
			
			setState(GameState.TEAM_SELECT);
			notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
			
			// TODO: connect chat room
			MessagingManager.getInst().joinRoom(game);
			
			return game;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public StartChampSelectDTO startChampionSelection(Double gameId, Integer numPlayers) {
		System.out.println("startChampionSelection() called: " + gameId + " : " + numPlayers);
		// TODO: figure out what is this num
		try {
			//int id = client.invoke("gameService", "startChampionSelection", new Object[] { gameId, numPlayers });
			// TODO: maybe the params are useless
			// TODO: switchteam
			int id = client.invoke("gameService", "startChampionSelection", new Object[] { game.getId(), game.getOptimisticLock() });
			TypedObject result = client.getResult(id);
			client.join(id);

			System.out.println("res chanpsel: "+ result);

			if (result == null || result.get("result").equals("_error")) {
				return null;
			}
			
			TypedObject typed = result.getTO("data").getTO("body");
			if (typed == null) {
				return null;
			}
			
			return new StartChampSelectDTO(typed);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ResultMessage switchTeams(Double gameId) {
		try {
			int id = client.invoke("gameService", "switchTeams", new Object[] { game.getId() });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.getTO("data").get("body") == null) {
				return ResultMessage.ERROR;
			}
						
			boolean switchOk = result.getTO("data").getBool("body");
			return (switchOk) ? ResultMessage.OK : ResultMessage.ERROR;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage selectChampion(Integer championId) {
		System.out.println("selectChampion() called");
		try {
			int id = client.invoke("gameService", "selectChampion", new Object[] { championId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println("SELECTCHAMPRES: " + result);
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public	ResultMessage selectChampionSkin(Integer championId, Integer skinId) {
		try {
			int id = client.invoke("gameService", "selectChampionSkin", new Object[] { championId, skinId });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage selectSpells(Integer spell1, Integer spell2) {
		try {
			int id = client.invoke("gameService", "selectSpells", new Object[] { spell1, spell2 });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println(result);
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage selectBotChampion(Integer championId, BotParticipant bot) {
		try {
			// TODO: how to get a complet bot object ?!
			int id = client.invoke("gameService", "selectBotChampion", new Object[] { championId, bot });
			TypedObject result = client.getResult(id);
			client.join(id);
			
			System.out.println(result);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage removeBotChampion(Integer championId, BotParticipant bot) {
		try {
			int id = client.invoke("gameService", "removeBotChampion", new Object[] { championId, bot.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
						
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage banUserFromGame(Double gameId, Integer accountId) {
		try {
			int id = client.invoke("gameService", "banUserFromGame", new Object[] { gameId, accountId });
			TypedObject result = client.getResult(id);
			client.join(id);
												
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}

	@Override
	public ResultMessage banObserverFromGame(Double gameId, Integer accountId) {
		try {
			int id = client.invoke("gameService", "banObserverFromGame", new Object[] { gameId, accountId });
			TypedObject result = client.getResult(id);
			client.join(id);
												
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage sendChampionTradeMessage(ChampionTradeMessage msg) {
		try {
			int id = client.invoke("gameService", "sendChampionTradeMessage", new Object[] { msg.getTypedObject() });
			TypedObject result = client.getResult(id);
			client.join(id);
												
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override 
	public ResultMessage tradeChampion(String playerName) {
		try {
			int id = client.invoke("gameService", "tradeChampion", new Object[] { playerName });
			TypedObject result = client.getResult(id);
			client.join(id);
												
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage championSelectCompleted() {
		try {
			int id = client.invoke("gameService", "championSelectCompleted", new Object[]{});
			TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage setClientReceivedGameMessage(Double gameId, GameState state) {
		System.out.println("setClientReceivedGameMessage() called");
		// TODO: fix this mess
		try {
			System.out.println("no error 0");

			int id = client.invoke("gameService", "setClientReceivedGameMessage", new Object[] { gameId, state.getName() });
			System.out.println("no error 1 - id = " + id);
			//TypedObject result = client.getResult(id);
			client.cancel(id);

			//System.out.println(result);
			//System.out.println("between 1 and 2");
			//client.join(id);

			//if (result == null || result.get("result").equals("_error")) {
			//	return ResultMessage.ERROR;
			//}
			
			System.out.println("no error 2");
			
			//client.join(id);
			System.out.println("no error 3");

			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage setClientReceivedMaestroMessage(Double gameId, GameState state) {
		try {
			System.out.println("setClientReceivedMaestroMessage() called");

			int id = client.invoke("gameService", "setClientReceivedMaestroMessage", new Object[] {gameId, state.getName()});
			//TypedObject result = client.getResult(id);
			//client.join(id);
			client.cancel(id);
			
			//if (result == null || result.get("result").equals("_error")) {
			//	return ResultMessage.ERROR;
			//}
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
	}
	
	@Override
	public ResultMessage getLatestGameTimerState(Double gameId, GameState state, Integer numPlayers) {
		try {
			System.out.println(gameId + " : " + state.getName() + " : " + numPlayers);
			int id = client.invoke("gameService", "getLatestGameTimerState", new Object[] {gameId, state.getName(), numPlayers});
			client.cancel(id);
			/*TypedObject result = client.getResult(id);
			client.join(id);
			
			if (result == null || result.get("result").equals("_error")) {
				return ResultMessage.ERROR;
			}
			
			TypedObject gameTo = result.getTO("data").getTO("body");
			if (gameTo == null) {
				return ResultMessage.ERROR;
			}
			
			game = new GameDTO(gameTo);
			System.out.println("getLatestGameTimerState() result:");
			System.out.println(game);*/
			
			return ResultMessage.OK;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultMessage.ERROR;
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
		if (type.equals(GameDTO.getTypeClass())) {
			
			//TypedObject r = queue.poll();
			if (result.getTO("data").getTO("body") == null) {
				System.out.println("BIG ERRRRRRRRRRRROOORRRR");
			}
			// used to know if team switch of join/left
			int oldNbPlayers = (game == null) ? 0 : game.getNumPlayers();
			// update our game object
			game = new GameDTO(result.getTO("data").getTO("body"));
			// get the new game state
			GameState newState = GameState.getStateFromString(game.getGameState());
			
			if (status.equals(GameState.IDLE)) {
				if (newState.equals(GameState.TEAM_SELECT)) {
					System.out.println("from status IDLE to JOINING_TEAM_SELECT");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.JOINING_TEAM_SELECT);
				}
				else if (newState.equals(GameState.JOINING_CHAMP_SELECT)) {
					setState(GameState.JOINING_CHAMP_SELECT);
					notifyGameUpdated(game, ClientEventType.JOINING_MATCHMAKING);
				}
			}
			// team select state
			if (status.equals(GameState.TEAM_SELECT)) {
				if (newState.equals(GameState.TEAM_SELECT)) {
					if (oldNbPlayers == game.getNumPlayers()) {
						System.out.println("Someone switched team");
					}
					else if (oldNbPlayers > game.getNumPlayers()) {
						System.out.println("Someone left the team select. New num: " + game.getNumPlayers());
					}
					else {
						System.out.println("Someone joined the team select. New num: " + game.getNumPlayers());
					}
					notifyGameUpdated(game, ClientEventType.TEAM_SELECT_UPDATE);
				}
				else if (newState.equals(GameState.CHAMP_SELECT)) {
					goToChampSelectState();
				}
				else if (newState.equals(GameState.TERMINATED)) {
					TerminatedCondition reason = TerminatedCondition.getStateFromString(game.getTerminatedCondition());
					System.out.println("Game terminated: reason: " + reason);
					setState(GameState.IDLE);
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
				}
			}
			// wait for game queue completed
			else if (status.equals(GameState.JOINING_CHAMP_SELECT)) {
				if (newState.equals(GameState.JOINING_CHAMP_SELECT)) {
					System.out.println("update status of participants: " + game.getStatusOfParticipants());
					notifyGameUpdated(game, ClientEventType.MATCHMAKING_UPDATE);
				}
				else if (newState.equals(GameState.CHAMP_SELECT)) {
					goToChampSelectState();
				}
				else if (newState.equals(GameState.TERMINATED)) {
					setState(GameState.IDLE);
					System.out.println("Someone cancel queue, not in queue anymore");
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
				}
			}
			// champ select state
			else if (status.equals(GameState.CHAMP_SELECT)) {
				if (newState.equals(GameState.TEAM_SELECT)) {
					System.out.println("Someone left the champ selection room, back to team select");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.RETURNING_TEAM_SELECT);
				}
				else if (newState.equals(GameState.CHAMP_SELECT)) {
					System.out.println("Someone picking a champ ???");
					notifyGameUpdated(game, ClientEventType.CHAMP_SELECT_UPDATE);
				}
				else if (newState.equals(GameState.POST_CHAMP_SELECT)) {
					System.out.println("Champions have been locked, waiting for the go...");
					setState(newState);
					notifyGameUpdated(game, ClientEventType.JOINING_POST_CHAMPION_SELECT);
				}
			}
			// champ post select
			else if (status.equals(GameState.POST_CHAMP_SELECT)) {
				if (newState.equals(GameState.TEAM_SELECT)) {
					System.out.println("Someone left the champ selection room, back to team select");
					setState(GameState.TEAM_SELECT);
					notifyGameUpdated(game, ClientEventType.RETURNING_TEAM_SELECT);
				}
				else if (newState.equals(GameState.START_REQUESTED)) {
					System.out.println("start requested, time to send our last messages...");
					setState(GameState.START_REQUESTED);
					// we wait for the credentials to be send
				}
				else {
					// what other status can we get ?
					// apparently its just if people change their summoner spells
					System.out.println("CHECK THIS PART IF I GET DISPLAYED !");
					notifyGameUpdated(game, ClientEventType.POST_CHAMPION_SELECT);
				}
			}
			// post game
			else if (status.equals(GameState.GameClientConnectedToServer)
					|| status.equals(GameState.GAME_IN_PROGRESS)) {
				// end of game notif (crash or normal end of game)
				// get the game state
				GameState state = GameState.getStateFromString(game.getGameState());
				// game is over
				if (state.equals(GameState.TERMINATED)) {
					// game over, we will receive stats message (ok not ?? don't think so)
					setState(GameState.POST_GAME);
				}
				// game finished for another reason (GameState.TERMINATED_IN_ERROR I guess)
				else {
					// read the readon in gamedto object
					TerminatedCondition reason = TerminatedCondition.getStateFromString(game.getTerminatedCondition());
					System.out.println("Game ended: reason: " + game.getTerminatedCondition() + " : " + reason);
					// TODO: apparently its whem someone leave riiiiight before it's too late, show go back in CHAMP_SELECT
					// but not sure: what about if I was using matchmaking ???
					// should i go back in queue from here ?
					// now i'll just notify back to normal
					notifyClientUpdate(ClientEventType.RETURNING_LOBBY);
				}
			}
		}
		////////////////////////////////////////////////////////////////////////////////
		// GameNotification object
		////////////////////////////////////////////////////////////////////////////////
		// TODO: handle that!
		else if (type.equals(GameNotification.getTypeClass())) {
			GameNotification notif = new GameNotification(result.getTO("data").getTO("body"));
			// get the type of notif
			GameNotificationType notifType = GameNotificationType.getStateFromString(notif.getType());
			// if we were in team select
			if (status.equals(GameState.TEAM_SELECT)) {
				if (notifType.equals(GameNotificationType.PLAYER_BANNED_FROM_GAME)) {
					System.out.println("you have been banned from game, return to idle state...");
					// leave chat
					MessagingManager.getInst().leaveRoom(game);
					// update state
					setState(GameState.IDLE);
					notifyClientUpdate(ClientEventType.BANNED_FROM_GAME);
				}
			}
			else if (status.equals(GameState.IDLE)) {
				// someone left the team invite, means the searching for match is over
				if (notifType.equals(GameNotificationType.PLAYER_QUIT)) {
					System.out.println("Someone left the team, back to idle. TODOOO!!!");
					// TODO
				}
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
			if (msgType.equals(ChampionTradeMessageType.CHAMPION_TRADE_REQUEST))  {
				notifyClientUpdate(ClientEventType.TRADE_CHAMP_REQUEST, msg);
			}
			// someone accepted my trade, time to confirm it
			else if (msgType.equals(ChampionTradeMessageType.CHAMPION_TRADE_ACCEPT)) {
				// TODO: call tradeChampion with the name of the player we accepted
				tradeChampion(msg.getSenderInternalSummonerName());
			}
			else if (msgType.equals(ChampionTradeMessageType.CHAMPION_TRADE_CANCELLED)) {
				notifyClientUpdate(ClientEventType.TRADE_CHAMP_CANCEL, msg);
			}
			else if (msgType.equals(ChampionTradeMessageType.CHAMPION_TRADE_NOT_ALLOWED)) {
				notifyClientUpdate(ClientEventType.TRADE_CHAMP_NOT_ALLOWED, msg);
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
		// StoreAccountBalanceNotification object (after playing a game or buying a champ for instance)
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
