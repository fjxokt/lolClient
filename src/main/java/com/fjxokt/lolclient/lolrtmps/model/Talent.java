package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class Talent extends ClassType {
	
	private Integer index;
	private String name;
	private String levelXDesc; // supposed to be from 1 to 5, but it's the same thing
	private Integer minLevel;
	private Integer minTier;
	private Integer maxRank;
	private Integer tltId;
	private Integer talentGroupId;
	private Integer talentRowId;
	private Integer prereqTalentGameCode; // talent you need to fully unlock to enable this one
	private Integer gameCode;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.Talent";
	}
	
	public Talent(TypedObject to) {
		super();
		this.index = to.getInt("index");
		this.name = to.getString("name");
		this.levelXDesc = to.getString("levelXDesc");
		this.minLevel = to.getInt("minLevel");
		this.minTier = to.getInt("minTier");
		this.maxRank = to.getInt("maxRank");
		this.tltId = to.getInt("tltId");
		this.talentGroupId = to.getInt("talentGroupId");
		this.talentRowId = to.getInt("talentRowId");
		this.prereqTalentGameCode = to.getInt("prereqTalentGameCode");
		this.gameCode = to.getInt("gameCode");
	}

	public Integer getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getLevelXDesc() {
		return levelXDesc;
	}

	public Integer getMinLevel() {
		return minLevel;
	}

	public Integer getMinTier() {
		return minTier;
	}

	public Integer getMaxRank() {
		return maxRank;
	}

	public Integer getTltId() {
		return tltId;
	}

	public Integer getTalentGroupId() {
		return talentGroupId;
	}

	public Integer getTalentRowId() {
		return talentRowId;
	}

	public Integer getPrereqTalentGameCode() {
		return prereqTalentGameCode;
	}

	public Integer getGameCode() {
		return gameCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Talent [index=");
		builder.append(index);
		builder.append(", name=");
		builder.append(name);
		builder.append(", levelXDesc=");
		builder.append(levelXDesc);
		builder.append(", minLevel=");
		builder.append(minLevel);
		builder.append(", minTier=");
		builder.append(minTier);
		builder.append(", maxRank=");
		builder.append(maxRank);
		builder.append(", tltId=");
		builder.append(tltId);
		builder.append(", talentGroupId=");
		builder.append(talentGroupId);
		builder.append(", talentRowId=");
		builder.append(talentRowId);
		builder.append(", prereqTalentGameCode=");
		builder.append(prereqTalentGameCode);
		builder.append(", gameCode=");
		builder.append(gameCode);
		builder.append("]");
		return builder.toString();
	}

}
