package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class PracticeGameConfig extends ClassType {
	
	private String gameName;
	private GameMap gameMap;
	private String gameMode;			// CLASSIC
	private String gamePassword;
	private Integer maxNumPlayers;
	private String allowSpectators; 	// "ALL"
	private Integer gameTypeConfig;	// 1

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.PracticeGameConfig";
	}

	public PracticeGameConfig(String gameName, GameMap gameMap,
			String gameMode, String gamePassword, Integer maxNumPlayers,
			String allowSpectators, Integer gameTypeConfig) {
		super();
		this.gameName = gameName;
		this.gameMap = gameMap;
		this.gameMode = gameMode;
		this.gamePassword = gamePassword;
		this.maxNumPlayers = maxNumPlayers;
		this.allowSpectators = allowSpectators;
		this.gameTypeConfig = gameTypeConfig;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getGamePassword() {
		return gamePassword;
	}

	public void setGamePassword(String gamePassword) {
		this.gamePassword = gamePassword;
	}

	public Integer getMaxNumPlayers() {
		return maxNumPlayers;
	}

	public void setMaxNumPlayers(Integer maxNumPlayers) {
		this.maxNumPlayers = maxNumPlayers;
	}

	public String getAllowSpectators() {
		return allowSpectators;
	}

	public void setAllowSpectators(String allowSpectators) {
		this.allowSpectators = allowSpectators;
	}

	public Integer getGameTypeConfig() {
		return gameTypeConfig;
	}

	public void setGameTypeConfig(Integer gameTypeConfig) {
		this.gameTypeConfig = gameTypeConfig;
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("gameName", gameName);
		res.put("gameMap", gameMap.getTypedObject());
		res.put("gameMode", gameMode);
		res.put("gamePassword", gamePassword);
		res.put("maxNumPlayers", maxNumPlayers);
		res.put("allowSpectators", allowSpectators);
		res.put("gameTypeConfig", gameTypeConfig);
		res.put("passbackDataPacket", null);
		res.put("passbackUrl", null);

		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PracticeGameConfig [gameName=");
		builder.append(gameName);
		builder.append(", gameMap=");
		builder.append(gameMap);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", gamePassword=");
		builder.append(gamePassword);
		builder.append(", maxNumPlayers=");
		builder.append(maxNumPlayers);
		builder.append(", allowSpectators=");
		builder.append(allowSpectators);
		builder.append(", gameTypeConfig=");
		builder.append(gameTypeConfig);
		builder.append("]");
		return builder.toString();
	}

}
