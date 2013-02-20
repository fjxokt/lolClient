package com.fjxokt.lolclient.model;

import java.util.List;

public class ItemRecipes {
	
	private Integer itemId;
	private List<Integer> requieredItemsIds;

	public ItemRecipes(/* ??? */) {
		
	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public List<Integer> getRequieredItemsIds() {
		return requieredItemsIds;
	}
	public void setRequieredItemsIds(List<Integer> requieredItemsIds) {
		this.requieredItemsIds = requieredItemsIds;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemRecipes [itemId=");
		builder.append(itemId);
		builder.append(", requieredItemsIds=");
		builder.append(requieredItemsIds);
		builder.append("]");
		return builder.toString();
	}

}
