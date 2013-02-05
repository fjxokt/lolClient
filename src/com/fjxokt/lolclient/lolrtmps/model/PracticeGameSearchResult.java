package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class PracticeGameSearchResult extends ClassType {

	private Double id;
	private String gameMode;
	private Integer spectatorCount;
	private boolean privateGame;
	private String name;
	private PlayerParticipant owner;
	private Integer team1Count;
	private Integer team2Count;
	private Integer gameMapId;
	private String allowSpectators;
	private Integer maxNumPlayers;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.practice.PracticeGameSearchResult";
	}
	
	public PracticeGameSearchResult(TypedObject to) {
		super();
		this.id = to.getDouble("id");
		this.gameMode = to.getString("gameMode");
		this.spectatorCount = to.getInt("spectatorCount");
		this.privateGame = to.getBool("privateGame");
		this.name = to.getString("name");
		TypedObject tobj = to.getTO("owner");
		this.owner = (tobj == null) ? null : new PlayerParticipant(tobj);
		this.team1Count = to.getInt("team1Count");
		this.team2Count = to.getInt("team2Count");
		this.gameMapId = to.getInt("gameMapId");
		this.allowSpectators = to.getString("allowSpectators");
		this.maxNumPlayers = to.getInt("maxNumPlayers");
	}
	
	public PracticeGameSearchResult(Double id, String gameMode,
			Integer spectatorCount, boolean privateGame, String name,
			PlayerParticipant owner, Integer team1Count, Integer team2Count,
			Integer gameMapId, String allowSpectators, Integer maxNumPlayers) {
		super();
		this.id = id;
		this.gameMode = gameMode;
		this.spectatorCount = spectatorCount;
		this.privateGame = privateGame;
		this.name = name;
		this.owner = owner;
		this.team1Count = team1Count;
		this.team2Count = team2Count;
		this.gameMapId = gameMapId;
		this.allowSpectators = allowSpectators;
		this.maxNumPlayers = maxNumPlayers;
	}

	public Double getId() {
		return id;
	}

	public String getGameMode() {
		return gameMode;
	}

	public Integer getSpectatorCount() {
		return spectatorCount;
	}

	public boolean isPrivateGame() {
		return privateGame;
	}

	public String getName() {
		return name;
	}

	public PlayerParticipant getOwner() {
		return owner;
	}

	public Integer getTeam1Count() {
		return team1Count;
	}

	public Integer getTeam2Count() {
		return team2Count;
	}
	
	public Integer getGameMapId() {
		return gameMapId;
	}

	public String getAllowSpectators() {
		return allowSpectators;
	}
	
	public Integer getNumPlayers() {
		return team1Count + team2Count;
	}

	public Integer getMaxNumPlayers() {
		return maxNumPlayers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PracticeGameSearchResult [id=");
		builder.append(id);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", spectatorCount=");
		builder.append(spectatorCount);
		builder.append(", privateGame=");
		builder.append(privateGame);
		builder.append(", name=");
		builder.append(name);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", team1Count=");
		builder.append(team1Count);
		builder.append(", team2Count=");
		builder.append(team2Count);
		builder.append(", gameMapId=");
		builder.append(gameMapId);
		builder.append(", allowSpectators=");
		builder.append(allowSpectators);
		builder.append(", maxNumPlayers=");
		builder.append(maxNumPlayers);
		builder.append("]");
		return builder.toString();
	}

}
