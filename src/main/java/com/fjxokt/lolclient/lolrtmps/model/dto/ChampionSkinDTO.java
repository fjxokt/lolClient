package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class ChampionSkinDTO extends ClassType {

	private Integer championId;
	private Integer skinId;
	private Boolean owned;
	private Boolean lastSelected;
	private Boolean stillObtainable;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.champion.ChampionSkinDTO";
	}
	
	public ChampionSkinDTO(TypedObject to) {
		super();
		this.lastSelected = to.getBool("lastSelected");
		this.stillObtainable = to.getBool("stillObtainable");
		this.owned = to.getBool("owned");
		this.championId = to.getInt("championId");
		this.skinId = to.getInt("skinId");
	}
	
	public ChampionSkinDTO(boolean lastSelected, boolean stillObtainable, boolean owned, Integer championId, Integer skinId) {
		super();
		this.lastSelected = lastSelected;
		this.stillObtainable = stillObtainable;
		this.owned = owned;
		this.championId = championId;
		this.skinId = skinId;
	}

	public boolean isLastSelected() {
		return lastSelected;
	}

	public boolean isStillObtainable() {
		return stillObtainable;
	}

	public boolean isOwned() {
		return owned;
	}

	public Integer getChampionId() {
		return championId;
	}

	public Integer getSkinId() {
		return skinId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionSkinDTO [championId=");
		builder.append(championId);
		builder.append(", skinId=");
		builder.append(skinId);
		builder.append(", owned=");
		builder.append(owned);
		builder.append(", lastSelected=");
		builder.append(lastSelected);
		builder.append(", stillObtainable=");
		builder.append(stillObtainable);
		builder.append("]");
		return builder.toString();
	}
	
}
