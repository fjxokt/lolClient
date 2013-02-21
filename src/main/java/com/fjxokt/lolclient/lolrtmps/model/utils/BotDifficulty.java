package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum BotDifficulty {

	NONE(1, "NONE"), EASY(2, "EASY"), MEDIUM(3, "MEDIUM");
	
	private int id;
	private String name;
	
	BotDifficulty(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static BotDifficulty getStateFromString(String state) {
		BotDifficulty[] values = BotDifficulty.values();
		for (BotDifficulty s : values) {
			if (s.getName().equals(state)) {
				return s;
			}
		}
		return null;
	}
	
}
