package com.fjxokt.lolclient.lolrtmps.model.utils;


public enum Map {
	
	// TODO: I should not use this class and use the getGameMapList() method
	SR(1, GameMode.CLASSIC, "Summoner's Rift", "SummonersRift", 10, "The oldest and most venerated Field of Justice is known as Summoner's Rift..."),
	PG(7, GameMode.ARAM, "The Proving Grounds", "ProvingGroundsARAM", 10, ""),
	CS(8, GameMode.ODIN, "The Crystal Scar", "CrystalScar", 10, ""),
	TT(10, GameMode.CLASSIC, "The Twisted Treeline!", "TwistedTreeline", 6, ""),
	HA(12, GameMode.ARAM, "Howling Abyss", "HowlingAbyss", 10, "");

	private int mapId;
	private GameMode mode;
	private String mapName;
	private String mapNameId;
	private int maxPlayers;
	private String mapDesc;
	
	Map(int mapId, GameMode mode, String mapName, String mapNameId, int maxPlayers, String mapDesc) {
		this.mapId = mapId;
		this.mode = mode;
		this.mapName = mapName;
		this.mapNameId = mapNameId;
		this.maxPlayers = maxPlayers;
		this.mapDesc = mapDesc;
	}
	
	public static Map getMapFromId(int id) {
		switch (id) {
		case 1:
			return SR;
		case 7:
			return PG;
		case 8:
			return CS;
		case 10:
			return TT;
		case 12:
			return HA;
		default:
			throw new RuntimeException("Map with id " + id + " is unknown. Fix this!");
		}
	}

	public int getMapId() {
		return mapId;
	}
	
	public GameMode getGameMode() {
		return mode;
	}

	public String getMapName() {
		return mapName;
	}

	public String getMapNameId() {
		return mapNameId;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String getMapDesc() {
		return mapDesc;
	}
	
	public String toString() {
		return mapName;
	}

}
