package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class TalentEntry extends ClassType {
	
	private Integer talentId;
	private Integer rank;
	private Double summonerId;
	private Talent talent;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.masterybook.TalentEntry";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("talentId", talentId);
		res.put("rank", rank);
		// it seems that 'talent' is not needed

		return res;
	}
	
	public TalentEntry(TypedObject to) {
		super();
		this.talentId = to.getInt("talentId");
		this.rank = to.getInt("rank");
		this.summonerId = to.getDouble("summonerId");
		TypedObject tobj = to.getTO("talent");
		this.talent = (tobj == null) ? null :  new Talent(tobj);
	}
	
	public TalentEntry(Talent talent) {
		super();
		this.talentId = talent.getTltId();
		this.summonerId = -1d;
		this.rank = 0;
		this.talent = talent;
	}

	public Integer getTalentId() {
		return talentId;
	}

	public Integer getRank() {
		return rank;
	}
	
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Talent getTalent() {
		return talent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TalentEntry [talentId=");
		builder.append(talentId);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", talent=");
		builder.append(talent);
		builder.append("]");
		return builder.toString();
	}

}
