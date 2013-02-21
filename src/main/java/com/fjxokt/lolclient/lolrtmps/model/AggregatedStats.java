package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class AggregatedStats extends ClassType {

	private List<AggregatedStat> lifetimeStatistics;
	private Date modifyDate;
	private AggregatedStatsKey key;
	// TODO: ? private String aggregatedStatsJson
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.AggregatedStats";
	}
	
	public AggregatedStats(TypedObject to) {
		super();
		this.modifyDate = to.getDate("modifyDate");
		this.lifetimeStatistics = new ArrayList<AggregatedStat>();
		Object[] objs = to.getArray("lifetimeStatistics");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					lifetimeStatistics.add(new AggregatedStat(ts));
				}
			}
		}
		TypedObject tobj = to.getTO("key");
		this.key = (tobj == null) ? null : new AggregatedStatsKey(tobj);
	}

	public List<AggregatedStat> getLifetimeStatistics() {
		return lifetimeStatistics;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public AggregatedStatsKey getKey() {
		return key;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AggregatedStats [key=");
		builder.append(key);
		builder.append(", lifetimeStatistics=");
		builder.append(lifetimeStatistics);
		builder.append(", modifyDate=");
		builder.append(modifyDate);
		builder.append("]");
		return builder.toString();
	}

}
