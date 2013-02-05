package com.fjxokt.lolclient.model;

public class ChampionSkin {

	private Integer id;
	private Boolean isBase;
	private Integer rank;
	private Integer championId;
	private String name;
	private String displayName;
	private String portraitPath;
	private String splashPath;
	
	public ChampionSkin(/* ??? */) {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getIsBase() {
		return isBase;
	}
	public void setIsBase(Boolean isBase) {
		this.isBase = isBase;
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPortraitPath() {
		return portraitPath;
	}
	public void setPortraitPath(String portraitPath) {
		this.portraitPath = portraitPath;
	}
	public String getSplashPath() {
		return splashPath;
	}
	public void setSplashPath(String splashPath) {
		this.splashPath = splashPath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionSkin [championId=");
		builder.append(championId);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", id=");
		builder.append(id);
		builder.append(", isBase=");
		builder.append(isBase);
		builder.append(", name=");
		builder.append(name);
		builder.append(", portraitPath=");
		builder.append(portraitPath);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", splashPath=");
		builder.append(splashPath);
		builder.append("]");
		return builder.toString();
	}
	
	
}
