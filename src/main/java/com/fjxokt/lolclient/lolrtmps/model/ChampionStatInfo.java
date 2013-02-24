package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChampionStatInfo extends ClassType {

	private Integer totalGamesPlayed;
	private Double accountId;
	private Double championId; // server returns a double eventhough it should be an integer
	private List<AggregatedStat> stats;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.ChampionStatInfo";
	}
	
	public ChampionStatInfo(TypedObject to) {
		super();
		this.totalGamesPlayed = to.getInt("totalGamesPlayed");
		this.accountId = to.getDouble("accountId");
		this.championId = to.getDouble("championId");
		this.stats = new ArrayList<AggregatedStat>();
		Object[] objs = to.getArray("stats");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					stats.add(new AggregatedStat(ts));
				}
			}
		}
	}

	public Integer getTotalGamesPlayed() {
		return totalGamesPlayed;
	}

	public Double getAccountId() {
		return accountId;
	}

	public Double getChampionId() {
		return championId;
	}

	public List<AggregatedStat> getStats() {
		return Collections.unmodifiableList(stats);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionStatInfo [accountId=");
		builder.append(accountId);
		builder.append(", championId=");
		builder.append(championId);
		builder.append(", stats=");
		builder.append(stats);
		builder.append(", totalGamesPlayed=");
		builder.append(totalGamesPlayed);
		builder.append("]");
		return builder.toString();
	}

}
