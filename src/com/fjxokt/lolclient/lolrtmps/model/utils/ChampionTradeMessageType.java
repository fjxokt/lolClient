package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum ChampionTradeMessageType {

	CHAMPION_TRADE_REQUEST(1, "CHAMPION_TRADE_REQUEST"), CHAMPION_TRADE_CANCELLED(2, "CHAMPION_TRADE_CANCELLED"),
	CHAMPION_TRADE_NOT_ALLOWED(3, "CHAMPION_TRADE_NOT_ALLOWED"), CHAMPION_TRADE_ACCEPT(4, "CHAMPION_TRADE_ACCEPT");
	
	private int id;
	private String name;
	
	ChampionTradeMessageType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static ChampionTradeMessageType getTypeFromString(String state) {
		ChampionTradeMessageType[] values = ChampionTradeMessageType.values();
		for (ChampionTradeMessageType s : values) {
			if (s.getName().equals(state)) {
				return s;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
