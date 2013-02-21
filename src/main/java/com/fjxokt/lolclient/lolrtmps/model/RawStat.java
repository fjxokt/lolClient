package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class RawStat extends ClassType {

	private String statType; // cf. StatType enum
	private Double value;
	
	public String getStatType() {
		return statType;
	}

	public Double getValue() {
		return value;
	}

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.RawStat";
	}
	
	public RawStat(TypedObject to) {
		super();
		this.statType = to.getString("statType");
		this.value = to.getDouble("value");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RawStat [statType=");
		builder.append(statType);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
