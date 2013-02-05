package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class Effect extends ClassType {

	private Integer effectId;
	private String gameCode;
	private String name;
	private RuneType runType;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.Effect";
	}
	
	public Effect(TypedObject to) {
		super();
		this.effectId = to.getInt("effectId");
		this.gameCode = to.getString("gameCode");
		this.name = to.getString("name");
		TypedObject tobj = to.getTO("runType");
		this.runType = (tobj == null) ? null : new RuneType(tobj);
	}

	public Integer getEffectId() {
		return effectId;
	}

	public String getGameCode() {
		return gameCode;
	}

	public String getName() {
		return name;
	}

	public RuneType getRunType() {
		return runType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Effect [effectId=");
		builder.append(effectId);
		builder.append(", gameCode=");
		builder.append(gameCode);
		builder.append(", name=");
		builder.append(name);
		builder.append(", runType=");
		builder.append(runType);
		builder.append("]");
		return builder.toString();
	}

}
