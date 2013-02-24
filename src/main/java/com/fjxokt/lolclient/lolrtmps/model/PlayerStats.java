package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStats extends ClassType {

	private List<TimeTrackedStat> timeTrackedStats;
	private Integer promoGamesPlayed;
	private Integer promoGamesPlayedLastUpdate;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerStats";
	}
	
	public PlayerStats(TypedObject to) {
		super();
		this.promoGamesPlayed = to.getInt("promoGamesPlayed");
		this.promoGamesPlayedLastUpdate = to.getInt("promoGamesPlayedLastUpdate");
		this.timeTrackedStats = new ArrayList<TimeTrackedStat>();
		Object[] objs = to.getArray("timeTrackedStats");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tt = (TypedObject)o;
				if (tt != null) {
					timeTrackedStats.add(new TimeTrackedStat(tt));
				}
			}
		}
	}

	public List<TimeTrackedStat> getTimeTrackedStats() {
		return Collections.unmodifiableList(timeTrackedStats);
	}

	public Integer getPromoGamesPlayed() {
		return promoGamesPlayed;
	}

	public Integer getPromoGamesPlayedLastUpdate() {
		return promoGamesPlayedLastUpdate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerStats [promoGamesPlayed=");
		builder.append(promoGamesPlayed);
		builder.append(", promoGamesPlayedLastUpdate=");
		builder.append(promoGamesPlayedLastUpdate);
		builder.append(", timeTrackedStats=");
		builder.append(timeTrackedStats);
		builder.append("]");
		return builder.toString();
	}

}
