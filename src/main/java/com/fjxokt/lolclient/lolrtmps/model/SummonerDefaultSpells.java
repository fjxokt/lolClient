package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SummonerDefaultSpells extends ClassType {
	
	private Double summonerId;
	private Map<String, SummonerGameModeSpells> summonerDefaultSpellMap;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerDefaultSpells";
	}
	
	public SummonerDefaultSpells(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.summonerDefaultSpellMap = new HashMap<String, SummonerGameModeSpells>();
		TypedObject object = to.getTO("summonerDefaultSpellMap");
		if (object != null) {
			for (String key : object.keySet()) {
				TypedObject tobj = (TypedObject)object.get(key);
				if (tobj != null) {
					summonerDefaultSpellMap.put(key, new SummonerGameModeSpells(tobj));
				}
			}
		}
	}

	public Map<String, SummonerGameModeSpells> getSummonerDefaultSpellMap() {
		return Collections.unmodifiableMap(summonerDefaultSpellMap);
	}

	public void setSummonerDefaultSpellMap(Map<String, SummonerGameModeSpells> summonerDefaultSpellMap) {
		this.summonerDefaultSpellMap = summonerDefaultSpellMap;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerDefaultSpells [summonerId=");
		builder.append(summonerId);
		builder.append(", summonerDefaultSpellMap=");
		builder.append(summonerDefaultSpellMap);
		builder.append("]");
		return builder.toString();
	}

}
