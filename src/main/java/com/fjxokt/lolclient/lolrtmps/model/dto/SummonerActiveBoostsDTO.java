package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class SummonerActiveBoostsDTO extends ClassType {

	private Double summonerId;
	private Double xpBoostEndDate;
	private Double ipBoostEndDate;
	private Integer xpBoostPerWinCount;
	private Integer xpLoyaltyBoost;
	private Integer ipBoostPerWinCount;
	private Integer ipLoyaltyBoost;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.boost.SummonerActiveBoostsDTO";
	}
	
	public SummonerActiveBoostsDTO(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.xpBoostEndDate = to.getDouble("xpBoostEndDate");
		this.ipBoostEndDate = to.getDouble("ipBoostEndDate");
		this.xpBoostPerWinCount = to.getInt("xpBoostPerWinCount");
		this.xpLoyaltyBoost = to.getInt("xpLoyaltyBoost");
		this.ipBoostPerWinCount = to.getInt("ipBoostPerWinCount");
		this.ipLoyaltyBoost = to.getInt("ipLoyaltyBoost");
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Double getXpBoostEndDate() {
		return xpBoostEndDate;
	}

	public Double getIpBoostEndDate() {
		return ipBoostEndDate;
	}

	public Integer getXpBoostPerWinCount() {
		return xpBoostPerWinCount;
	}

	public Integer getXpLoyaltyBoost() {
		return xpLoyaltyBoost;
	}

	public Integer getIpBoostPerWinCount() {
		return ipBoostPerWinCount;
	}

	public Integer getIpLoyaltyBoost() {
		return ipLoyaltyBoost;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerActiveBoostsDTO [ipBoostEndDate=");
		builder.append(ipBoostEndDate);
		builder.append(", ipBoostPerWinCount=");
		builder.append(ipBoostPerWinCount);
		builder.append(", ipLoyaltyBoost=");
		builder.append(ipLoyaltyBoost);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", xpBoostEndDate=");
		builder.append(xpBoostEndDate);
		builder.append(", xpBoostPerWinCount=");
		builder.append(xpBoostPerWinCount);
		builder.append(", xpLoyaltyBoost=");
		builder.append(xpLoyaltyBoost);
		builder.append("]");
		return builder.toString();
	}

}
