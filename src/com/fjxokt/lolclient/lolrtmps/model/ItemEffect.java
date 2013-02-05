package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class ItemEffect extends ClassType {

	private Integer itemEffectId;
	private Integer effectId;
	private Integer itemId;
	private String value;
	private Effect effect;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.ItemEffect";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		res.put("itemEffectId", itemEffectId);
		res.put("effectId", effectId);
		res.put("itemId", itemId);
		res.put("value", value);
		res.put("effect", effect.getTypedObject());
		return res;
	}
	
	public ItemEffect(TypedObject to) {
		super();
		this.itemEffectId = to.getInt("itemEffectId");
		this.effectId = to.getInt("effectId");
		this.itemId = to.getInt("itemId");
		this.value = to.getString("value");
		TypedObject tobj = to.getTO("effect");
		this.effect = (tobj == null) ? null : new Effect(tobj);
	}

	public Integer getItemEffectId() {
		return itemEffectId;
	}

	public Integer getEffectId() {
		return effectId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public String getValue() {
		return value;
	}

	public Effect getEffect() {
		return effect;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemEffect [itemEffectId=");
		builder.append(itemEffectId);
		builder.append(", effectId=");
		builder.append(effectId);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append(", value=");
		builder.append(value);
		builder.append(", effect=");
		builder.append(effect);
		builder.append("]");
		return builder.toString();
	}

}
