package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class PlayerDTO extends ClassType {
	
	private Double playerId;
	// teamsSummary
	// createdTeams
	// playerTeams

	@Override
	protected String getTypeName() {
		return "com.riotgames.team.dto.PlayerDTO";
	}
	
	public PlayerDTO(TypedObject to) {
		super();
		this.playerId = to.getDouble("playerId");
	}

	public Double getPlayerId() {
		return playerId;
	}

	@Override
	public String toString() {
		return "PlayerDTO [playerId=" + playerId + "]";
	}

}
