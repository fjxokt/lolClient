package com.fjxokt.lolclient.lolrtmps.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class SummonerIconInventoryDTO extends ClassType {

	private Double summonerId;
	// TODO: what is this list type ?
	private List<Object> summonerIcons;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.icon.SummonerIconInventoryDTO";
	}
	
	public SummonerIconInventoryDTO(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.summonerIcons = new ArrayList<Object>();
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public List<Object> getSummonerIcons() {
		return summonerIcons;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerIconInventoryDTO [summonerId=");
		builder.append(summonerId);
		builder.append(", summonerIcons=");
		builder.append(summonerIcons);
		builder.append("]");
		return builder.toString();
	}

}
