package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class BasePublicSummonerDTO extends ClassType {

	private Double sumId;
	private String name;
	private String internalName;
	private Double acctId;
	private String seasonOneTier;
	private Integer profileIconId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.BasePublicSummonerDTO";
	}
	
	public BasePublicSummonerDTO(TypedObject to) {
		super();
		this.sumId = to.getDouble("sumId");
		this.name = to.getString("name");
		this.internalName = to.getString("internalName");
		this.acctId = to.getDouble("acctId");
		this.seasonOneTier = to.getString("seasonOneTier");
		this.profileIconId = to.getInt("profileIconId");
	}

	public Double getSumId() {
		return sumId;
	}

	public String getName() {
		return name;
	}

	public String getInternalName() {
		return internalName;
	}

	public Double getAcctId() {
		return acctId;
	}

	public String getSeasonOneTier() {
		return seasonOneTier;
	}

	public Integer getProfileIconId() {
		return profileIconId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BasePublicSummonerDTO [sumId=");
		builder.append(sumId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", internalName=");
		builder.append(internalName);
		builder.append(", acctId=");
		builder.append(acctId);
		builder.append(", seasonOneTier=");
		builder.append(seasonOneTier);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append("]");
		return builder.toString();
	}

}
