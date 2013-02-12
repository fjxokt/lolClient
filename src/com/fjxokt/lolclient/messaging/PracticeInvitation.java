package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.utils.SimpleXML;

public class PracticeInvitation extends Invitation {
	
	/*
	 * invite to practice game
	 * <body>
	 * 		<inviteId>883308981</inviteId>
	 * 		<userName>fjxokt</userName>
	 * 		<profileIconId>28</profileIconId>
	 * 		<gameType>PRACTICE_GAME</gameType>
	 * 		<gameTypeIndex>practiceGame_gameMode_GAME_CFG_PICK_BLIND</gameTypeIndex>
	 * 		<gameId>671954227</gameId>
	 * 		<mapId>1</mapId>
	 * 		<gamePassword></gamePassword>
	 * </body>
	 */
	
	private Integer gameId;
	private String gameTypeIndex;
	private String gamePassword;
	
	public PracticeInvitation() {
		
	}
	
	public PracticeInvitation(Integer inviteId, String username,
			Integer profilIconId, String gameType, Integer mapId,
			Integer gameId, String gameTypeIndex, String gamePassword) {
		super(inviteId, username, profilIconId, gameType, mapId);
		this.gameId = gameId;
		this.gameTypeIndex = gameTypeIndex;
		this.gamePassword = gamePassword;
	}
	
	public PracticeInvitation(String xml) {
		super(xml);
		System.out.println("GDSGDSG");
		this.gameId = SimpleXML.getIntTagValue(xml, "gameId");
		this.gameTypeIndex = SimpleXML.getTagValue(xml, "gameTypeIndex");
		this.gamePassword = SimpleXML.getTagValue(xml, "gamePassword");
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameTypeIndex() {
		return gameTypeIndex;
	}

	public void setGameTypeIndex(String gameTypeIndex) {
		this.gameTypeIndex = gameTypeIndex;
	}

	public String getGamePassword() {
		return gamePassword;
	}

	public void setGamePassword(String gamePassword) {
		this.gamePassword = gamePassword;
	}
	
	@Override
	public String toXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<body>");
		builder.append(super.toXML());
		builder.append("<gameId>").append(gameId).append("</gameId>");
		builder.append("<gameTypeIndex>").append(gameTypeIndex).append("</gameTypeIndex>");
		if (gamePassword != null && !gamePassword.isEmpty()) {
			builder.append("<gamePassword>").append(gamePassword).append("</gamePassword>");
		}
		builder.append("</body>");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PracticeInvitation [gameId=");
		builder.append(gameId);
		builder.append(", gamePassword=");
		builder.append(gamePassword);
		builder.append(", gameTypeIndex=");
		builder.append(gameTypeIndex);
		builder.append(", getGameType()=");
		builder.append(getGameType());
		builder.append(", getInviteId()=");
		builder.append(getInviteId());
		builder.append(", getMapId()=");
		builder.append(getMapId());
		builder.append(", getProfilIconId()=");
		builder.append(getProfilIconId());
		builder.append(", getUserName()=");
		builder.append(getUserName());
		builder.append("]");
		return builder.toString();
	}
	
	

}
