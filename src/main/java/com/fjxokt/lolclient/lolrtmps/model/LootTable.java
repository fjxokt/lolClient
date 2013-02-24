package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LootTable extends ClassType {

	private Double id;
	private Integer minNumberOfItemsReturned;
	private Integer type;
	private List<LootTableItem> lootTableItems;
	
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.inventory.loot.LootTable";
	}

	public LootTable(TypedObject to) {
		super();
		this.id = to.getDouble("id");
		this.minNumberOfItemsReturned = to.getInt("minNumberOfItemsReturned");
		this.type = to.getInt("type");
		this.id = to.getDouble("id");
		this.lootTableItems = new ArrayList<LootTableItem>();
		Object[] objs = to.getArray("lootTableItems");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tl = (TypedObject)o;
				if (tl != null) {
					lootTableItems.add(new LootTableItem(tl));
				}
			}
		}
	}

	public Double getId() {
		return id;
	}

	public Integer getMinNumberOfItemsReturned() {
		return minNumberOfItemsReturned;
	}

	public Integer getType() {
		return type;
	}

	public List<LootTableItem> getLootTableItems() {
		return Collections.unmodifiableList(lootTableItems);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LootTable [id=");
		builder.append(id);
		builder.append(", lootTableItems=");
		builder.append(lootTableItems);
		builder.append(", minNumberOfItemsReturned=");
		builder.append(minNumberOfItemsReturned);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
