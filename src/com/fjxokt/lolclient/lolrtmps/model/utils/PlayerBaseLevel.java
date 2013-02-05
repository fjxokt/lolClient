package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum PlayerBaseLevel {

	BEGINNER("BEGINNER"), RTS_PLAYER("RTS_PLAYER"), VETERAN("VETERAN"), EXPERT("EXPERT");
	
	private String lvl;
	
	PlayerBaseLevel(String lvl) {
		this.lvl = lvl;
	}
	
	public String getLevel() {
		return lvl;
	}
	
	@Override
	public String toString() {
		return lvl;
	}
	
}
