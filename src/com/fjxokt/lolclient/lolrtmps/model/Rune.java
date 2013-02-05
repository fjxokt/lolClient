package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.gvaneyck.rtmp.TypedObject;

public class Rune extends ClassType {

	private Integer itemId;
	private Integer tier;
	private Integer gameCode;
	private String baseType; // "RUNE" : this could be one of the retrieveInventoryTypes ??
	private String name;
	private String description;
	private Integer duration;
	private RuneType runeType;
	private List<ItemEffect> itemEffects;
	private String imagePath;
	private String displayDescription;
	private String displayName;
	private Integer uses;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.runes.Rune";
	}
	
	public Rune(TypedObject to) {
		super();
		this.itemId = to.getInt("itemId");
		this.tier = to.getInt("tier");
		this.gameCode = to.getInt("gameCode");
		this.baseType = to.getString("baseType");
		this.name = to.getString("name");
		this.description = to.getString("description");
		this.duration = to.getInt("duration");
		this.imagePath = to.getString("imagePath");
		this.displayDescription = to.getString("displayDescription");
		this.displayName = to.getString("displayName");
		this.uses = to.getInt("uses");
		
		TypedObject tobj = to.getTO("runeType");
		this.runeType = (tobj == null) ? null : new RuneType(tobj);
		
		this.itemEffects = new ArrayList<ItemEffect>();
		Object[] objs = to.getArray("itemEffects");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tr = (TypedObject)o;
				if (tr != null) {
					itemEffects.add(new ItemEffect(tr));
				}
			}
		}
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getTier() {
		return tier;
	}

	public Integer getGameCode() {
		return gameCode;
	}

	public String getBaseType() {
		return baseType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDuration() {
		return duration;
	}

	public RuneType getRuneType() {
		return runeType;
	}

	public List<ItemEffect> getItemEffects() {
		return itemEffects;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rune [baseType=");
		builder.append(baseType);
		builder.append(", description=");
		builder.append(description);
		builder.append(", displayDescription=");
		builder.append(displayDescription);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", gameCode=");
		builder.append(gameCode);
		builder.append(", imagePath=");
		builder.append(imagePath);
		builder.append(", itemEffects=");
		builder.append(itemEffects);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", runeType=");
		builder.append(runeType);
		builder.append(", tier=");
		builder.append(tier);
		builder.append(", uses=");
		builder.append(uses);
		builder.append("]");
		return builder.toString();
	}

}
