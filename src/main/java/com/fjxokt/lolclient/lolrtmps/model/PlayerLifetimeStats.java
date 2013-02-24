package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.Date;

public class PlayerLifetimeStats extends ClassType {

	private PlayerStatSummaries playerStatSummaries;
	private LeaverPenaltyStats leaverPenaltyStats;
	private Date previousFirstWinOfDay;
	private Double userId;
	private Integer dodgeStreak;
	private Date dodgePenaltyDate;
	// TODO: private ? playerStatsJson
	private PlayerStats playerStats;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerLifetimeStats";
	}
	
	public PlayerLifetimeStats(TypedObject to) {
		super();
		this.previousFirstWinOfDay = to.getDate("previousFirstWinOfDay");
		this.userId = to.getDouble("userId");
		this.dodgeStreak = to.getInt("dodgeStreak");
		this.dodgePenaltyDate = to.getDate("dodgePenaltyDate");
		TypedObject tobj = to.getTO("playerStatSummaries");
		this.playerStatSummaries = (tobj == null) ? null : new PlayerStatSummaries(tobj);
		tobj = to.getTO("leaverPenaltyStats");
		this.leaverPenaltyStats = (tobj == null) ? null : new LeaverPenaltyStats(tobj);
		tobj = to.getTO("playerStats");
		this.playerStats = (tobj == null) ? null : new PlayerStats(tobj);
	}

	public PlayerStatSummaries getPlayerStatSummaries() {
		return playerStatSummaries;
	}

	public LeaverPenaltyStats getLeaverPenaltyStats() {
		return leaverPenaltyStats;
	}

	public Date getPreviousFirstWinOfDay() {
		return previousFirstWinOfDay;
	}

	public Double getUserId() {
		return userId;
	}

	public Integer getDodgeStreak() {
		return dodgeStreak;
	}

	public Date getDodgePenaltyDate() {
		return dodgePenaltyDate;
	}

	public PlayerStats getPlayerStats() {
		return playerStats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerLifetimeStats [dodgePenaltyDate=");
		builder.append(dodgePenaltyDate);
		builder.append(", dodgeStreak=");
		builder.append(dodgeStreak);
		builder.append(", leaverPenaltyStats=");
		builder.append(leaverPenaltyStats);
		builder.append(", playerStatSummaries=");
		builder.append(playerStatSummaries);
		builder.append(", playerStats=");
		builder.append(playerStats);
		builder.append(", previousFirstWinOfDay=");
		builder.append(previousFirstWinOfDay);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
