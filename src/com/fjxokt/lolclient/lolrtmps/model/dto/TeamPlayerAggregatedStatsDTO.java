package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.AggregatedStats;
import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class TeamPlayerAggregatedStatsDTO extends ClassType {
	
	private Double playerId;
	private AggregatedStats aggregatedStats;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.team.TeamPlayerAggregatedStatsDTO";
	}
	
	public TeamPlayerAggregatedStatsDTO(TypedObject to) {
		super();
		this.playerId = to.getDouble("playerId");
		TypedObject t = to.getTO("aggregatedStats");
		this.aggregatedStats = (t == null) ? null : new AggregatedStats(t);
	}

	public Double getPlayerId() {
		return playerId;
	}

	public AggregatedStats getAggregatedStats() {
		return aggregatedStats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamPlayerAggregatedStatsDTO [aggregatedStats=");
		builder.append(aggregatedStats);
		builder.append(", playerId=");
		builder.append(playerId);
		builder.append("]");
		return builder.toString();
	}

}
