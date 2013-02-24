package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PlayerGameStats extends ClassType {

	private Boolean ranked;
	private Integer skinIndex;
	private List<FellowPlayerInfo> fellowPlayers;
	private String gameType; // cf. GameType enum
	private Double experienceEarned;
	// String rawStatsJson
	private Boolean eligibleFirstWinOfDay;
	// difficulty ??
	private Integer gameMapId;
	private Double gameId;
	private Boolean leaver;
	private Double spell1;
	private Double spell2;
	private String gameTypeEnum; // "MATCHED_GAME"
	private Double teamId;
	private Double summonerId;
	private List<RawStat> statistics;
	private Boolean afk;
	// id ??
	private Double boostXpEarned;
	private Double boostIpEarned;
	private Double level;
	private Boolean invalid;
	private Double userId;
	private Date createDate;
	private Integer userServerPing;
	private Integer adjustedRating;
	private Integer premadeSize;
	private Integer timeInQueue;
	private Double ipEarned;
	private Integer eloChange;
	private String gameMode; // cf. GameMode enum
	private Double KCoefficient;
	private Double rating;
	private Integer teamRating;
	private String queueType; // cf. QueueType enum
	private String subType; // same as queueType ?
	private Boolean premadeTeam;
	private Double predictedWinPct;
	private Double championId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerGameStats";
	}
	
	public PlayerGameStats(TypedObject to) {
		super();
		this.ranked = to.getBool("ranked");
		this.skinIndex = to.getInt("skinIndex");
		this.gameType = to.getString("gameType");
		this.experienceEarned = to.getDouble("experienceEarned");
		this.eligibleFirstWinOfDay = to.getBool("eligibleFirstWinOfDay");
		this.gameMapId = to.getInt("gameMapId");
		this.gameId = to.getDouble("gameId");
		this.leaver = to.getBool("leaver");
		this.spell1 = to.getDouble("spell1");
		this.spell2 = to.getDouble("spell2");
		this.gameTypeEnum = to.getString("gameTypeEnum");
		this.teamId = to.getDouble("teamId");
		this.summonerId = to.getDouble("summonerId");
		this.afk = to.getBool("afk");
		this.boostXpEarned = to.getDouble("boostXpEarned");
		this.boostIpEarned = to.getDouble("boostIpEarned");
		this.level = to.getDouble("level");
		this.invalid = to.getBool("invalid");
		this.userId = to.getDouble("userId");
		this.createDate = to.getDate("createDate");
		this.userServerPing = to.getInt("userServerPing");
		this.adjustedRating = to.getInt("adjustedRating");
		this.premadeSize = to.getInt("premadeSize");
		this.timeInQueue = to.getInt("timeInQueue");
		this.ipEarned = to.getDouble("ipEarned");
		this.eloChange = to.getInt("eloChange");
		this.gameMode = to.getString("gameMode");
		this.KCoefficient = to.getDouble("KCoefficient");
		this.rating = to.getDouble("rating");
		this.teamRating = to.getInt("teamRating");
		this.queueType = to.getString("queueType");
		this.subType = to.getString("subType");
		this.premadeTeam = to.getBool("premadeTeam");
		this.predictedWinPct = to.getDouble("predictedWinPct");
		this.championId = to.getDouble("championId");
		

		this.fellowPlayers = new ArrayList<FellowPlayerInfo>();
		Object[] objs = to.getArray("fellowPlayers");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tf = (TypedObject)o;
				if (tf != null) {
					fellowPlayers.add(new FellowPlayerInfo(tf));
				}
			}
		}
		
		this.statistics = new ArrayList<RawStat>();
		objs = to.getArray("statistics");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tf = (TypedObject)o;
				if (tf != null) {
					statistics.add(new RawStat(tf));
				}
			}
		}
	}

	public Boolean getRanked() {
		return ranked;
	}

	public Integer getSkinIndex() {
		return skinIndex;
	}

	public List<FellowPlayerInfo> getFellowPlayers() {
		return Collections.unmodifiableList(fellowPlayers);
	}

	public String getGameType() {
		return gameType;
	}

	public Double getExperienceEarned() {
		return experienceEarned;
	}

	public Boolean getEligibleFirstWinOfDay() {
		return eligibleFirstWinOfDay;
	}

	public Integer getGameMapId() {
		return gameMapId;
	}

	public Double getGameId() {
		return gameId;
	}

	public Boolean getLeaver() {
		return leaver;
	}

	public Double getSpell1() {
		return spell1;
	}

	public Double getSpell2() {
		return spell2;
	}

	public String getGameTypeEnum() {
		return gameTypeEnum;
	}

	public Double getTeamId() {
		return teamId;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public List<RawStat> getStatistics() {
		return Collections.unmodifiableList(statistics);
	}

	public Boolean getAfk() {
		return afk;
	}

	public Double getBoostXpEarned() {
		return boostXpEarned;
	}

	public Double getBoostIpEarned() {
		return boostIpEarned;
	}

	public Double getLevel() {
		return level;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public Double getUserId() {
		return userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Integer getUserServerPing() {
		return userServerPing;
	}

	public Integer getAdjustedRating() {
		return adjustedRating;
	}

	public Integer getPremadeSize() {
		return premadeSize;
	}

	public Integer getTimeInQueue() {
		return timeInQueue;
	}

	public Double getIpEarned() {
		return ipEarned;
	}

	public Integer getEloChange() {
		return eloChange;
	}

	public String getGameMode() {
		return gameMode;
	}

	public Double getKCoefficient() {
		return KCoefficient;
	}

	public Double getRating() {
		return rating;
	}

	public Integer getTeamRating() {
		return teamRating;
	}

	public String getQueueType() {
		return queueType;
	}

	public String getSubType() {
		return subType;
	}

	public Boolean getPremadeTeam() {
		return premadeTeam;
	}

	public Double getPredictedWinPct() {
		return predictedWinPct;
	}

	public Double getChampionId() {
		return championId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerGameStats [KCoefficient=");
		builder.append(KCoefficient);
		builder.append(", adjustedRating=");
		builder.append(adjustedRating);
		builder.append(", afk=");
		builder.append(afk);
		builder.append(", boostIpEarned=");
		builder.append(boostIpEarned);
		builder.append(", boostXpEarned=");
		builder.append(boostXpEarned);
		builder.append(", championId=");
		builder.append(championId);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", eligibleFirstWinOfDay=");
		builder.append(eligibleFirstWinOfDay);
		builder.append(", eloChange=");
		builder.append(eloChange);
		builder.append(", experienceEarned=");
		builder.append(experienceEarned);
		builder.append(", fellowPlayers=");
		builder.append(fellowPlayers);
		builder.append(", gameId=");
		builder.append(gameId);
		builder.append(", gameMapId=");
		builder.append(gameMapId);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", gameType=");
		builder.append(gameType);
		builder.append(", gameTypeEnum=");
		builder.append(gameTypeEnum);
		builder.append(", invalid=");
		builder.append(invalid);
		builder.append(", ipEarned=");
		builder.append(ipEarned);
		builder.append(", leaver=");
		builder.append(leaver);
		builder.append(", level=");
		builder.append(level);
		builder.append(", predictedWinPct=");
		builder.append(predictedWinPct);
		builder.append(", premadeSize=");
		builder.append(premadeSize);
		builder.append(", premadeTeam=");
		builder.append(premadeTeam);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", ranked=");
		builder.append(ranked);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", skinIndex=");
		builder.append(skinIndex);
		builder.append(", spell1=");
		builder.append(spell1);
		builder.append(", spell2=");
		builder.append(spell2);
		builder.append(", statistics=");
		builder.append(statistics);
		builder.append(", subType=");
		builder.append(subType);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append(", teamRating=");
		builder.append(teamRating);
		builder.append(", timeInQueue=");
		builder.append(timeInQueue);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userServerPing=");
		builder.append(userServerPing);
		builder.append("]");
		return builder.toString();
	}

}
