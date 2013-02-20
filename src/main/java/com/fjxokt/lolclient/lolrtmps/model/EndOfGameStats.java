package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class EndOfGameStats extends ClassType {

	private Integer talentPointsGained;
	private Boolean ranked;
	private Boolean leveledUp;
	private Integer skinIndex;
	private Integer queueBonusEarned;
	private String gameType;
	private Double experienceEarned;
	private Boolean imbalancedTeamsNoPoints;
	private List<PlayerParticipantStatsSummary> teamPlayerParticipantStats;
	private List<PlayerParticipantStatsSummary> otherTeamPlayerParticipantStats;
	private Integer basePoints;
	private Double gameLength;
	private Double boostXpEarned;
	private Boolean invalid;
	private Double userId;
	private List<PointsPenalty> pointsPenalties;
	private Double loyaltyBoostIpEarned;
	private Integer completionBonusPoints;
	private Double boostIpEarned;
	private List<Object> newSpells; 		// TODO: check what is this 1
	private Double experienceTotal;
	private Double gameId;
	private Double timeUntilNextFirstWinBonus;
	private Double loyaltyBoostXpEarned;
	private Integer elo;
	private Double ipEarned;
	private Double rpEarned;
	private Double firstWinBonus;
	private Integer eloChange;
	private String gameMode;
	private String queueType;
	private Integer odinBonusIp;
	private Integer practiceMinutesLeftToday;
	private Double ipTotal;
	private String summonerName;
	private Double reportGameId;
	private String difficulty;
	private String roomName;
	private String roomPassword;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.EndOfGameStats";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.statistics.EndOfGameStats";
	}
	
	public EndOfGameStats(TypedObject to) {
		this.talentPointsGained = to.getInt("talentPointsGained");
		this.ranked = to.getBool("ranked");
		this.leveledUp = to.getBool("leveledUp");
		this.skinIndex = to.getInt("skinIndex");
		this.queueBonusEarned = to.getInt("queueBonusEarned");
		this.gameType = to.getString("gameType");
		this.experienceEarned = to.getDouble("experienceEarned");
		this.imbalancedTeamsNoPoints = to.getBool("imbalancedTeamsNoPoints");
		this.basePoints = to.getInt("basePoints");
		this.gameLength = to.getDouble("gameLength");
		this.boostXpEarned = to.getDouble("boostXpEarned");
		this.invalid = to.getBool("invalid");
		this.userId = to.getDouble("userId");
		this.loyaltyBoostIpEarned = to.getDouble("loyaltyBoostIpEarned");
		this.completionBonusPoints = to.getInt("completionBonusPoints");
		this.boostIpEarned = to.getDouble("boostIpEarned");
		this.experienceTotal = to.getDouble("experienceTotal");
		this.gameId = to.getDouble("gameId");
		this.timeUntilNextFirstWinBonus = to.getDouble("timeUntilNextFirstWinBonus");
		this.loyaltyBoostXpEarned = to.getDouble("loyaltyBoostXpEarned");
		this.elo = to.getInt("elo");
		this.ipEarned = to.getDouble("ipEarned");
		this.firstWinBonus = to.getDouble("firstWinBonus");
		this.eloChange = to.getInt("eloChange");
		this.gameMode = to.getString("gameMode");
		this.queueType = to.getString("queueType");
		this.odinBonusIp = to.getInt("odinBonusIp");
		this.practiceMinutesLeftToday = to.getInt("practiceMinutesLeftToday");
		this.ipTotal = to.getDouble("ipTotal");
		this.summonerName = to.getString("summonerName");

		this.teamPlayerParticipantStats = new ArrayList<PlayerParticipantStatsSummary>();
		Object[] stats = to.getArray("teamPlayerParticipantStats");
		if (stats != null) {
			for (Object o : stats) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					teamPlayerParticipantStats.add(new PlayerParticipantStatsSummary(ts));
				}
			}
		}
		
		this.otherTeamPlayerParticipantStats = new ArrayList<PlayerParticipantStatsSummary>();
		stats = to.getArray("otherTeamPlayerParticipantStats");
		if (stats != null) {
			for (Object o : stats) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					otherTeamPlayerParticipantStats.add(new PlayerParticipantStatsSummary(ts));
				}
			}
		}
		
		this.pointsPenalties = new ArrayList<PointsPenalty>();
		stats = to.getArray("pointsPenalties");
		if (stats != null) {
			for (Object o : stats) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					pointsPenalties.add(new PointsPenalty(ts));
				}
			}
		}
		
		// TODO: what is this ?
		this.newSpells = new ArrayList<Object>();	
	}

	public Integer getTalentPointsGained() {
		return talentPointsGained;
	}

	public Boolean getRanked() {
		return ranked;
	}

	public Boolean getLeveledUp() {
		return leveledUp;
	}

	public Integer getSkinIndex() {
		return skinIndex;
	}

	public Integer getQueueBonusEarned() {
		return queueBonusEarned;
	}

	public String getGameType() {
		return gameType;
	}

	public Double getExperienceEarned() {
		return experienceEarned;
	}

	public Boolean getImbalancedTeamsNoPoints() {
		return imbalancedTeamsNoPoints;
	}

	public List<PlayerParticipantStatsSummary> getTeamPlayerParticipantStats() {
		return teamPlayerParticipantStats;
	}

	public List<PlayerParticipantStatsSummary> getOtherTeamPlayerParticipantStats() {
		return otherTeamPlayerParticipantStats;
	}

	public Integer getBasePoints() {
		return basePoints;
	}

	public Double getGameLength() {
		return gameLength;
	}

	public Double getBoostXpEarned() {
		return boostXpEarned;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public Double getUserId() {
		return userId;
	}

	public List<PointsPenalty> getPointsPenalties() {
		return pointsPenalties;
	}

	public Double getLoyaltyBoostIpEarned() {
		return loyaltyBoostIpEarned;
	}

	public Integer getCompletionBonusPoints() {
		return completionBonusPoints;
	}

	public Double getBoostIpEarned() {
		return boostIpEarned;
	}

	public List<Object> getNewSpells() {
		return newSpells;
	}

	public Double getExperienceTotal() {
		return experienceTotal;
	}

	public Double getGameId() {
		return gameId;
	}

	public Double getTimeUntilNextFirstWinBonus() {
		return timeUntilNextFirstWinBonus;
	}

	public Double getLoyaltyBoostXpEarned() {
		return loyaltyBoostXpEarned;
	}

	public Integer getElo() {
		return elo;
	}

	public Double getIpEarned() {
		return ipEarned;
	}

	public Double getFirstWinBonus() {
		return firstWinBonus;
	}

	public Integer getEloChange() {
		return eloChange;
	}

	public String getGameMode() {
		return gameMode;
	}

	public String getQueueType() {
		return queueType;
	}

	public Integer getOdinBonusIp() {
		return odinBonusIp;
	}

	public Integer getPracticeMinutesLeftToday() {
		return practiceMinutesLeftToday;
	}

	public Double getIpTotal() {
		return ipTotal;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public Double getRpEarned() {
		return rpEarned;
	}

	public Double getReportGameId() {
		return reportGameId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getRoomPassword() {
		return roomPassword;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EndOfGameStats [basePoints=");
		builder.append(basePoints);
		builder.append(", boostIpEarned=");
		builder.append(boostIpEarned);
		builder.append(", boostXpEarned=");
		builder.append(boostXpEarned);
		builder.append(", completionBonusPoints=");
		builder.append(completionBonusPoints);
		builder.append(", difficulty=");
		builder.append(difficulty);
		builder.append(", elo=");
		builder.append(elo);
		builder.append(", eloChange=");
		builder.append(eloChange);
		builder.append(", experienceEarned=");
		builder.append(experienceEarned);
		builder.append(", experienceTotal=");
		builder.append(experienceTotal);
		builder.append(", firstWinBonus=");
		builder.append(firstWinBonus);
		builder.append(", gameId=");
		builder.append(gameId);
		builder.append(", gameLength=");
		builder.append(gameLength);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", gameType=");
		builder.append(gameType);
		builder.append(", imbalancedTeamsNoPoints=");
		builder.append(imbalancedTeamsNoPoints);
		builder.append(", invalid=");
		builder.append(invalid);
		builder.append(", ipEarned=");
		builder.append(ipEarned);
		builder.append(", ipTotal=");
		builder.append(ipTotal);
		builder.append(", leveledUp=");
		builder.append(leveledUp);
		builder.append(", loyaltyBoostIpEarned=");
		builder.append(loyaltyBoostIpEarned);
		builder.append(", loyaltyBoostXpEarned=");
		builder.append(loyaltyBoostXpEarned);
		builder.append(", newSpells=");
		builder.append(newSpells);
		builder.append(", odinBonusIp=");
		builder.append(odinBonusIp);
		builder.append(", otherTeamPlayerParticipantStats=");
		builder.append(otherTeamPlayerParticipantStats);
		builder.append(", pointsPenalties=");
		builder.append(pointsPenalties);
		builder.append(", practiceMinutesLeftToday=");
		builder.append(practiceMinutesLeftToday);
		builder.append(", queueBonusEarned=");
		builder.append(queueBonusEarned);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", ranked=");
		builder.append(ranked);
		builder.append(", reportGameId=");
		builder.append(reportGameId);
		builder.append(", roomName=");
		builder.append(roomName);
		builder.append(", roomPassword=");
		builder.append(roomPassword);
		builder.append(", rpEarned=");
		builder.append(rpEarned);
		builder.append(", skinIndex=");
		builder.append(skinIndex);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append(", talentPointsGained=");
		builder.append(talentPointsGained);
		builder.append(", teamPlayerParticipantStats=");
		builder.append(teamPlayerParticipantStats);
		builder.append(", timeUntilNextFirstWinBonus=");
		builder.append(timeUntilNextFirstWinBonus);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
