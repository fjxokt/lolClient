package com.fjxokt.lolclient.lolrtmps.model;

import java.util.Date;

import com.gvaneyck.rtmp.TypedObject;

public class TimeTrackedStat extends ClassType {
	
	private Date timestamp;
	private String type; // "PRACTICE_MINUTES" or "MM_BOTS_GAMES_PLAYED" or others...

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.TimeTrackedStat";
	}
	
	public TimeTrackedStat(TypedObject to) {
		super();
		this.timestamp = to.getDate("timestamp");
		this.type = to.getString("type");
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeTrackedStat [timestamp=");
		builder.append(timestamp);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
