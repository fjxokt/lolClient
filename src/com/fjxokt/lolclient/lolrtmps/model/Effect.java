package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class Effect extends ClassType {

	private Integer effectId;
	private String gameCode;
	private String name;
	private RuneType runeType;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.Effect";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		res.put("effectId", effectId);
		res.put("gameCode", gameCode);
		res.put("name", name);
		res.put("runeType", runeType.getTypedObject());
		return res;
	}
	
	public Effect(TypedObject to) {
		super();
		this.effectId = to.getInt("effectId");
		this.gameCode = to.getString("gameCode");
		this.name = to.getString("name");
		TypedObject tobj = to.getTO("runType");
		this.runeType = (tobj == null) ? null : new RuneType(tobj);
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

	public RuneType getRuneType() {
		return runeType;
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
		builder.append(runeType);
		builder.append("]");
		return builder.toString();
	}

}
