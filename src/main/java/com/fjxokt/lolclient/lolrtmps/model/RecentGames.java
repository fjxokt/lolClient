package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecentGames extends ClassType {
	
	// private String recentGamesJson;
	// TODO: private map playerGamesStatsMap ???
	private Double userId;
	private List<PlayerGameStats> gameStatistics;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.RecentGames";
	}
	
	public RecentGames(TypedObject to) {
		super();
		this.userId = to.getDouble("userId");
		this.gameStatistics = new ArrayList<PlayerGameStats>();
		Object[] objs = to.getArray("gameStatistics");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tg = (TypedObject)o;
				if (tg != null) {
					gameStatistics.add(new PlayerGameStats(tg));
				}
			}
		}
	}

	public Double getUserId() {
		return userId;
	}

	public List<PlayerGameStats> getGameStatistics() {
		return Collections.unmodifiableList(gameStatistics);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecentGames [gameStatistics=");
		builder.append(gameStatistics);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
