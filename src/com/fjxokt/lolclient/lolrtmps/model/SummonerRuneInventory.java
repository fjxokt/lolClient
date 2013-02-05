package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerRuneInventory extends ClassType {

	private Double summonerId;
	private String dateString;
	private List<SummonerRune> summonerRunes;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.runes.SummonerRuneInventory";
	}
	
	public SummonerRuneInventory(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.dateString = to.getString("dateString");
		
		this.summonerRunes = new ArrayList<SummonerRune>();
		Object[] objs = to.getArray("summonerRunes");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tob = (TypedObject)o;
				if (tob != null) {
					summonerRunes.add(new SummonerRune(tob));
				}
			}
		}
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public String getDateString() {
		return dateString;
	}

	public List<SummonerRune> getSummonerRunes() {
		return summonerRunes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerRuneInventory [summonerId=");
		builder.append(summonerId);
		builder.append(", dateString=");
		builder.append(dateString);
		builder.append(", summonerRunes=");
		builder.append(summonerRunes);
		builder.append("]");
		return builder.toString();
	}

}
