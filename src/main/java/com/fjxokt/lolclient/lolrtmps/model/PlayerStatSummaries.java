package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatSummaries extends ClassType {

	private Double userId;
	private List<PlayerStatSummary> playerStatSummarySet;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerStatSummaries";
	}
	
	public PlayerStatSummaries(TypedObject to) {
		super();
		this.userId = to.getDouble("userId");
		this.playerStatSummarySet = new ArrayList<PlayerStatSummary>();
		Object[] objs = to.getArray("playerStatSummarySet");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tp = (TypedObject)o;
				if (tp != null) {
					playerStatSummarySet.add(new PlayerStatSummary(tp));
				}
			}
		}
	}

	public Double getUserId() {
		return userId;
	}

	public List<PlayerStatSummary> getPlayerStatSummarySet() {
		return Collections.unmodifiableList(playerStatSummarySet);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerStatSummaries [userId=");
		builder.append(userId);
		builder.append(", playerStatSummarySet=");
		builder.append(playerStatSummarySet);
		builder.append("]");
		return builder.toString();
	}

}
