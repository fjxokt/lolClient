package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class LootTableItem extends ClassType {

	private Double id;
	private Integer lootTableId;
	private Double refId;
	private Integer type;
	private Integer likelihood;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.inventory.loot.LootTableItem";
	}
	
	public LootTableItem(TypedObject to) {
		super();
		this.id = to.getDouble("id");
		this.lootTableId = to.getInt("lootTableId");
		this.refId = to.getDouble("refId");
		this.type = to.getInt("type");
		this.likelihood = to.getInt("likelihood");
	}

	public Double getId() {
		return id;
	}

	public Integer getLootTableId() {
		return lootTableId;
	}

	public Double getRefId() {
		return refId;
	}

	public Integer getType() {
		return type;
	}

	public Integer getLikelihood() {
		return likelihood;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LootTableItem [id=");
		builder.append(id);
		builder.append(", likelihood=");
		builder.append(likelihood);
		builder.append(", lootTableId=");
		builder.append(lootTableId);
		builder.append(", refId=");
		builder.append(refId);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
