package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.Date;

public class TeamMemberInfoDTO extends ClassType {

	private Double playerId;
	private String status;
	private Date inviteDate;
	private Date joinDate;
	private String playerName;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.team.dto.TeamMemberInfoDTO";
	}
	
	public TeamMemberInfoDTO(TypedObject to) {
		super();
		this.playerId = to.getDouble("playerId");
		this.status = to.getString("status");
		this.inviteDate = to.getDate("inviteDate");
		this.joinDate = to.getDate("joinDate");
		this.playerName = to.getString("playerName");
	}

	public Double getPlayerId() {
		return playerId;
	}

	public String getStatus() {
		return status;
	}

	public Date getInviteDate() {
		return inviteDate;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public String getPlayerName() {
		return playerName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamMemberInfoDTO [playerId=");
		builder.append(playerId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", inviteDate=");
		builder.append(inviteDate);
		builder.append(", joinDate=");
		builder.append(joinDate);
		builder.append(", playerName=");
		builder.append(playerName);
		builder.append("]");
		return builder.toString();
	}

}
