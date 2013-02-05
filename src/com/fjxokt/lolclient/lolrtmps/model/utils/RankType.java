package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum RankType {

	I("I"), II("II"), III("III"), IV("IV"), V("V");
	
	private String type;
	
	RankType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public static RankType getTypeFromString(String type) {
		RankType[] values = RankType.values();
		for (RankType s : values) {
			if (s.getType().equals(type)) {
				return s;
			}
		}
		return null;
	}
	
}
