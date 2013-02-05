package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class RuneType extends ClassType {

	private Integer runeTypeId;
	private String name;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.runes.RuneType";
	}
	
	public RuneType(TypedObject to) {
		super();
		this.runeTypeId = to.getInt("runeTypeId");
		this.name = to.getString("name");
	}

	public Integer getRuneTypeId() {
		return runeTypeId;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "RuneType [runeTypeId=" + runeTypeId + ", name=" + name + "]";
	}

}
