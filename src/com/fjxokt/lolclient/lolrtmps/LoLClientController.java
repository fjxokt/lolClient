package com.fjxokt.lolclient.lolrtmps;

import java.util.List;

import com.fjxokt.lolclient.lolrtmps.exceptions.PlayerAlreadyInGameException;
import com.fjxokt.lolclient.lolrtmps.model.AggregatedStats;
import com.fjxokt.lolclient.lolrtmps.model.AllSummonerData;
import com.fjxokt.lolclient.lolrtmps.model.BotParticipant;
import com.fjxokt.lolclient.lolrtmps.model.ChampionStatInfo;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.fjxokt.lolclient.lolrtmps.model.GameQueueConfig;
import com.fjxokt.lolclient.lolrtmps.model.LoginDataPacket;
import com.fjxokt.lolclient.lolrtmps.model.MatchMakerParams;
import com.fjxokt.lolclient.lolrtmps.model.PlayerLifetimeStats;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.model.RecentGames;
import com.fjxokt.lolclient.lolrtmps.model.Rune;
import com.fjxokt.lolclient.lolrtmps.model.RuneCombiner;
import com.fjxokt.lolclient.lolrtmps.model.RuneQuantity;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.SummonerRuneInventory;
import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.fjxokt.lolclient.lolrtmps.model.dto.AllPublicSummonerDataDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.LeagueListDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.StartChampSelectDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerActiveBoostsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerIconInventoryDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerLeaguesDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamAggregatedStatsDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameMode;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.PlayerBaseLevel;
import com.fjxokt.lolclient.lolrtmps.model.utils.QueueType;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.gvaneyck.rtmp.Callback;

public interface LoLClientController extends Callback {
	
	// init method
	void initUser(String region, String clientVersion, String user, String pass);
	String getUser();
	String getPassword();
	
	///////////////////////////////////////////////////
	// login Service
	///////////////////////////////////////////////////
	ResultMessage login();
	void logout();
	boolean isLoggedIn();
	String getRegion();
	String getStoreUrl();
	Integer getAccountId();
	
	///////////////////////////////////////////////////
	// summonerTeam Service
	///////////////////////////////////////////////////
	PlayerDTO createPlayer();
	TeamDTO createTeam(String teamName, String teamTag);
	TeamDTO invitePlayer(Double summonerId, TeamId teamId);
	TeamDTO findTeamById(TeamId teamId);
	TeamDTO findTeamByTag(String tag);
	TeamDTO findTeamByName(String name);
	TeamDTO disbandTeam(TeamId teamId);
	Boolean isNameValidAndAvailable(String teamName);
	Boolean isTagValidAndAvailable(String tagName);
	
	///////////////////////////////////////////////////
	// clientFacade Service
	///////////////////////////////////////////////////
	LoginDataPacket getLoginDataPacketForUser();
	Integer[] callKudos(String commandName, Double summonerId);
	
	///////////////////////////////////////////////////
	// summoner Service
	///////////////////////////////////////////////////
	AllSummonerData createDefaultSummoner(String summonerName);
	ResultMessage updateProfileIconId(Integer iconId);
	ResultMessage saveSeenTutorialFlag();
	PublicSummoner getSummonerByName(String name);
	String getSummonerInternalNameByName(String summonerName);
	List<String> getSummonerNames(Integer[] summonerIds);
	AllPublicSummonerDataDTO getAllPublicSummonerDataByAccount(Integer accountId);
	// TODO: getAllSummonerDataByAccount
	
	///////////////////////////////////////////////////
	// leagues Service Proxy
	///////////////////////////////////////////////////
	SummonerLeaguesDTO getAllMyLeagues();
	SummonerLeaguesDTO getAllLeaguesForPlayer(Double summonerId);
	SummonerLeaguesDTO getLeaguesForTeam(String teamId);
	LeagueListDTO getChallengerLeague(QueueType type);
	
	///////////////////////////////////////////////////
	// summonerIcon Service
	///////////////////////////////////////////////////
	SummonerIconInventoryDTO getSummonerIconInventory(Double summonerId);
	
