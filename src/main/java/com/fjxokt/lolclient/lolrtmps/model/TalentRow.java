package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalentRow extends ClassType {

	private Integer tltRowId;
	private Integer tltGroupId;
	private Integer index;
	private Integer pointsToActivate;
	private List<Talent> talents;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.TalentRow";
	}
	
	public TalentRow(TypedObject to) {
		super();
		this.tltRowId = to.getInt("tltRowId");
		this.tltGroupId = to.getInt("tltGroupId");
		this.index = to.getInt("index");
		this.pointsToActivate = to.getInt("pointsToActivate");
		
		this.talents = new ArrayList<Talent>();
		Object[] objs = to.getArray("talents");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tto = (TypedObject)o;
				if (tto != null) {					
					talents.add(new Talent(tto));
				}
			}
		}
	}

	public Integer getTltRowId() {
		return tltRowId;
	}

	public Integer getTltGroupId() {
		return tltGroupId;
	}

	public Integer getIndex() {
		return index;
	}

	public Integer getPointsToActivate() {
		return pointsToActivate;
	}

	public List<Talent> getTalents() {
		return Collections.unmodifiableList(talents);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TalentRow [tltRowId=");
		builder.append(tltRowId);
		builder.append(", tltGroupId=");
		builder.append(tltGroupId);
		builder.append(", index=");
		builder.append(index);
		builder.append(", pointsToActivate=");
		builder.append(pointsToActivate);
		builder.append(", talents=");
		builder.append(talents);
		builder.append("]");
		return builder.toString();
	}

}
