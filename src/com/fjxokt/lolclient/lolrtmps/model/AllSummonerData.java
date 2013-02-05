package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.gvaneyck.rtmp.TypedObject;

public class AllSummonerData extends ClassType {
	
	private SpellBookDTO spellBook;
	private SummonerDefaultSpells summonerDefaultSpells;
	private Summoner summoner;
	private SummonerLevel summonerLevel;
	private MasteryBookDTO masteryBook;
	private SummonerTalentsAndPoints summonerTalentsAndPoints;
	private SummonerLevelAndPoints summonerLevelAndPoints;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.AllSummonerData";
	}
	
	public AllSummonerData(TypedObject to) {
		super();
		TypedObject tobj = to.getTO("spellBook");
		this.spellBook = (tobj == null) ? null : new SpellBookDTO(tobj);
		tobj = to.getTO("summonerDefaultSpells");
		this.summonerDefaultSpells = (tobj == null) ? null : new SummonerDefaultSpells(tobj);
		tobj = to.getTO("summoner");
		this.summoner = (tobj == null) ? null : new Summoner(tobj);		
		tobj = to.getTO("summonerLevel");
		this.summonerLevel = (tobj == null) ? null : new SummonerLevel(tobj);
		tobj = to.getTO("masteryBook");
		this.masteryBook = (tobj == null) ? null : new MasteryBookDTO(tobj);
		tobj = to.getTO("summonerTalentsAndPoints");
		this.summonerTalentsAndPoints = (tobj == null) ? null : new SummonerTalentsAndPoints(tobj);
		tobj = to.getTO("summonerLevelAndPoints");
		this.summonerLevelAndPoints = (tobj == null) ? null : new SummonerLevelAndPoints(tobj);
	}

	public SpellBookDTO getSpellBook() {
		return spellBook;
	}

	public SummonerDefaultSpells getSummonerDefaultSpells() {
		return summonerDefaultSpells;
	}

	public Summoner getSummoner() {
		return summoner;
	}

	public SummonerLevel getSummonerLevel() {
		return summonerLevel;
	}

	public MasteryBookDTO getMasteryBook() {
		return masteryBook;
	}

	public SummonerTalentsAndPoints getSummonerTalentsAndPoints() {
		return summonerTalentsAndPoints;
	}
	
	public SummonerLevelAndPoints getSummonerLevelAndPoints() {
		return summonerLevelAndPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AllSummonerData [spellBook=");
		builder.append(spellBook);
		builder.append(", summonerDefaultSpells=");
		builder.append(summonerDefaultSpells);
		builder.append(", summoner=");
		builder.append(summoner);
		builder.append(", summonerLevel=");
		builder.append(summonerLevel);
		builder.append(", masteryBook=");
		builder.append(masteryBook);
		builder.append(", summonerTalentsAndPoints=");
		builder.append(summonerTalentsAndPoints);		
		builder.append(", summonerLevelAndPoints=");
		builder.append(summonerLevelAndPoints);
		builder.append("]");
		return builder.toString();
	}

}
