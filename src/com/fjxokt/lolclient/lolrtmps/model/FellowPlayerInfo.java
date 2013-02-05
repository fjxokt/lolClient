package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class FellowPlayerInfo extends ClassType {

	private Double summonerId;
	private Integer teamId;
	private Double championId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.FellowPlayerInfo";
	}
	
	public FellowPlayerInfo(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.teamId = to.getInt("teamId");
		this.championId = to.getDouble("championId");
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public Double getChampionId() {
		return championId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FellowPlayerInfo [championId=");
		builder.append(championId);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append("]");
		return builder.toString();
	}

}
