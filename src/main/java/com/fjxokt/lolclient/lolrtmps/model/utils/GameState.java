package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum GameState {

	IDLE(0, "IDLE"), TEAM_SELECT(1, "TEAM_SELECT"), CHAMP_SELECT(2, "CHAMP_SELECT"),
	POST_CHAMP_SELECT(3, "POST_CHAMP_SELECT"), PRE_CHAMP_SELECT(17, "PRE_CHAMP_SELECT"),
	START_REQUESTED(4, "START_REQUESTED"), GAME_START_CLIENT(5, "GAME_START_CLIENT"), GameClientConnectedToServer(6, "GameClientConnectedToServer"),
	IN_PROGRESS(7, "IN_PROGRESS"), IN_QUEUE(8, "IN_QUEUE"), POST_GAME(9, "POST_GAME"), TERMINATED(10, "TERMINATED"),
	TERMINATED_IN_ERROR(10, "TERMINATED_IN_ERROR"), CHAMP_SELECT_CLIENT(11, "CHAMP_SELECT_CLIENT"), GameReconnect(12, "GameReconnect"),
	GAME_IN_PROGRESS(13, "GAME_IN_PROGRESS"), JOINING_CHAMP_SELECT(14, "JOINING_CHAMP_SELECT"), WAITING(15, "WAITING"), DISCONNECTED(16, "DISCONNECTED");
	
	private int id;
	private String name;
	
	GameState(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static GameState getStateFromString(String state) {
		GameState[] values = GameState.values();
		for (GameState s : values) {
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
