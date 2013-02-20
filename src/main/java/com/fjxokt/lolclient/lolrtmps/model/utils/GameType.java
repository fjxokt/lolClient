package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum GameType {

	PRACTICE_GAME(1, "PRACTICE_GAME"), NORMAL_GAME(2, "NORMAL_GAME"),
	MATCHED_GAME(3, "MATCHED_GAME"), CUSTOM_GAME(4, "CUSTOM_GAME");
	
	private int id;
	private String name;
	
	GameType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
