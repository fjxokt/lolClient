package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.utils.ChampionTradeMessageType;
import com.gvaneyck.rtmp.TypedObject;

public class ChampionTradeMessage extends ClassType {

	private String champion;
	private String championTradeMessageType;
	private String senderInternalSummonerName;
	private String receiverInternalSummonerName;
	private Integer spell1;
	private Integer spell2;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.message.ChampionTradeMessage";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.message.ChampionTradeMessage";
	}
	
	public ChampionTradeMessage(TypedObject to) {
		super();
		this.champion = to.getString("champion");
		this.championTradeMessageType = to.getString("championTradeMessageType");
		this.senderInternalSummonerName = to.getString("senderInternalSummonerName");
		this.receiverInternalSummonerName = to.getString("receiverInternalSummonerName");
		this.spell1 = to.getInt("spell1");
		this.spell2 = to.getInt("spell2");
	}
	
	public ChampionTradeMessage(String champion,
			String championTradeMessageType, String senderInternalSummonerName,
			String receiverInternalSummonerName, Integer spell1, Integer spell2) {
		super();
		this.champion = champion;
		this.championTradeMessageType = championTradeMessageType;
		this.senderInternalSummonerName = senderInternalSummonerName;
		this.receiverInternalSummonerName = receiverInternalSummonerName;
		this.spell1 = spell1;
		this.spell2 = spell2;
	}
	
	public ChampionTradeMessage(ChampionTradeMessage msgSrc, ChampionTradeMessageType response) {
		this(msgSrc.getChampion(), response.getName(), msgSrc.getReceiverInternalSummonerName(), 
				msgSrc.getSenderInternalSummonerName(), msgSrc.getSpell1(), msgSrc.getSpell2());
	}

	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("champion", champion);
		res.put("championTradeMessageType", championTradeMessageType);
		res.put("senderInternalSummonerName", senderInternalSummonerName);
		res.put("receiverInternalSummonerName", receiverInternalSummonerName);
		res.put("spell1", spell1);
		res.put("spell2", spell2);
		
		return res;
	}

	public String getChampion() {
		return champion;
	}

	public String getChampionTradeMessageType() {
		return championTradeMessageType;
	}

	public String getSenderInternalSummonerName() {
		return senderInternalSummonerName;
	}

	public String getReceiverInternalSummonerName() {
		return receiverInternalSummonerName;
	}

	public Integer getSpell1() {
		return spell1;
	}

	public Integer getSpell2() {
		return spell2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionTradeMessage [champion=");
		builder.append(champion);
		builder.append(", championTradeMessageType=");
		builder.append(championTradeMessageType);
		builder.append(", senderInternalSummonerName=");
		builder.append(senderInternalSummonerName);
		builder.append(", receiverInternalSummonerName=");
		builder.append(receiverInternalSummonerName);
		builder.append(", spell1=");
		builder.append(spell1);
		builder.append(", spell2=");
		builder.append(spell2);
		builder.append("]");
		return builder.toString();
	}

}
