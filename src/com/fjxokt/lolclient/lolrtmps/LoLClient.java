package com.fjxokt.lolclient.lolrtmps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.AllSummonerData;
import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.fjxokt.lolclient.lolrtmps.model.GameObserver;
import com.fjxokt.lolclient.lolrtmps.model.GameQueueConfig;
import com.fjxokt.lolclient.lolrtmps.model.LoginDataPacket;
import com.fjxokt.lolclient.lolrtmps.model.MatchMakerParams;
import com.fjxokt.lolclient.lolrtmps.model.Participant;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.model.Summoner;
import com.fjxokt.lolclient.lolrtmps.model.SummonerRuneInventory;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionSkinDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameTypeConfigDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerChampionSelectionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.PlayerBaseLevel;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.messaging.Invitation;
import com.fjxokt.lolclient.messaging.InvitationListener;
import com.fjxokt.lolclient.messaging.InvitationManager;
import com.fjxokt.lolclient.messaging.MatchmakingInvitation;
import com.fjxokt.lolclient.messaging.MessagingManager;


public class LoLClient extends LoLClientControllerImpl implements ClientListener, InvitationListener {
	
	private static LoLClient instance;
	
	// region
	public static final HashMap<String, String> regions = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
	{
		put("NORTH AMERICA", "NA");
	    put("EUROPE WEST", "EUW");
	    put("EUROPE NORDIC & EAST", "EUN");
	    put("KOREA", "KR");
	    put("BRAZIL", "BR");
	    put("TURKEY", "TR");
	    put("PUBLIC BETA ENVIRONMENT", "PBE");
	    put("SINGAPORE/MALAYSIA", "SG");
	    put("TAIWAN", "TW");
	    put("THAILAND", "TH");
	    put("PHILLIPINES", "PH");
	    put("VIETNAM", "VN");
	}};
	
	
	// chat manager
	public MessagingManager messageManager = MessagingManager.getInst();
		
	// Game data
	private LoginDataPacket loginDataPacket;
	private List<GameQueueConfig> availableQueues = null;
	private List<ChampionDTO> availableChampions = null;
	private List<GameMap> gameMaps = null;
	private SummonerRuneInventory summonerRuneInventory;
	private MasteryBookDTO masteryBook;
	private SpellBookDTO spellBook;
	private PlayerDTO player;
	
	// class specific objects
	private boolean isInMatchMaking;

	private LoLClient() {
		super();
	}
	
	public static LoLClient getInst() {
		if (instance == null) {
			instance = new LoLClient();
		}
		return instance;
	}
	
	@Override
	public ResultMessage login() {
		ResultMessage r = super.login();
		if (r.equals(ResultMessage.OK)) {
			initData();
		}
		return r;
	}
	
	private void initData() {
		// get login data infos
		loginDataPacket = super.getLoginDataPacketForUser();
		
		// check if new summoner
		if (loginDataPacket.getAllSummonerData() == null) {
			// we have a new summoner
			notifyClientUpdate(ClientEventType.LOGIN_NEW_SUMMONER);
		}
		else {
			initDataNext();
		}
		
		// connect to chat service
		// na:chat.na1.lol.riotgames.com
		// euw: chat.eu.lol.riotgames.com
		messageManager.init("chat.na1.lol.riotgames.com", 5223);
		boolean loggedToChat = messageManager.login(getUser(), getPassword());
		if (!loggedToChat) {
			System.out.println("Could not connect to chat");
		}
		else {
			// register client to get invitation messages
			//messageManager.addInvitationListener(this);
			InvitationManager.getInst().addInvitationListener(this);
		}
	}
	
	private void initDataNext() {
		// get queues list
		availableQueues = getAvailableQueues();
		// get champions list
		availableChampions = getAvailableChampions();
		// get rune inventory
		summonerRuneInventory = getSummonerRuneInventory(loginDataPacket.getAllSummonerData().getSummoner().getSumId());
		// get mastery book
		masteryBook = loginDataPacket.getAllSummonerData().getMasteryBook();
		// get spell book
		spellBook = loginDataPacket.getAllSummonerData().getSpellBook();
		// create player
		player = createPlayer();
		System.out.println(getRecentGames(loginDataPacket.getAllSummonerData().getSummoner().getAcctId().intValue()));
	}

	/////////////////////
	// Override
	/////////////////////
	
	@Override
	public LoginDataPacket getLoginDataPacketForUser() {
		return loginDataPacket;
	}
	
	@Override
	public List<ChampionDTO> getAvailableChampions() {
		if (availableChampions == null) {
			availableChampions = super.getAvailableChampions();
		}
		return availableChampions;
	}
	
	@Override
	public List<GameQueueConfig> getAvailableQueues() {
		if (availableQueues == null) {
			availableQueues = super.getAvailableQueues();
		}
		return availableQueues;
	}
	
	@Override
	public List<GameMap> getGameMapList() {
		if (gameMaps == null) {
			gameMaps = super.getGameMapList();
		}
		return gameMaps;
	}
	
	public SpellBookPageDTO selectDefaultSpellBookPage(SpellBookPageDTO page) {
		SpellBookPageDTO res = super.selectDefaultSpellBookPage(page);
		// if the page has been correctly changed, update our list
		if (res != null) {
			for (SpellBookPageDTO p : spellBook.getBookPages()) {
				p.setCurrent(p.getPageId().equals(page.getPageId()));
			}
		}
		return res;
	}
	
	public MasteryBookPageDTO selectDefaultMasteryBookPage(MasteryBookPageDTO page) {
		MasteryBookPageDTO res = super.selectDefaultMasteryBookPage(page);
		// if the page has been correctly changed, update our list
		if (res != null) {
			for (MasteryBookPageDTO p : masteryBook.getBookPages()) {
				p.setCurrent(p.getPageId().equals(page.getPageId()));
			}
		}
		return res;
	}
	
	/////////////////////
	// utils
	////////////////////
	
	///////////////
	// invitations
	///////////////

	public void invitationAccepted(String invitationId, String username, String queueId) {
		System.out.println(" beforesum name");

		PublicSummoner sum = getSummonerByName(username);
		System.out.println("sum name: " + sum);

		Double myId = getLoginDataPacketForUser().getAllSummonerData().getSummoner().getSumId();
		MatchMakerParams params = new MatchMakerParams(new Integer[]{Integer.parseInt(queueId)},
					invitationId, new Integer[]{sum.getSummonerId(), myId.intValue()});
		System.out.println("mmparams: " + params);
		System.out.println(params.getTypedObject());
		System.out.println("ECHO: "+attachTeamToQueue(params));
	}
	
	/////////////////
	
	public ResultMessage createNewSummoner(String summonerName, PlayerBaseLevel lvl) {
		AllSummonerData data = super.createDefaultSummoner(summonerName);
		// if coud not create user (name invalid or already used)
		if (data == null) {
			return ResultMessage.ERROR;
		}
		loginDataPacket.setAllSummonerData(data);
		// TODO: we should set the summoner icon
		//updateProfileIconId(2);
		processEloQuestionaire(lvl);
		saveSeenTutorialFlag();
		// continue init;
		initDataNext();
		// everything went ok
		return ResultMessage.OK;
	}
	
	public SummonerRuneInventory getSummonerRuneInventory() {
		return summonerRuneInventory;
	}
	
	public MasteryBookDTO getMasteryBook() {
		return masteryBook;
	}
	
	public SpellBookDTO getSpellBook() {
		return spellBook;
	}
	
	public List<ChampionSkinDTO> getSkinsForChampion(Integer championId) {
		for (ChampionDTO champ : getAvailableChampions()) {
			if (champ.getChampionId().equals(championId)) {
				return champ.getChampionSkins();
			}
		}
		return null;
	}
	
	public Summoner getMySummoner() {
		if (loginDataPacket.getAllSummonerData() == null) {
			return null;
		}
		return loginDataPacket.getAllSummonerData().getSummoner();
	}
	
	public PlayerDTO getPlayer() {
		return player;
	}
	
	public boolean isInMatchMaking() {
		return isInMatchMaking;
	}
	
	public SpellBookPageDTO getDefaultSpellBookPage() {
		for (SpellBookPageDTO p : spellBook.getBookPages()) {
			if (p.getCurrent()) {
				return p;
			}
		}
		return null;
	}
	
	public MasteryBookPageDTO getDefaultMasteryBookPage() {
		for (MasteryBookPageDTO p : masteryBook.getBookPages()) {
			if (p.getCurrent()) {
				return p;
			}
		}
		return null;
	}
	
	public GameDTO getCurrentGame() {
		return game;
	}
	
	public ResultMessage switchTeams() {
		if (game != null) {
			return switchTeams(game.getId());
		}
		return ResultMessage.ERROR;
	}
	
	public List<Participant> getMyTeam() {
		return getMyTeam(game);
	}
	
	// return my team or teamTwo if I'm observer
	public List<Participant> getMyTeam(GameDTO game) {
		if (isObserver()) {
			return null;
		}
		String myName = loginDataPacket.getAllSummonerData().getSummoner().getInternalName();
		for (Participant p : game.getTeamOne()) {
			// we check for null because bot don't have internal name
			if (p.getSummonerInternalName() != null && p.getSummonerInternalName().equals(myName)) {				
				return game.getTeamOne();
			}
		}
		return game.getTeamTwo();
	}
	
	public List<Participant> getOtherTeam() {
		return getOtherTeam(game);
	}
	
	// return other team or teamOne if I'm observer
	public List<Participant> getOtherTeam(GameDTO game) {
		if (isObserver()) {
			return null;
		}
		String myName = loginDataPacket.getAllSummonerData().getSummoner().getInternalName();
		for (Participant p : game.getTeamOne()) {
			if (p.getSummonerInternalName() != null && p.getSummonerInternalName().equals(myName)) {
				return game.getTeamTwo();
			}
		}
		return game.getTeamOne();
	}
	
	public PlayerChampionSelectionDTO getMyPlayer() {
		return getMyPlayer(game);
	}
	
	public PlayerChampionSelectionDTO getMyPlayer(GameDTO game) {
		String myName = loginDataPacket.getAllSummonerData().getSummoner().getInternalName();
		for (PlayerChampionSelectionDTO pl : game.getPlayerChampionSelections()) {
			if (pl.getSummonerInternalName().equals(myName)) {
				return pl;
			}
		}
		return null;
	}
	
	public boolean isPlayer() {
		return !isObserver();
	}
	
	public boolean isObserver() {
		String myName = loginDataPacket.getAllSummonerData().getSummoner().getInternalName();
		for (GameObserver obs : game.getObservers()) {
			if (obs.getSummonerInternalName().equals(myName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isGameOwner() {
		String myName = loginDataPacket.getAllSummonerData().getSummoner().getInternalName();
		Participant owner = game.getOwnerSummary();
		if (owner == null) {
			return false;
		}
		return myName.equals(owner.getSummonerInternalName());
	}

	public Boolean isChampionSelected(GameDTO game, Integer championId) {
		for (PlayerChampionSelectionDTO pl : game.getPlayerChampionSelections()) {
			if (pl.getChampionId().equals(championId)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public GameTypeConfigDTO getGameTypeConfig(Integer configId) {
		for (GameTypeConfigDTO conf : loginDataPacket.getGameTypeConfigs()) {
			if (conf.getId().equals(configId)) {
				return conf;
			}
		}
		return null;
	}
	
	// return the list of champ available for this game
	public List<ChampionDTO> getAvailableChampions(GameDTO game) {
		// get the champs
		List<ChampionDTO> champs = new ArrayList<ChampionDTO>(getAvailableChampions());
		// inactive champs switch
		// TODO: check that champ.isActive() is enough to know what champs are active or not
//		List<Integer> inactiveChamps = getLoginDataPacketForUser().getClientSystemStates().getInactiveChampionIdList();
		
		Iterator<ChampionDTO> iterator = champs.iterator();
		while (iterator.hasNext()) {
			ChampionDTO champ = iterator.next();
			// if champ inactive or if I don't own him
			if (!champ.isActive() || (!champ.isFreeToPlay() && !champ.isOwned()) /*|| inactiveChamps.contains(champ.getChampionId())*/) {
				iterator.remove();
			}
		}
		
		return champs;
	}
	
	//////////////////////////////////
	// Events handling
	//////////////////////////////////

	@Override
	public void clientStateUpdated(ClientEvent e) {
		switch (e.getType()) {
		case JOINING_MATCHMAKING:
			isInMatchMaking = true;
			break;
		case RETURNING_LOBBY:
			isInMatchMaking = false;
			break;
		case BALANCE_NOTIF:
			loginDataPacket.setIpBalance(e.getBalance().getIp());
			loginDataPacket.setRpBalance(e.getBalance().getRp());
			break;
		}
		
	}
	
	///////////////////////////////
	// Invitation listeners
	///////////////////////////////

	@Override
	public void invitationAccepted(String userId, Invitation inv) {
		// TODO Auto-generated method stub
		System.out.println("INVITATION ACCEPTED !");
		if (inv instanceof MatchmakingInvitation) {
			MatchmakingInvitation minv = (MatchmakingInvitation)inv;
			// TODO: get the correct userId or userName.....
			invitationAccepted(""+inv.getInviteId(), MessagingManager.getInst().getBuddyFromId(userId).getName(), ""+minv.getQueueId());
		}
	}

	@Override
	public void invitationReceived(String userId, Invitation invitation) {
		// TODO Auto-generated method stub
		System.out.println("Received invitation from " + userId + " : " + invitation);
	}

	@Override
	public void invitationRejected(String userId, Invitation invitation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invitationStatus(String msg) {
		// TODO Auto-generated method stub
		System.out.println("STATUS°: " + msg);
	}

}
