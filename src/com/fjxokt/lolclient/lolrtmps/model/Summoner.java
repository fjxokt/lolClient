package com.fjxokt.lolclient.lolrtmps.model;

import java.util.Date;

import com.gvaneyck.rtmp.TypedObject;

public class Summoner extends ClassType {

	private Double acctId;
	private Double sumId;
	private String internalName;
	private Integer profileIconId;
	private Date lastGameDate;
	private String name;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.Summoner";
	}
	
	public Summoner(TypedObject to) {
		super();
		this.acctId = to.getDouble("acctId");
		this.sumId = to.getDouble("sumId");
		this.internalName = to.getString("internalName");
		this.profileIconId = to.getInt("profileIconId");
		this.lastGameDate = to.getDate("lastGameDate");
		this.name = to.getString("name");
	}

	public void setProfileIconId(Integer profileIconId) {
		this.profileIconId = profileIconId;
	}

	public void setLastGameDate(Date lastGameDate) {
		this.lastGameDate = lastGameDate;
	}

	public Double getAcctId() {
		return acctId;
	}

	public Double getSumId() {
		return sumId;
	}

	public String getInternalName() {
		return internalName;
	}

	public Integer getProfileIconId() {
		return profileIconId;
	}

	public Date getLastGameDate() {
		return lastGameDate;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Summoner [acctId=");
		builder.append(acctId);
		builder.append(", sumId=");
		builder.append(sumId);
		builder.append(", internalName=");
		builder.append(internalName);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append(", lastGameDate=");
		builder.append(lastGameDate);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
