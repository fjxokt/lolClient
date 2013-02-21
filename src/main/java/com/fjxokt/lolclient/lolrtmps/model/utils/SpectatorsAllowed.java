package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum SpectatorsAllowed {

	ALL(1, "ALL", "All"), NONE(2, "NONE", "None"),
	LOBBYONLY(3, "LOBBYONLY", "Lobby Only"), DROPINONLY(4, "DROPINONLY", "Friend List Only");
	
	private int id;
	private String name;
	private String text;
	
	SpectatorsAllowed(int id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
	}
	
	public static SpectatorsAllowed getStateFromString(String state) {
		SpectatorsAllowed[] values = SpectatorsAllowed.values();
		for (SpectatorsAllowed s : values) {
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
	
	public String getText() {
		return text;
	}
	
	public String toString() {
		return text;
	}
	
}
