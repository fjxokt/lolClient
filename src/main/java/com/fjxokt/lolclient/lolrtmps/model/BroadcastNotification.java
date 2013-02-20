package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class BroadcastNotification extends ClassType {

	private String json;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.broadcast.BroadcastNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.broadcast.BroadcastNotification";
	}
	
	public BroadcastNotification(TypedObject to) {
		super();
		this.json = to.getString("json");
	}

	public String getJson() {
		return json;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BroadcastNotification [json=");
		builder.append(json);
		builder.append("]");
		return builder.toString();
	}

}
