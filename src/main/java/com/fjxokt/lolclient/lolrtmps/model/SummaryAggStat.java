package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SummaryAggStat extends ClassType {

	private String statType;
	private Double count;
	private Double value;
	
	@Override
	protected String getTypeName() {
		return null;
	}
	
	public SummaryAggStat(TypedObject to) {
		super();
		this.statType = to.getString("statType");
		this.count = to.getDouble("count");
		this.value = to.getDouble("value");
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummaryAggStat [statType=");
		builder.append(statType);
		builder.append(", count=");
		builder.append(count);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
