package com.fjxokt.lolclient.lolrtmps.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class MasteryBookDTO extends ClassType {
	
	private Double summonerId;
	private String dateString;
	private List<MasteryBookPageDTO> bookPages;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.masterybook.MasteryBookDTO";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("summonerId", summonerId);
		res.put("dateString", dateString);
		Object[] entries = new Object[bookPages.size()];
		for (int i=0; i<entries.length; i++) {
			MasteryBookPageDTO entry = bookPages.get(i);
			entries[i] = (entry == null) ? null : bookPages.get(i).getTypedObject();
		}
		res.put("bookPages", TypedObject.makeArrayCollection(entries));
		
		/*
			"sortByPageId":{
				"$type":"FluorineFx.ASObject, FluorineFx",
				"fields":{
				"$type":"System.Object[], mscorlib",
				"$values":[
				{
				"$type":"FluorineFx.ASObject, FluorineFx",
				"descending":false,
				"numeric":null,
				"name":"pageId",
				"caseInsensitive":false,
				"compareFunction":null,
				"TypeName":null
				}
				]
				},
				"unique":false,
				"compareFunction":null,
				"TypeName":null
			},
		 */
		
		// TODO: check this !!
		TypedObject f = new TypedObject();
		f.put("descending", false);
		f.put("name", "pageId");
		f.put("caseInsensitive", false);
		f.put("compareFunction", null);
		f.put("numeric", null);
		
		TypedObject t = new TypedObject();
		t.put("unique", false);
		t.put("compareFunction", null);
		t.put("fields", f);
		
		res.put("sortByPageId", t);

		return res;
	}
	
	public MasteryBookDTO(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.dateString = to.getString("dateString");
		
		this.bookPages = new ArrayList<MasteryBookPageDTO>();
		Object[] pages = to.getArray("bookPages");
		if (pages != null) {
			for (Object o : pages) {
				TypedObject tp = (TypedObject)o;
				if (tp != null) {
					bookPages.add(new MasteryBookPageDTO(tp));
				}
			}
		}
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public List<MasteryBookPageDTO> getBookPages() {
		return bookPages;
	}

	public void setBookPages(List<MasteryBookPageDTO> bookPages) {
		this.bookPages = bookPages;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasteryBookDTO [summonerId=");
		builder.append(summonerId);
		builder.append(", dateString=");
		builder.append(dateString);
		builder.append(", bookPages=");
		builder.append(bookPages);
		builder.append("]");
		return builder.toString();
	}

}
