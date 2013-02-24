package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum GameModeTypeConfig {
	
	BLIND_PICK(1, "Blind Pick"), DRAFT_MODE(2, "Draft Mode"),
	ARAM(4, "All Random"), TOURNAMENT_DRAFT(6, "Tournament Draft");

	private int typeConfigId;
	private String name;
	
	GameModeTypeConfig(int typeConfigId, String name) {
		this.typeConfigId = typeConfigId;
		this.name = name;
	}

	public int getTypeConfigId() {
		return typeConfigId;
	}

	public String getName() {
		return name;
	}
	
    @Override
	public String toString() {
		return name;
	}
	
}
