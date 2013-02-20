package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerLevelAndPoints extends ClassType {

	private Double summonerId;
	private Double summonerLevel;
	private Double expPoints;
	private Double infPoints;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerLevelAndPoints";
	}
	
	public SummonerLevelAndPoints(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.summonerLevel = to.getDouble("summonerLevel");
		this.expPoints = to.getDouble("expPoints");
		this.infPoints = to.getDouble("infPoints");
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Double getSummonerLevel() {
		return summonerLevel;
	}

	public Double getExpPoints() {
		return expPoints;
	}

	public Double getInfPoints() {
		return infPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerLevelAndPoints [summonerId=");
		builder.append(summonerId);
		builder.append(", summonerLevel=");
		builder.append(summonerLevel);
		builder.append(", expPoints=");
		builder.append(expPoints);
		builder.append(", infPoints=");
		builder.append(infPoints);
		builder.append("]");
		return builder.toString();
	}

}
