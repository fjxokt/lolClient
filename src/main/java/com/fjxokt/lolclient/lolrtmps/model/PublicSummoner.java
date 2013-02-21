package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class PublicSummoner extends ClassType {
	
	private String internalName;
	private Integer acctId;
	private Integer profileIconId;
	private Integer summonerLevel;
	private Integer summonerId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.PublicSummone";
	}
	
	public PublicSummoner(TypedObject to) {
		super();
		this.internalName = to.getString("internalName");
		this.acctId = to.getInt("acctId");
		this.profileIconId = to.getInt("profileIconId");
		this.summonerLevel = to.getInt("summonerLevel");
		this.summonerId = to.getInt("summonerId");
	}
	
	public PublicSummoner(String internalName, Integer acctId, Integer profileIconId, Integer summonerLevel, Integer summonerId) {
		super();
		this.internalName = internalName;
		this.acctId = acctId;
		this.profileIconId = profileIconId;
		this.summonerLevel = summonerLevel;
		this.summonerId = summonerId;
	}

	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public Integer getAcctId() {
		return acctId;
	}
	public void setAcctId(Integer acctId) {
		this.acctId = acctId;
	}
	public Integer getProfileIconId() {
		return profileIconId;
	}
	public void setProfileIconId(Integer profileIconId) {
		this.profileIconId = profileIconId;
	}
	public Integer getSummonerLevel() {
		return summonerLevel;
	}
	public void setSummonerLevel(Integer summonerLevel) {
		this.summonerLevel = summonerLevel;
	}
	public Integer getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(Integer summonerId) {
		this.summonerId = summonerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PublicSummoner [internalName=");
		builder.append(internalName);
		builder.append(", acctId=");
		builder.append(acctId);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append(", summonerLevel=");
		builder.append(summonerLevel);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append("]");
		return builder.toString();
	}

}
