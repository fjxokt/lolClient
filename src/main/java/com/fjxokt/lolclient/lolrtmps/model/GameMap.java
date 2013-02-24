package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.utils.GameMode;
import com.fjxokt.lolclient.lolrtmps.model.utils.Map;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMap extends ClassType {
	
	private Integer mapId;
	private Integer totalPlayers;
	private Integer minCustomPlayers;
	private String displayName;	// "Summoner's Rift"
	private String name;			// "SummonersRift"
	private String description;	// lot of blabla, needed ?
	// list<Object> polyPoints; ????
	private List<BotComposition> botCompositions;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.map.GameMap";
	}
	
	public GameMap(TypedObject to) {
		super();
		this.mapId = to.getInt("mapId");
		this.totalPlayers = to.getInt("totalPlayers");
		this.minCustomPlayers = to.getInt("minCustomPlayers");
		this.displayName = to.getString("displayName");
		this.name = to.getString("name");
		
		this.botCompositions = new ArrayList<BotComposition>();
		Object[] objs = to.getArray("botCompositions");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tb = (TypedObject)o;
				if (tb != null) {
					botCompositions.add(new BotComposition(tb));
				}
			}
		}
	}

	public GameMap(Integer mapId, Integer totalPlayers, Integer minCustomPlayers,
			String displayName, String name, String description) {
		super();
		this.mapId = mapId;
		this.totalPlayers = totalPlayers;
		this.minCustomPlayers = minCustomPlayers;
		this.displayName = displayName;
		this.name = name;
		this.description = description;
	}
	
	public static GameMode getModeFromMapId(int id) {
		switch (id) {
		case 1:
			return GameMode.CLASSIC;
		case 7:
			return GameMode.ARAM;
		case 8:
			return GameMode.ODIN;
		case 10:
			return GameMode.CLASSIC;
		default:
			return null;
		}
	}
	
	public static GameMap createDummy(int mapId, int totalPlayers, int minPlayers) {
		Map map = Map.getMapFromId(mapId);
		return new GameMap(mapId, totalPlayers, minPlayers, map.getMapName(), map.getMapNameId(), map.getMapDesc());
	}
	
	public Integer getMinCustomPlayers() {
		return minCustomPlayers;
	}

	public void setMinCustomPlayers(Integer minCustomPlayers) {
		this.minCustomPlayers = minCustomPlayers;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMapId() {
		return mapId;
	}

	public Integer getTotalPlayers() {
		return totalPlayers;
	}
	
	public void setTotalPlayers(Integer totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	public String getName() {
		return name;
	}
	
	public List<BotComposition> getBotCompositions() {
		return Collections.unmodifiableList(botCompositions);
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("mapId", mapId);
		res.put("totalPlayers", totalPlayers);
		res.put("minCustomPlayers", minCustomPlayers);
		res.put("displayName", displayName);
		res.put("name", name);
		res.put("description", description);
		res.put("dataVersion", null);
		res.put("futureData", null);

		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMap [mapId=");
		builder.append(mapId);
		builder.append(", totalPlayers=");
		builder.append(totalPlayers);
		builder.append(", minCustomPlayers=");
		builder.append(minCustomPlayers);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);		
		builder.append(", botCompositions=");
		builder.append(botCompositions);
		builder.append("]");
		return builder.toString();
	}

}
