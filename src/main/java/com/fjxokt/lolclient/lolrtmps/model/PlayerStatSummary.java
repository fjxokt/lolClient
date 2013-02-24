package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.Date;

public class PlayerStatSummary extends ClassType {
	
	private String playerStatSummaryType;
	// there is also a String playerStatSummaryTypeString but its the same value
	private Double userId;
	private Date modifyDate;
	private Integer rating;
	private Integer maxRating;
	private Integer wins;
	private Integer losses;
	private Integer leaves;
	private SummaryAggStats aggregatedStats;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerStatSummary";
	}
	
	public PlayerStatSummary(TypedObject to) {
		super();
		this.playerStatSummaryType = to.getString("playerStatSummaryType");
		this.userId = to.getDouble("userId");
		this.modifyDate = to.getDate("modifyDate");
		this.rating = to.getInt("rating");
		this.maxRating = to.getInt("maxRating");
		this.wins = to.getInt("wins");
		this.losses = to.getInt("losses");
		this.leaves = to.getInt("leaves");
		TypedObject ts = to.getTO("aggregatedStats");
		this.aggregatedStats = (ts == null) ? null : new SummaryAggStats(ts);
	}

	public String getPlayerStatSummaryType() {
		return playerStatSummaryType;
	}

	public Double getUserId() {
		return userId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public Integer getRating() {
		return rating;
	}

	public Integer getMaxRating() {
		return maxRating;
	}

	public Integer getWins() {
		return wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public Integer getLeaves() {
		return leaves;
	}

	public SummaryAggStats getAggregatedStats() {
		return aggregatedStats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerStatSummary [playerStatSummaryType=");
		builder.append(playerStatSummaryType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", modifyDate=");
		builder.append(modifyDate);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", maxRating=");
		builder.append(maxRating);
		builder.append(", wins=");
		builder.append(wins);
		builder.append(", losses=");
		builder.append(losses);
		builder.append(", leaves=");
		builder.append(leaves);
		builder.append(", aggregatedStats=");
		builder.append(aggregatedStats);
		builder.append("]");
		return builder.toString();
	}

}
