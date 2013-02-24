package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		return Collections.unmodifiableList(summonerIcons);
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
