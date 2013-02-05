package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum TierType {

	BRONZE("BRONZE"), SILVER("SILVER"), GOLD("GOLD"), 
	PLATINUM("PLATINUM"), DIAMOND("DIAMOND"), CHALLENGER("CHALLENGER");
	
	private String type;
	
	TierType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public static TierType getTypeFromString(String type) {
		TierType[] values = TierType.values();
		for (TierType s : values) {
			if (s.getType().equals(type)) {
				return s;
			}
		}
		return null;
	}
	
}
