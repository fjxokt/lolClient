package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;

public class GameMapEnabledDTO extends ClassType {

	private Integer gameMapId;
	private Integer minPlayers;
	
	@Override
	protected String getTypeName() {
		return "GameMapEnabledDTO";
	}
	
	public GameMapEnabledDTO(Integer gameMapId, Integer minPlayers) {
		super();
		this.gameMapId = gameMapId;
		this.minPlayers = minPlayers;
	}

	public Integer getGameMapId() {
		return gameMapId;
	}

	public Integer getMinPlayers() {
		return minPlayers;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMapEnabledDTO [gameMapId=");
		builder.append(gameMapId);
		builder.append(", minPlayers=");
		builder.append(minPlayers);
		builder.append("]");
		return builder.toString();
	}

}
