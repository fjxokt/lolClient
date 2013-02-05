package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class GameQueueConfig extends ClassType {
	
	private Integer id;
	private String gameMode;
	private Integer numPlayersPerTeam;
	private String type; 		// "RANKED_SOLO_5x5" for instance
	private String typeString; // same (what's the point?)
	private boolean ranked;
	private List<Integer> supportedMapIds;
	private String pointsConfigKey;
	private Integer minimumParticipantListSize;
	private Integer maximumParticipantListSize;
	private Integer minLevel;
	private Integer maxLevel;
	private Integer gameTypeConfigId;
	private boolean teamOnly;
	private String queueStateString;
	private Boolean disallowFreeChampions;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.matchmaking.GameQueueConfig";
	}
	
	public GameQueueConfig(TypedObject to) {
		super();
		this.ranked = to.getBool("ranked");
		this.pointsConfigKey = to.getString("pointsConfigKey");
		this.minimumParticipantListSize = to.getInt("minimumParticipantListSize");
		this.maximumParticipantListSize = to.getInt("maximumParticipantListSize");
		this.minLevel = to.getInt("minLevel");
		this.maxLevel = to.getInt("maxLevel");
		this.gameTypeConfigId = to.getInt("gameTypeConfigId");
		this.teamOnly = to.getBool("teamOnly");
		this.disallowFreeChampions = to.getBool("disallowFreeChampions");
		this.type = to.getString("type");
		this.typeString = to.getString("typeString");
		this.gameMode = to.getString("gameMode");
		this.id = to.getInt("id");
		this.numPlayersPerTeam = to.getInt("numPlayersPerTeam");
		this.queueStateString = to.getString("queueStateString");
		
		this.supportedMapIds = new ArrayList<Integer>();
		Object[] objs = to.getArray("supportedMapIds");
		if (objs != null) {
			for (Object o : objs) {
				supportedMapIds.add((Integer)o);
			}
		}
	}

	public boolean isRanked() {
		return ranked;
	}

	public String getPointsConfigKey() {
		return pointsConfigKey;
	}

	public Integer getMinimumParticipantListSize() {
		return minimumParticipantListSize;
	}
	
	public Integer getMaximumParticipantListSize() {
		return maximumParticipantListSize;
	}

	public Integer getMinLevel() {
		return minLevel;
	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public Integer getGameTypeConfigId() {
		return gameTypeConfigId;
	}

	public Boolean isTeamOnly() {
		return teamOnly;
	}
	
	public Boolean getDisallowFreeChampions() {
		return disallowFreeChampions;
	}

	public String getType() {
		return type;
	}

	public String getTypeString() {
		return typeString;
	}

	public List<Integer> getSupportedMapIds() {
		return supportedMapIds;
	}

	public String getGameMode() {
		return gameMode;
	}

	public Integer getId() {
		return id;
	}

	public Integer getNumPlayersPerTeam() {
		return numPlayersPerTeam;
	}

	public String getQueueStateString() {
		return queueStateString;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameQueueConfig [id=");
		builder.append(id);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", numPlayersPerTeam=");
		builder.append(numPlayersPerTeam);
		builder.append(", type=");
		builder.append(type);
		builder.append(", typeString=");
		builder.append(typeString);
		builder.append(", ranked=");
		builder.append(ranked);
		builder.append(", supportedMapIds=");
		builder.append(supportedMapIds);
		builder.append(", pointsConfigKey=");
		builder.append(pointsConfigKey);
		builder.append(", minimumParticipantListSize=");
		builder.append(minimumParticipantListSize);
		builder.append(", maximumParticipantListSize=");
		builder.append(maximumParticipantListSize);
		builder.append(", minLevel=");
		builder.append(minLevel);
		builder.append(", maxLevel=");
		builder.append(maxLevel);
		builder.append(", gameTypeConfigId=");
		builder.append(gameTypeConfigId);
		builder.append(", teamOnly=");
		builder.append(teamOnly);
		builder.append(", disallowFreeChampions=");
		builder.append(disallowFreeChampions);
		builder.append(", queueStateString=");
		builder.append(queueStateString);
		builder.append("]");
		return builder.toString();
	}

}
