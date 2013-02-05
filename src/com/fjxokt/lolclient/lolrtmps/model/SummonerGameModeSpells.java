package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerGameModeSpells extends ClassType {
	
	private Integer spell1Id;
	private Integer spell2Id;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerGameModeSpells";
	}
	
	public SummonerGameModeSpells(TypedObject to) {
		super();
		this.spell1Id = to.getInt("spell1Id");
		this.spell2Id = to.getInt("spell2Id");
	}

	public Integer getSpell1Id() {
		return spell1Id;
	}

	public Integer getSpell2Id() {
		return spell2Id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerGameModeSpells [spell1Id=");
		builder.append(spell1Id);
		builder.append(", spell2Id=");
		builder.append(spell2Id);
		builder.append("]");
		return builder.toString();
	}

}
