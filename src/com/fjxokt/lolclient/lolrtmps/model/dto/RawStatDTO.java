package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class RawStatDTO extends ClassType {
	
	private String statTypeName;
	private Double value;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.RawStatDTO";
	}
	
	public RawStatDTO(TypedObject to) {
		super();
		this.statTypeName = to.getString("statTypeName");
		this.value = to.getDouble("value");
	}

	public String getStatTypeName() {
		return statTypeName;
	}

	public Double getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RawStatDTO [statTypeName=");
		builder.append(statTypeName);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
