package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SlotEntry extends ClassType {
	
	private Integer runeId;
	private Integer runeSlotId;
	// TODO: figure out what these two things are. Apparently they are always null
	private Object runeSlot;
	private Object rune;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.spellbook.SlotEntry";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());

		res.put("runeId", runeId);
		res.put("runeSlotId", runeSlotId);
		res.put("runeSlot", null);
		res.put("rune", null);
		
		return res;
	}
	
	public SlotEntry(TypedObject to) {
		super();
		this.runeId = to.getInt("runeId");
		this.runeSlotId = to.getInt("runeSlotId");
		this.runeSlot = null;
		this.rune = null;
	}

	public Integer getRuneId() {
		return runeId;
	}

	public void setRuneId(Integer runeId) {
		this.runeId = runeId;
	}

	public Integer getRuneSlotId() {
		return runeSlotId;
	}

	public void setRuneSlotId(Integer runeSlotId) {
		this.runeSlotId = runeSlotId;
	}

	public Object getRuneSlot() {
		return runeSlot;
	}

	public void setRuneSlot(Object runeSlot) {
		this.runeSlot = runeSlot;
	}

	public Object getRune() {
		return rune;
	}

	public void setRune(Object rune) {
		this.rune = rune;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SlotEntry [runeId=");
		builder.append(runeId);
		builder.append(", runeSlotId=");
		builder.append(runeSlotId);
		builder.append(", runeSlot=");
		builder.append(runeSlot);
		builder.append(", rune=");
		builder.append(rune);
		builder.append("]");
		return builder.toString();
	}

}
