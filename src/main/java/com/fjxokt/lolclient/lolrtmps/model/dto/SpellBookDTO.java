package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellBookDTO extends ClassType {

	private Double summonerId;
	private String dateString;
	private List<SpellBookPageDTO> bookPages;
	private SpellBookPageDTO defaultPage;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.spellbook.SpellBookDTO";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("summonerId", summonerId);
		res.put("dateString", dateString);
		Object[] entries = new Object[bookPages.size()];
		for (int i=0; i<entries.length; i++) {
			SpellBookPageDTO entry = bookPages.get(i);
			entries[i] = (entry == null) ? null : bookPages.get(i).getTypedObject();
		}
		res.put("bookPages", TypedObject.makeArrayCollection(entries));
		
		if (defaultPage != null) {
			res.put("defaultPage", defaultPage.getTypedObject());
		}
		
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
	
	public SpellBookDTO(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.dateString = to.getString("dateString");
		
		this.bookPages = new ArrayList<SpellBookPageDTO>();
		Object[] pages = to.getArray("bookPages");
		if (pages != null) {
			for (Object o : pages) {
				TypedObject tp = (TypedObject)o;
				if (tp != null) {
					bookPages.add(new SpellBookPageDTO(tp));
				}
			}
		}
		
		TypedObject tobj = to.getTO("defaultPage");
		this.defaultPage = (tobj == null) ? null : new SpellBookPageDTO(tobj);
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public List<SpellBookPageDTO> getBookPages() {
		return Collections.unmodifiableList(bookPages);
	}

	public void setBookPages(List<SpellBookPageDTO> bookPages) {
		this.bookPages = bookPages;
	}
	
	public SpellBookPageDTO getDefaultPage() {
		return this.defaultPage;
	}
	
	public void setDefaultPage(SpellBookPageDTO defaultPage) {
		this.defaultPage = defaultPage;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellBookDTO [summonerId=");
		builder.append(summonerId);
		builder.append(", dateString=");
		builder.append(dateString);
		builder.append(", bookPages=");
		builder.append(bookPages);
		builder.append(", defaultPage=");
		builder.append(defaultPage);
		builder.append("]");
		return builder.toString();
	}

}
