package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.TalentEntry;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MasteryBookPageDTO extends ClassType {
	
	private Double pageId;
	private String name;
	private Double summonerId;
	private Date createDate;
	private Boolean current;
	private List<TalentEntry> talentEntries;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.masterybook.MasteryBookPageDTO";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("current", current);
		res.put("summonerId", summonerId);
		res.put("pageId", pageId);
		res.put("createDate", createDate);
		res.put("name", name);
		Object[] entries = new Object[talentEntries.size()];
		for (int i=0; i<entries.length; i++) {
			TalentEntry entry = talentEntries.get(i);
			entries[i] = (entry == null) ? null : talentEntries.get(i).getTypedObject();
		}
		res.put("talentEntries", TypedObject.makeArrayCollection(entries));

		return res;
	}
	
	public MasteryBookPageDTO(TypedObject to) {
		super();
		this.pageId = to.getDouble("pageId");
		this.name = to.getString("name");
		this.summonerId = to.getDouble("summonerId");
		this.createDate = to.getDate("createDate");
		this.current = to.getBool("current");
		
		talentEntries = new ArrayList<TalentEntry>();
		Object[] entries = to.getArray("talentEntries");
		if (entries != null) {
			for (Object o : entries) {
				TypedObject te = (TypedObject)o;
				if (te != null) {
					talentEntries.add(new TalentEntry(te));
				}
			}
		}
	}

	public Double getPageId() {
		return pageId;
	}

	public void setPageId(Double pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public List<TalentEntry> getTalentEntries() {
		return Collections.unmodifiableList(talentEntries);
	}

	public void setTalentEntries(List<TalentEntry> talentEntries) {
		this.talentEntries = talentEntries;
	}

	public Double getSummonerId() {
		return summonerId;
	}
	
	public Integer getTotalSetPoints() {
		Integer res = 0;
		for (TalentEntry e : talentEntries) {
			res += e.getRank();
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasteryBookPageDTO [pageId=");
		builder.append(pageId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", current=");
		builder.append(current);
		builder.append(", talentEntries=");
		builder.append(talentEntries);
		builder.append("]");
		return builder.toString();
	}

}
