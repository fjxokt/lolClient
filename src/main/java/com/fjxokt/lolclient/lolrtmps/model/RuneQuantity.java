package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class RuneQuantity extends ClassType {

	private Integer runeId;
	private Integer quantity;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.runes.RuneQuantity";
	}
	
	public RuneQuantity(TypedObject to) {
		super();
		this.runeId = to.getInt("runeId");
		this.quantity = to.getInt("quantity");
	}

	public Integer getRuneId() {
		return runeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuneQuantity [quantity=");
		builder.append(quantity);
		builder.append(", runeId=");
		builder.append(runeId);
		builder.append("]");
		return builder.toString();
	}

}
