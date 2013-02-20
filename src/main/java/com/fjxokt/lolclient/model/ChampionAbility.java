package com.fjxokt.lolclient.model;

public class ChampionAbility {

	private Integer id;
	private Integer rank;
	private Integer championId;
	private String name;
	private String cost;
	private String cooldown;
	private String iconPath;
	private Double range;
	private String effect;
	private String description;
	private String hotkey;
	
	public ChampionAbility( /* ??? */) {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getChampionId() {
		return championId;
	}
	public void setChampionId(Integer championId) {
		this.championId = championId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getCooldown() {
		return cooldown;
	}
	public void setCooldown(String cooldown) {
		this.cooldown = cooldown;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public Double getRange() {
		return range;
	}
	public void setRange(Double range) {
		this.range = range;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHotkey() {
		return hotkey;
	}
	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionAbility [championId=");
		builder.append(championId);
		builder.append(", cooldown=");
		builder.append(cooldown);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", description=");
		builder.append(description);
		builder.append(", effect=");
		builder.append(effect);
		builder.append(", hotkey=");
		builder.append(hotkey);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", range=");
		builder.append(range);
		builder.append(", rank=");
		builder.append(rank);
		builder.append("]");
		return builder.toString();
	}



	
}
