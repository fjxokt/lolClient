package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum BotRole {

	ATTACKER("ATTACKER"), TANK("TANK"), MAGE("MAGE"), SUPPORT("SUPPORT"), BRAWLER("BRAWLER");
	
	private String name;
	
	BotRole(String name) {
		this.name = name;
	}

	public static BotRole getRoleFromString(String state) {
		BotRole[] values = BotRole.values();
		for (BotRole s : values) {
			if (s.getName().equals(state)) {
				return s;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
	
}
