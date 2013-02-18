package com.fjxokt.lolclient.lolrtmps.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.gvaneyck.rtmp.TypedObject;

public class TeamAggregatedStatsDTO extends ClassType {
	
	private String queueType; // cf. QueueType enum
	private TeamId teamId;
	private List<TeamPlayerAggregatedStatsDTO> playerAggregatedStatsList;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.team.TeamAggregatedStatsDTO";
	}
	
	public TeamAggregatedStatsDTO(TypedObject to) {
		super();
		this.queueType = to.getString("queueType");
		TypedObject t = to.getTO("teamId");
		this.teamId = (t == null) ? null : new TeamId(t);
		this.playerAggregatedStatsList = new ArrayList<TeamPlayerAggregatedStatsDTO>();
		Object[] objs = to.getArray("playerAggregatedStatsList");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					playerAggregatedStatsList.add(new TeamPlayerAggregatedStatsDTO(ts));
				}
			}
		}		
	}

	public String getQueueType() {
		return queueType;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public List<TeamPlayerAggregatedStatsDTO> getPlayerAggregatedStatsList() {
		return playerAggregatedStatsList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamAggregatedStatsDTO [playerAggregatedStatsList=");
		builder.append(playerAggregatedStatsList);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append("]");
		return builder.toString();
	}

}
