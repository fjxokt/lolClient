package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerTalentsAndPoints extends ClassType {

	private Double summonerId;
	private Integer talentPoints;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerTalentsAndPoints";
	}
	
	public SummonerTalentsAndPoints(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.talentPoints = to.getInt("talentPoints");
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Integer getTalentPoints() {
		return talentPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerTalentsAndPoints [summonerId=");
		builder.append(summonerId);
		builder.append(", talentPoints=");
		builder.append(talentPoints);
		builder.append("]");
		return builder.toString();
	}

}
