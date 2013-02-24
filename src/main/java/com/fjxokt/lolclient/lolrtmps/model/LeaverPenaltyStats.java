package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.Date;

public class LeaverPenaltyStats extends ClassType {

	// TODO: private ? lastLevelIncrease
	private Integer level;
	private Date lastDecay;
	private Boolean userInformed;
	private Integer points;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.LeaverPenaltyStats";
	}
	
	public LeaverPenaltyStats(TypedObject to) {
		super();
		this.level = to.getInt("level");
		this.lastDecay = to.getDate("lastDecay");
		this.userInformed = to.getBool("userInformed");
		this.points = to.getInt("points");
	}

	public Integer getLevel() {
		return level;
	}

	public Date getLastDecay() {
		return lastDecay;
	}

	public Boolean getUserInformed() {
		return userInformed;
	}

	public Integer getPoints() {
		return points;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeaverPenaltyStats [lastDecay=");
		builder.append(lastDecay);
		builder.append(", level=");
		builder.append(level);
		builder.append(", points=");
		builder.append(points);
		builder.append(", userInformed=");
		builder.append(userInformed);
		builder.append("]");
		return builder.toString();
	}

}
