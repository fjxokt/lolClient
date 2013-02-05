package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class TeamStatDetail extends ClassType {

	private TeamId teamId;
	private String teamIdString;
	private String teamStatType;
//	private String teamStatTypeString; the same as above...
	private Integer rating;
	private Integer maxRating;
	private Integer seedRating;
	private Integer wins;
	private Integer losses;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.team.stats.TeamStatDetail";
	}
	
	public TeamStatDetail(TypedObject to) {
		super();
		this.teamIdString = to.getString("teamIdString");
		this.teamStatType = to.getString("teamStatType");
		this.rating = to.getInt("rating");
		this.maxRating = to.getInt("maxRating");
		this.seedRating = to.getInt("seedRating");
		this.wins = to.getInt("wins");
		this.losses = to.getInt("losses");
		TypedObject obj = to.getTO("teamId");
		this.teamId = (obj == null) ? null : new TeamId(obj);
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public String getTeamIdString() {
		return teamIdString;
	}

	public String getTeamStatType() {
		return teamStatType;
	}

	public Integer getRating() {
		return rating;
	}

	public Integer getMaxRating() {
		return maxRating;
	}

	public Integer getSeedRating() {
		return seedRating;
	}

	public Integer getWins() {
		return wins;
	}

	public Integer getLosses() {
		return losses;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamStatDetail [teamId=");
		builder.append(teamId);
		builder.append(", teamIdString=");
		builder.append(teamIdString);
		builder.append(", teamStatType=");
		builder.append(teamStatType);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", maxRating=");
		builder.append(maxRating);
		builder.append(", seedRating=");
		builder.append(seedRating);
		builder.append(", wins=");
		builder.append(wins);
		builder.append(", losses=");
		builder.append(losses);
		builder.append("]");
		return builder.toString();
	}

}
