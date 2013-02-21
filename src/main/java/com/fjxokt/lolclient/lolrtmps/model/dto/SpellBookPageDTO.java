package com.fjxokt.lolclient.lolrtmps.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.SlotEntry;
import com.gvaneyck.rtmp.TypedObject;

public class SpellBookPageDTO extends ClassType {

	private Integer pageId;
	private Boolean current;
	private String name;
	private Date createDate;
	private Integer summonerId;
	private List<SlotEntry> slotEntries;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.spellbook.SpellBookPageDTO";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());

		res.put("pageId", pageId);
		res.put("current", current);
		res.put("name", name);
		res.put("createDate", createDate);
		res.put("summonerId", summonerId);
		Object[] entries = new Object[slotEntries.size()];
		for (int i=0; i<entries.length; i++) {
			SlotEntry entry = slotEntries.get(i);
			entries[i] = (entry == null) ? null : slotEntries.get(i).getTypedObject();
		}
		res.put("slotEntries", TypedObject.makeArrayCollection(entries));
		
		return res;		
	}
	
	public SpellBookPageDTO(TypedObject to) {
		super();
		this.pageId = to.getInt("pageId");
		this.current = to.getBool("current");
		this.name = to.getString("name");
		this.createDate = to.getDate("createDate");
		this.summonerId = to.getInt("summonerId");
		
		this.slotEntries = new ArrayList<SlotEntry>();
		Object[] entries = to.getArray("slotEntries");
		if (entries != null) {
			for (Object o : entries) {
				TypedObject te = (TypedObject)o;
				if (te != null) {
					slotEntries.add(new SlotEntry(te));
				}
			}
		}
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
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

	public List<SlotEntry> getSlotEntries() {
		return slotEntries;
	}

	public void setSlotEntries(List<SlotEntry> slotEntries) {
		this.slotEntries = slotEntries;
	}

	public Integer getSummonerId() {
		return summonerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellBookPageDTO [pageId=");
		builder.append(pageId);
		builder.append(", current=");
		builder.append(current);
		builder.append(", name=");
		builder.append(name);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", slotEntries=");
		builder.append(slotEntries);
		builder.append("]");
		return builder.toString();
	}

}