	///////////////////////////////////////////////////
	// playerStats Service
	///////////////////////////////////////////////////
	ResultMessage processEloQuestionaire(PlayerBaseLevel level);
	PlayerLifetimeStats retrievePlayerStatsByAccountId(Integer accountId);
	List<ChampionStatInfo> retrieveTopPlayedChampions(Integer accountId, GameMode mode);
	AggregatedStats getAggregatedStats(Integer accountId, GameMode mode);
	List<TeamAggregatedStatsDTO> getTeamAggregatedStats(String teamId);
	EndOfGameStats getTeamEndOfGameStats(TeamId teamId, Double gameId);
	RecentGames getRecentGames(Integer accountId);
	
	///////////////////////////////////////////////////
	// inventory Service
	///////////////////////////////////////////////////
	SummonerActiveBoostsDTO getSumonerActiveBoosts();
	List<ChampionDTO> getAvailableChampions();
	List<String> retrieveInventoryTypes();
	List<RuneCombiner> getAllRuneCombiners();
	// TODO: Rune has to be "serializable" (as they are sent to the server)
	List<RuneQuantity> useRuneCombiner(Integer runeCombinerId, List<Rune> runes);
	// findChampionById : doesn't work
	// isStoreEnabled(no params) : doesn't work
	
	///////////////////////////////////////////////////
	// GameMap Service
	///////////////////////////////////////////////////
	List<GameMap> getGameMapList();
	// TODO: getGameMapSet(no params) , doesn't seems to exist
	
	///////////////////////////////////////////////////
	// masteryBook Service
	///////////////////////////////////////////////////
	MasteryBookDTO getMasteryBook(Double summonerId);
	MasteryBookDTO saveMasteryBook(MasteryBookDTO book);
	
	///////////////////////////////////////////////////
	// spellBook Service
	///////////////////////////////////////////////////
	SpellBookDTO getSpellBook(Double summonerId);
	SpellBookPageDTO selectDefaultSpellBookPage(SpellBookPageDTO page);
	SpellBookDTO saveSpellBook(SpellBookDTO book);

	///////////////////////////////////////////////////
	// summonerRune Service
	///////////////////////////////////////////////////
	SummonerRuneInventory getSummonerRuneInventory(Double summonerId);
	
	///////////////////////////////////////////////////
	// matchmakerService Service
	///////////////////////////////////////////////////
	List<GameQueueConfig> getAvailableQueues();
	SearchingForMatchNotification attachToQueue(MatchMakerParams params);
	SearchingForMatchNotification attachTeamToQueue(MatchMakerParams params);
	ResultMessage cancelFromQueueIfPossible(Double queueId);
	ResultMessage acceptInviteForMatchmakingGame(String invitationId);

	///////////////////////////////////////////////////
	// game Service
	///////////////////////////////////////////////////
	PlatformGameLifecycleDTO retrieveInProgressSpectatorGameInfo(String summonerName);
	ResultMessage acceptPoppedGame(Boolean answer);
	// TODO: getAuditInfo(no params)
	List<PracticeGameSearchResult> listAllPracticeGames();
	ResultMessage joinGame(Double gameId, String pwd);
	ResultMessage switchPlayerToObserver(Double gameId);
	ResultMessage switchObserverToPlayer(Double gameId, Integer whatTeam);
	GameDTO observeGame(Double gameId);
	ResultMessage quitGame();
	ResultMessage declineObserverReconnect();
	GameDTO createPracticeGame(String name, String gameMode, GameMap map, Integer maxPlayers, Integer minPlayers, String pwd, String type) throws PlayerAlreadyInGameException;
	StartChampSelectDTO startChampionSelection(Double gameId, Integer numPlayers);
	ResultMessage switchTeams(Double gameId);
	ResultMessage selectChampion(Integer championId);
	ResultMessage selectChampionSkin(Integer championId, Integer skinId);
	ResultMessage selectSpells(Integer spell1, Integer spell2);
	ResultMessage selectBotChampion(Integer championId, BotParticipant bot);
	ResultMessage removeBotChampion(Integer championId, BotParticipant bot);
	ResultMessage banUserFromGame(Double gameId, Integer accountId);
	ResultMessage banObserverFromGame(Double gameId, Integer accountId);
	ResultMessage sendChampionTradeMessage(ChampionTradeMessage msg);
	ResultMessage tradeChampion(String playerName);
	ResultMessage championSelectCompleted();
	// private ?
	ResultMessage setClientReceivedGameMessage(Double gameId, GameState state);
	ResultMessage setClientReceivedMaestroMessage(Double gameId, GameState state);
	ResultMessage getLatestGameTimerState(Double gameId, GameState state, Integer numPlayers);
	
}
