package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class TeamId extends ClassType {

	private String fullId;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.team.TeamId";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		res.put("fullId", fullId);
		return res;
	}
	
	public TeamId(TypedObject to) {
		super();
		this.fullId = to.getString("fullId");
	}

	public String getFullId() {
		return fullId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamId [fullId=");
		builder.append(fullId);
		builder.append("]");
		return builder.toString();
	}

}
