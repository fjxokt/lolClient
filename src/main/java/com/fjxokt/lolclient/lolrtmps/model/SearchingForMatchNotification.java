package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class SearchingForMatchNotification extends ClassType {
	
	private List<PlayerJoinFailure> playerJoinFailures;
	// TODO: fix this one !
	private Object ghostGameSummoners;
	private List<QueueInfo> joinedQueues;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.matchmaking.SearchingForMatchNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.matchmaking.SearchingForMatchNotification";
	}
	
	public SearchingForMatchNotification(TypedObject to) {
		super();
		this.ghostGameSummoners = to.get("ghostGameSummoners");

		this.playerJoinFailures = new ArrayList<PlayerJoinFailure>();
		Object[] objs = to.getArray("playerJoinFailures");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tp = (TypedObject)o;
				if (tp != null) {
					playerJoinFailures.add(PlayerJoinFailure.createPlayerJoinFailure(tp));
				}
			}
		}
		
		this.joinedQueues = new ArrayList<QueueInfo>();
		objs = to.getArray("joinedQueues");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tq = (TypedObject)o;
				if (tq != null) {
					joinedQueues.add(new QueueInfo(tq));
				}
			}
		}
	}

	public List<PlayerJoinFailure> getPlayerJoinFailures() {
		return playerJoinFailures;
	}

	public Object getGhostGameSummoners() {
		return ghostGameSummoners;
	}

	public List<QueueInfo> getJoinedQueues() {
		return joinedQueues;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchingForMatchNotification [ghostGameSummoners=");
		builder.append(ghostGameSummoners);
		builder.append(", joinedQueues=");
		builder.append(joinedQueues);
		builder.append(", playerJoinFailures=");
		builder.append(playerJoinFailures);
		builder.append("]");
		return builder.toString();
	}

}
