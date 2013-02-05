package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class FailedJoinPlayer extends PlayerJoinFailure {

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.FailedJoinPlayer";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.FailedJoinPlayer";
	}
	
	public FailedJoinPlayer(TypedObject to) {
		super();
		this.reasonFailed = to.getString("reasonFailed");
		TypedObject tobj = to.getTO("summoner");
		this.summoner = (tobj == null) ? null : new Summoner(tobj);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FailedJoinPlayer [reasonFailed=");
		builder.append(reasonFailed);
		builder.append(", summoner=");
		builder.append(summoner);
		builder.append("]");
		return builder.toString();
	}

}
