package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum GameMode {
	
	CLASSIC(1, "CLASSIC"), ARAM(2, "ARAM"), ODIN(3, "ODIN"), TUTORIAL(4, "TUTORIAL");
	
	private int id;
	private String name;
	
	GameMode(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static GameMode getModeFromString(String state) {
		GameMode[] values = GameMode.values();
		for (GameMode s : values) {
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
