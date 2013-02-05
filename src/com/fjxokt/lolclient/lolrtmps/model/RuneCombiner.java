package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class RuneCombiner extends ClassType {

	private Double id;
	private String name;
	private Integer inputTier;
	private Integer inputCount;
	private LootTable lootTable;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.inventory.loot.RuneCombiner";
	}
	
	public RuneCombiner(TypedObject to) {
		super();
		this.id = to.getDouble("id");
		this.name = to.getString("name");
		this.inputTier = to.getInt("inputTier");
		this.inputCount = to.getInt("inputCount");
		TypedObject tobj = to.getTO("lootTable");
		this.lootTable = (tobj == null) ? null : new LootTable(tobj);
	}

	public Double getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getInputTier() {
		return inputTier;
	}

	public Integer getInputCount() {
		return inputCount;
	}

	public LootTable getLootTable() {
		return lootTable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuneCombiner [id=");
		builder.append(id);
		builder.append(", inputCount=");
		builder.append(inputCount);
		builder.append(", inputTier=");
		builder.append(inputTier);
		builder.append(", lootTable=");
		builder.append(lootTable);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
