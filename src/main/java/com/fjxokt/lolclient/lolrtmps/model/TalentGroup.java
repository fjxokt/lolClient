package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class TalentGroup extends ClassType {

	private Integer tltGroupId;
	private String name;
	private Integer index;
	private List<TalentRow> talentRows;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.TalentGroup";
	}
	
	public TalentGroup(TypedObject to) {
		super();
		this.tltGroupId = to.getInt("tltGroupId");
		this.name = to.getString("name");
		this.index = to.getInt("index");
		
		this.talentRows = new ArrayList<TalentRow>();
		Object[] objs = to.getArray("talentRows");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ttr = (TypedObject)o;
				if (ttr != null) {
					talentRows.add(new TalentRow(ttr));
				}
			}
		}
	}

	public Integer getTltGroupId() {
		return tltGroupId;
	}

	public String getName() {
		return name;
	}

	public Integer getIndex() {
		return index;
	}

	public List<TalentRow> getTalentRows() {
		return talentRows;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TalentGroup [tltGroupId=");
		builder.append(tltGroupId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", index=");
		builder.append(index);
		builder.append(", talentRows=");
		builder.append(talentRows);
		builder.append("]");
		return builder.toString();
	}

}
