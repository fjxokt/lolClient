package com.fjxokt.lolclient.model;

import java.util.List;

public class Item {
	
	private Integer id;
	private String name;
	private String description;
	private String iconPath;
	private Integer price;
	// TODO: lot of other stats
	private Integer epicness; // between O and 2
	private List<ItemCategory> categories;
	
	public Item(/* ??? */) {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getEpicness() {
		return epicness;
	}
	public void setEpicness(Integer epicness) {
		this.epicness = epicness;
	}

	public List<ItemCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ItemCategory> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", categories=");
		builder.append(categories);
		builder.append(", description=");
		builder.append(description);
		builder.append(", epicness=");
		builder.append(epicness);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

}
