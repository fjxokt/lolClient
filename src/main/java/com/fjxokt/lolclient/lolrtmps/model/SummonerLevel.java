package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerLevel extends ClassType {
	
	private Double summonerLevel;
	private Double summonerTier;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerLevel";
	}
	
	public SummonerLevel(TypedObject to) {
		super();
		this.summonerLevel = to.getDouble("summonerLevel");
		this.summonerTier = to.getDouble("summonerTier");
	}

	public Double getSummonerLevel() {
		return summonerLevel;
	}

	public Double getSummonerTier() {
		return summonerTier;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerLevel [summonerLevel=");
		builder.append(summonerLevel);
		builder.append(", summonerTier=");
		builder.append(summonerTier);
		builder.append("]");
		return builder.toString();
	}

}
