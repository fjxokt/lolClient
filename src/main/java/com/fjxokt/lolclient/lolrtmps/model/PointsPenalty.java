package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class PointsPenalty extends ClassType {

	private Double penalty;
	private String type; 	// "CUSTOM"
	
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PointsPenalty";
	}
	
	public PointsPenalty(TypedObject to) {
		super();
		this.penalty = to.getDouble("penalty");
		this.type = to.getString("type");
	}

	public Double getPenalty() {
		return penalty;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PointsPenalty [penalty=");
		builder.append(penalty);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
