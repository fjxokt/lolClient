package com.fjxokt.lolclient.lolrtmps.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.GameObserver;
import com.fjxokt.lolclient.lolrtmps.model.Participant;
import com.gvaneyck.rtmp.TypedObject;

public class GameDTO extends ClassType {

	private Double id;
	private String spectatorsAllowed;		// "ALL"
	private boolean passwordSet;
	private String gameMode;				// "CLASSIC"
	private Integer mapId;
	private String gameType;				// "PRACTICE_GAME"
	private Integer gameTypeConfigId;		// 1
	private String gameState; 				// "TEAM_SELECT"
	private String gameStateString;			// "TEAM_SELECT" (same... useful?)
	private Participant ownerSummary;
	private String statusOfParticipants;	//list of 0 and 1 and 2 (0 = waiting for answer, 1 = said ok, 2 = said no)
	private Integer dataVersion;			// 0
	private String name;					// "Jabe's game"
	private Integer spectatorDelay;			// 180
	private Date creationTime;
	private List<Participant> teamOne;
	private List<Participant> teamTwo;
	private List<GameObserver> observers;
	private List<Object> bannedChampions;	// TODO: Check what it is !!!
	private String terminatedCondition;		// cf. TerminatedCondition enum
	private String queueTypeName;			// cf. QueueType enum
	private Integer optimisticLock;			// 1.0
	private Integer maxNumPlayers;
	private Integer queuePosition;
	private Integer expiryTime;
	private Integer pickTurn;					// TODO: check but I think 0 = not time to pick, 1 = pick, 2 = champion locked
	private List<PlayerChampionSelectionDTO> playerChampionSelections;	// our team selection (why?)
	private String roomName;
	private String roomPassword;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.GameDTO";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.GameDTO";
	}
	
	public GameDTO(TypedObject to) {
		super();
		this.id = to.getDouble("id");
		this.spectatorsAllowed = to.getString("spectatorsAllowed");
		this.passwordSet = to.getBool("passwordSet");
		this.gameMode = to.getString("gameMode");
		this.mapId = to.getInt("mapId");
		this.gameType = to.getString("gameType");
		this.gameTypeConfigId = to.getInt("gameTypeConfigId");
		this.gameState = to.getString("gameState");
		this.gameStateString = to.getString("gameStateString");
		this.statusOfParticipants = to.getString("statusOfParticipants");
		
		TypedObject tobj = to.getTO("ownerSummary");
		this.ownerSummary = (tobj == null) ? null : Participant.createParticipant(tobj);
		
		this.dataVersion = to.getInt("dataVersion");
		this.name = to.getString("name");
		this.spectatorDelay = to.getInt("spectatorDelay");
		this.creationTime = to.getDate("creationTime");
		this.terminatedCondition = to.getString("terminatedCondition");
		this.queueTypeName = to.getString("queueTypeName");
		this.optimisticLock = to.getInt("optimisticLock");
		this.maxNumPlayers = to.getInt("maxNumPlayers");
		this.queuePosition = to.getInt("queuePosition");
		this.expiryTime = to.getInt("expiryTime");
		this.pickTurn = to.getInt("pickTurn");
		this.roomName = to.getString("roomName");
		this.roomPassword = to.getString("roomPassword");
		
		// create the two teams
		// team one
		teamOne = new ArrayList<Participant>();
		Object[] t1 = to.getArray("teamOne");
		for (Object o : t1) {
			TypedObject t = (TypedObject)o;
			teamOne.add(Participant.createParticipant(t));
		}
		
		// team two
		teamTwo = new ArrayList<Participant>();
		Object[] t2 = to.getArray("teamTwo");
		for (Object o : t2) {
			TypedObject t = (TypedObject)o;
			teamTwo.add(Participant.createParticipant(t));
		}
		
		// create observers
		observers = new ArrayList<GameObserver>();
		Object[] ob = to.getArray("observers");
		for (Object o : ob) {
			TypedObject t = (TypedObject)o;
			observers.add(new GameObserver(t));
		}
		
		// TODO: create ban champs
		bannedChampions = new ArrayList<Object>();
		
		// create playerChampionSelections
		playerChampionSelections = new ArrayList<PlayerChampionSelectionDTO>();
		Object[] oc = to.getArray("playerChampionSelections");
		for (Object o : oc) {
			TypedObject t = (TypedObject)o;
			playerChampionSelections.add(new PlayerChampionSelectionDTO(t));
		}
	}
	
	public Integer getNumPlayers() {
		return teamOne.size() + teamTwo.size();
	}
	
	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String isSpectatorsAllowed() {
		return spectatorsAllowed;
	}

	public void setSpectatorsAllowed(String spectatorsAllowed) {
		this.spectatorsAllowed = spectatorsAllowed;
	}

	public boolean isPasswordSet() {
		return passwordSet;
	}

	public void setPasswordSet(boolean passwordSet) {
		this.passwordSet = passwordSet;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Integer getGameTypeConfigId() {
		return gameTypeConfigId;
	}

	public void setGameTypeConfigId(Integer gameTypeConfigId) {
		this.gameTypeConfigId = gameTypeConfigId;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public String getGameStateString() {
		return gameStateString;
	}

	public void setGameStateString(String gameStateString) {
		this.gameStateString = gameStateString;
	}

	public Participant getOwnerSummary() {
		return ownerSummary;
	}

	public void setOwnerSummary(Participant ownerSummary) {
		this.ownerSummary = ownerSummary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSpectatorDelay() {
		return spectatorDelay;
	}

	public void setSpectatorDelay(Integer spectatorDelay) {
		this.spectatorDelay = spectatorDelay;
	}

	public List<Participant> getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(List<Participant> teamOne) {
		this.teamOne = teamOne;
	}

	public List<Participant> getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(List<Participant> teamTwo) {
		this.teamTwo = teamTwo;
	}

	public List<GameObserver> getObservers() {
		return observers;
	}

	public void setObservers(List<GameObserver> observers) {
		this.observers = observers;
	}

	public List<Object> getBannedChampions() {
		return bannedChampions;
	}

	public void setBannedChampions(List<Object> bannedChampions) {
		this.bannedChampions = bannedChampions;
	}

	public String getTerminatedCondition() {
		return terminatedCondition;
	}

	public void setTerminatedCondition(String terminatedCondition) {
		this.terminatedCondition = terminatedCondition;
	}

	public String getQueueTypeName() {
		return queueTypeName;
	}

	public void setQueueTypeName(String queueTypeName) {
		this.queueTypeName = queueTypeName;
	}

	public Integer getOptimisticLock() {
		return optimisticLock;
	}

	public void setOptimisticLock(Integer optimisticLock) {
		this.optimisticLock = optimisticLock;
	}

	public Integer getMaxNumPlayers() {
		return maxNumPlayers;
	}

	public void setMaxNumPlayers(Integer maxNumPlayers) {
		this.maxNumPlayers = maxNumPlayers;
	}

	public Integer getQueuePosition() {
		return queuePosition;
	}

	public void setQueuePosition(Integer queuePosition) {
		this.queuePosition = queuePosition;
	}

	public Integer getPickTurn() {
		return pickTurn;
	}

	public void setPickTurn(Integer pickTurn) {
		this.pickTurn = pickTurn;
	}

	public List<PlayerChampionSelectionDTO> getPlayerChampionSelections() {
		return playerChampionSelections;
	}

	public void setPlayerChampionSelections(
			List<PlayerChampionSelectionDTO> playerChampionSelections) {
		this.playerChampionSelections = playerChampionSelections;
	}

	public Integer getDataVersion() {
		return dataVersion;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Integer getExpiryTime() {
		return expiryTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getRoomPassword() {
		return roomPassword;
	}
	
	public String getStatusOfParticipants() {
		return statusOfParticipants;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameDTO [id=");
		builder.append(id);
		builder.append(", spectatorsAllowed=");
		builder.append(spectatorsAllowed);
		builder.append(", passwordSet=");
		builder.append(passwordSet);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", mapId=");
		builder.append(mapId);
		builder.append(", gameType=");
		builder.append(gameType);
		builder.append(", gameTypeConfigId=");
		builder.append(gameTypeConfigId);
		builder.append(", gameState=");
		builder.append(gameState);
		builder.append(", gameStateString=");
		builder.append(gameStateString);
		builder.append(", statusOfParticipants=");
		builder.append(statusOfParticipants);
		builder.append(", ownerSummary=");
		builder.append(ownerSummary);
		builder.append(", dataVersion=");
		builder.append(dataVersion);
		builder.append(", name=");
		builder.append(name);
		builder.append(", spectatorDelay=");
		builder.append(spectatorDelay);
		builder.append(", creationTime=");
		builder.append(creationTime);
		builder.append(", teamOne=");
		builder.append(teamOne);
		builder.append(", teamTwo=");
		builder.append(teamTwo);
		builder.append(", observers=");
		builder.append(observers);
		builder.append(", bannedChampions=");
		builder.append(bannedChampions);
		builder.append(", terminatedCondition=");
		builder.append(terminatedCondition);
		builder.append(", queueTypeName=");
		builder.append(queueTypeName);
		builder.append(", optimisticLock=");
		builder.append(optimisticLock);
		builder.append(", maxNumPlayers=");
		builder.append(maxNumPlayers);
		builder.append(", queuePosition=");
		builder.append(queuePosition);
		builder.append(", expiryTime=");
		builder.append(expiryTime);
		builder.append(", pickTurn=");
		builder.append(pickTurn);
		builder.append(", playerChampionSelections=");
		builder.append(playerChampionSelections);
		builder.append(", roomName=");
		builder.append(roomName);
		builder.append(", roomPassword=");
		builder.append(roomPassword);
		builder.append("]");
		return builder.toString();
	}

}
