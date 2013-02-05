package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class AggregatedStatsKey extends ClassType {

	private String gameMode; // "CLASSIC"
	private String gameModeString; // "CLASSIC"
	private Double userId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.AggregatedStatsKey";
	}
	
	public AggregatedStatsKey(TypedObject to) {
		super();
		this.gameMode = to.getString("gameMode");
		this.gameModeString = to.getString("gameModeString");
		this.userId = to.getDouble("userId");
	}

	public String getGameMode() {
		return gameMode;
	}

	public String getGameModeString() {
		return gameModeString;
	}

	public Double getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AggregatedStatsKey [gameMode=");
		builder.append(gameMode);
		builder.append(", gameModeString=");
		builder.append(gameModeString);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
