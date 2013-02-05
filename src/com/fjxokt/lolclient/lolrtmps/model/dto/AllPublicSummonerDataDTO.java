package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.SpellBook;
import com.fjxokt.lolclient.lolrtmps.model.SummonerDefaultSpells;
import com.fjxokt.lolclient.lolrtmps.model.SummonerLevel;
import com.fjxokt.lolclient.lolrtmps.model.SummonerLevelAndPoints;
import com.fjxokt.lolclient.lolrtmps.model.SummonerTalentsAndPoints;
import com.gvaneyck.rtmp.TypedObject;

public class AllPublicSummonerDataDTO extends ClassType {

	private SpellBook spellBook;
	private SummonerDefaultSpells summonerDefaultSpells;
	private SummonerTalentsAndPoints summonerTalentsAndPoints;
	private BasePublicSummonerDTO summoner;
	private SummonerLevelAndPoints summonerLevelAndPoints;
	private SummonerLevel summonerLevel;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.AllPublicSummonerDataDTO";
	}
	
	public AllPublicSummonerDataDTO(TypedObject to) {
		super();
		TypedObject obj = to.getTO("spellBook");
		this.spellBook = (obj == null) ? null : new SpellBook(obj);
		obj = to.getTO("summonerDefaultSpells");
		this.summonerDefaultSpells = (obj == null) ? null : new SummonerDefaultSpells(obj);
		obj = to.getTO("summonerTalentsAndPoints");
		this.summonerTalentsAndPoints = (obj == null) ? null : new SummonerTalentsAndPoints(obj);
		obj = to.getTO("summoner");
		this.summoner = (obj == null) ? null : new BasePublicSummonerDTO(obj);
		obj = to.getTO("summonerLevelAndPoints");
		this.summonerLevelAndPoints = (obj == null) ? null : new SummonerLevelAndPoints(obj);
		obj = to.getTO("summonerLevel");
		this.summonerLevel = (obj == null) ? null : new SummonerLevel(obj);
	}

	public SpellBook getSpellBook() {
		return spellBook;
	}

	public SummonerDefaultSpells getSummonerDefaultSpells() {
		return summonerDefaultSpells;
	}

	public SummonerTalentsAndPoints getSummonerTalentsAndPoints() {
		return summonerTalentsAndPoints;
	}

	public BasePublicSummonerDTO getSummoner() {
		return summoner;
	}

	public SummonerLevelAndPoints getSummonerLevelAndPoints() {
		return summonerLevelAndPoints;
	}

	public SummonerLevel getSummonerLevel() {
		return summonerLevel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AllPublicSummonerDataDTO [spellBook=");
		builder.append(spellBook);
		builder.append(", summonerDefaultSpells=");
		builder.append(summonerDefaultSpells);
		builder.append(", summonerTalentsAndPoints=");
		builder.append(summonerTalentsAndPoints);
		builder.append(", summoner=");
		builder.append(summoner);
		builder.append(", summonerLevelAndPoints=");
		builder.append(summonerLevelAndPoints);
		builder.append(", summonerLevel=");
		builder.append(summonerLevel);
		builder.append("]");
		return builder.toString();
	}

}
