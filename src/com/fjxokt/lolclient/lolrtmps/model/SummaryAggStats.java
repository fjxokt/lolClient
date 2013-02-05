package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class SummaryAggStats extends ClassType {

	private List<SummaryAggStat> stats;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.SummaryAggStats";
	}

	public SummaryAggStats(TypedObject to) {
		super();
		stats = new ArrayList<SummaryAggStat>();
		Object[] objs = to.getArray("stats");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					stats.add(new SummaryAggStat(ts));
				}
			}
		}
	}
	
	public List<SummaryAggStat> getStats() {
		return stats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummaryAggStats [stats=");
		builder.append(stats);
		builder.append("]");
		return builder.toString();
	}

}
