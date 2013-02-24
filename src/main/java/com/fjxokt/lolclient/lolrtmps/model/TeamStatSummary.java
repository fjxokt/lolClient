package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamStatSummary extends ClassType {

	private String teamIdString;
	private TeamId teamId;
	private List<TeamStatDetail> teamStatDetails;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.team.stats.TeamStatSummary";
	}
	
	public TeamStatSummary(TypedObject to) {
		super();
		this.teamIdString = to.getString("teamIdString");
		TypedObject obj = to.getTO("teamId");
		this.teamId = (obj == null) ? null : new TeamId(obj);
		this.teamStatDetails = new ArrayList<TeamStatDetail>();
		Object[] objs = to.getArray("teamStatDetails");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tm = (TypedObject)o;
				if (tm != null) {
					teamStatDetails.add(new TeamStatDetail(tm));
				}
			}
		}
	}

	public String getTeamIdString() {
		return teamIdString;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public List<TeamStatDetail> getTeamStatDetails() {
		return Collections.unmodifiableList(teamStatDetails);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamStatSummary [teamIdString=");
		builder.append(teamIdString);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append(", teamStatDetails=");
		builder.append(teamStatDetails);
		builder.append("]");
		return builder.toString();
	}

}
