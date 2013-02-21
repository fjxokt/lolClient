package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class LeagueItemDTO extends ClassType {
	
	private Integer previousDayLeaguePosition;
	private Boolean hotStreak;
	// private ? miniSeries
	private Boolean freshBlood;
	private String tier; // "BRONZE"
	private Double lastPlayed;
	private Integer playerOrTeamId;
	private String playerOrTeamName;
	private Integer leaguePoints;
	private Boolean inactive;
	private String rank; // "II"
	private Boolean veteran;
	private String queueType; // "RANKED_SOLO_5x5"
	private Integer losses;
	private Integer wins;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.leagues.pojo.LeagueItemDTO";
	}
	
	public LeagueItemDTO(TypedObject to) {
		super();
		this.previousDayLeaguePosition = to.getInt("previousDayLeaguePosition");
		this.hotStreak = to.getBool("hotStreak");
		this.freshBlood = to.getBool("freshBlood");
		this.tier = to.getString("tier");
		this.lastPlayed = to.getDouble("lastPlayed");
		this.playerOrTeamId = to.getInt("playerOrTeamId");
		this.playerOrTeamName = to.getString("playerOrTeamName");
		this.leaguePoints = to.getInt("leaguePoints");
		this.inactive = to.getBool("inactive");
		this.rank = to.getString("rank");
		this.veteran = to.getBool("veteran");
		this.queueType = to.getString("queueType");
		this.losses = to.getInt("losses");
		this.wins = to.getInt("wins");
	}

	public Integer getPreviousDayLeaguePosition() {
		return previousDayLeaguePosition;
	}

	public Boolean getHotStreak() {
		return hotStreak;
	}

	public Boolean getFreshBlood() {
		return freshBlood;
	}

	public String getTier() {
		return tier;
	}

	public Double getLastPlayed() {
		return lastPlayed;
	}

	public Integer getPlayerOrTeamId() {
		return playerOrTeamId;
	}

	public String getPlayerOrTeamName() {
		return playerOrTeamName;
	}

	public Integer getLeaguePoints() {
		return leaguePoints;
	}

	public Boolean getInactive() {
		return inactive;
	}

	public String getRank() {
		return rank;
	}

	public Boolean getVeteran() {
		return veteran;
	}

	public String getQueueType() {
		return queueType;
	}

	public Integer getLosses() {
		return losses;
	}

	public Integer getWins() {
		return wins;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeagueItemDTO [freshBlood=");
		builder.append(freshBlood);
		builder.append(", hotStreak=");
		builder.append(hotStreak);
		builder.append(", inactive=");
		builder.append(inactive);
		builder.append(", lastPlayed=");
		builder.append(lastPlayed);
		builder.append(", leaguePoints=");
		builder.append(leaguePoints);
		builder.append(", losses=");
		builder.append(losses);
		builder.append(", playerOrTeamId=");
		builder.append(playerOrTeamId);
		builder.append(", playerOrTeamName=");
		builder.append(playerOrTeamName);
		builder.append(", previousDayLeaguePosition=");
		builder.append(previousDayLeaguePosition);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", tier=");
		builder.append(tier);
		builder.append(", veteran=");
		builder.append(veteran);
		builder.append(", wins=");
		builder.append(wins);
		builder.append("]");
		return builder.toString();
	}

}
