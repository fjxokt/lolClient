package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class PlayerChampionSelectionDTO extends ClassType {

	private String summonerInternalName;
	private Integer championId;	
	private Integer spell1Id; 	// -1 when no selection
	private Integer spell2Id; 	// -1 when no selection
	private Integer selectedSkinIndex;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.PlayerChampionSelectionDTO";
	}
	
	public PlayerChampionSelectionDTO(TypedObject to) {
		super();
		this.summonerInternalName = to.getString("summonerInternalName");
		this.championId = to.getInt("championId");
		this.spell1Id = to.getInt("spell1Id");
		this.spell2Id = to.getInt("spell2Id");
		this.selectedSkinIndex = to.getInt("selectedSkinIndex");
	}
	
	public Integer getChampionId() {
		return championId;
	}

	public void setChampionId(Integer championId) {
		this.championId = championId;
	}

	public Integer getSpell1Id() {
		return spell1Id;
	}

	public void setSpell1Id(Integer spell1Id) {
		this.spell1Id = spell1Id;
	}

	public Integer getSpell2Id() {
		return spell2Id;
	}

	public void setSpell2Id(Integer spell2Id) {
		this.spell2Id = spell2Id;
	}

	public Integer getSelectedSkinIndex() {
		return selectedSkinIndex;
	}

	public void setSelectedSkinIndex(Integer selectedSkinIndex) {
		this.selectedSkinIndex = selectedSkinIndex;
	}

	public String getSummonerInternalName() {
		return summonerInternalName;
	}

	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("summonerInternalName", summonerInternalName);
		res.put("championId", championId);
		res.put("spell1Id", spell1Id);
		res.put("spell2Id", spell2Id);
		res.put("selectedSkinIndex", selectedSkinIndex);
		res.put("dataVersion", 0);
		res.put("futureData", null);

		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerChampionSelectionDTO [summonerInternalName=");
		builder.append(summonerInternalName);
		builder.append(", championId=");
		builder.append(championId);
		builder.append(", spell1Id=");
		builder.append(spell1Id);
		builder.append(", spell2Id=");
		builder.append(spell2Id);
		builder.append(", selectedSkinIndex=");
		builder.append(selectedSkinIndex);
		builder.append("]");
		return builder.toString();
	}

}
