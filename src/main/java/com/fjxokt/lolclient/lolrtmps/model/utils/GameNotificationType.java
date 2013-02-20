package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum GameNotificationType {
	
	PLAYER_BANNED_FROM_GAME(1, "PLAYER_BANNED_FROM_GAME"), PLAYER_QUIT(2, "PLAYER_QUIT");
	
	private int id;
	private String name;
	
	GameNotificationType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static GameNotificationType getStateFromString(String state) {
		GameNotificationType[] values = GameNotificationType.values();
		for (GameNotificationType s : values) {
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
