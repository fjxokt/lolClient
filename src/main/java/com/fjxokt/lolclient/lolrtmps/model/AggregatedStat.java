package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class AggregatedStat extends ClassType {

	private String statType; // cf. StatType
	private Double count;
	private Double value;
	private Double championId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.AggregatedStat";
	}
	
	public AggregatedStat(TypedObject to) {
		super();
		this.statType = to.getString("statType");
		this.count = to.getDouble("count");
		this.value = to.getDouble("value");
		this.championId = to.getDouble("championId");
	}

	public String getStatType() {
		return statType;
	}

	public Double getCount() {
		return count;
	}

	public Double getValue() {
		return value;
	}

	public Double getChampionId() {
		return championId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AggregatedStat [championId=");
		builder.append(championId);
		builder.append(", count=");
		builder.append(count);
		builder.append(", statType=");
		builder.append(statType);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
