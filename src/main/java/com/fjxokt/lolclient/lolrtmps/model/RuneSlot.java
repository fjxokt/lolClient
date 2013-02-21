package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class RuneSlot extends ClassType {
	
	private Integer id;
	private Integer minLevel;
	private RuneType runeType;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.RuneSlot";
	}
	
	public RuneSlot(TypedObject to) {
		super();
		this.id = to.getInt("id");
		this.minLevel = to.getInt("minLevel");
		TypedObject tobj = to.getTO("runeType");
		this.runeType = (tobj == null) ? null : new RuneType(tobj);
	}

	public Integer getId() {
		return id;
	}

	public Integer getMinLevel() {
		return minLevel;
	}

	public RuneType getRuneType() {
		return runeType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuneSlot [id=");
		builder.append(id);
		builder.append(", minLevel=");
		builder.append(minLevel);
		builder.append(", runeType=");
		builder.append(runeType);
		builder.append("]");
		return builder.toString();
	}

}
