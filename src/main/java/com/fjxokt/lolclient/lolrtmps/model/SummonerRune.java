package com.fjxokt.lolclient.lolrtmps.model;

import java.util.Date;

import com.gvaneyck.rtmp.TypedObject;

public class SummonerRune extends ClassType {

	private Double summonerId;
	private Integer runeId;
	private Date purchaseDate;
	private Integer quantity;
	private Rune rune;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.runes.SummonerRune";
	}
	
	public SummonerRune(TypedObject to) {
		super();
		this.summonerId = to.getDouble("summonerId");
		this.runeId = to.getInt("runeId");
		this.purchaseDate = to.getDate("purchaseDate");
		this.quantity = to.getInt("quantity");
		
		TypedObject obj = to.getTO("rune");
		this.rune = (obj == null) ? null : new Rune(obj);
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public Integer getRuneId() {
		return runeId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Rune getRune() {
		return rune;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerRune [summonerId=");
		builder.append(summonerId);
		builder.append(", runeId=");
		builder.append(runeId);
		builder.append(", purchaseDate=");
		builder.append(purchaseDate);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", rune=");
		builder.append(rune);
		builder.append("]");
		return builder.toString();
	}

}
