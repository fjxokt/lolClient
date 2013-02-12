package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.utils.SimpleXML;

public abstract class Invitation {
	
	/*
	 * invite to matchmaking:
	 * <body>
	 * 		<inviteId>1847728772</inviteId>
	 * 		<userName>NinjaSUPER</userName>
	 * 		<profileIconId>536</profileIconId
	 * 		><gameType>NORMAL_GAME</gameType>
	 * 		<mapId>1</mapId>
	 * 		<queueId>2</queueId>
	 * 		<gameMode>classic_pvp</gameMode>
	 * 		<gameDifficulty></gameDifficulty>
	 * </body>
	 */
	
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
	
	private Integer inviteId;
	private String userName;
	private Integer profileIconId;
	private String gameType;
	private Integer mapId;
	
	public Invitation() {
		
	}
	
	public Invitation(Integer inviteId, String userName, Integer profilIconId,
			String gameType, Integer mapId) {
		super();
		this.inviteId = inviteId;
		this.userName = userName;
		this.profileIconId = profilIconId;
		this.gameType = gameType;
		this.mapId = mapId;
	}
	
	public Invitation(String xml) {
		this.inviteId = SimpleXML.getIntTagValue(xml, "inviteId");
		this.userName = SimpleXML.getTagValue(xml, "userName");
		this.profileIconId = SimpleXML.getIntTagValue(xml, "profileIconId");
		this.gameType = SimpleXML.getTagValue(xml, "gameType");
		this.mapId = SimpleXML.getIntTagValue(xml, "mapId");
	}
	
	public static Invitation createInvitationFromXML(String xml) {
		// if gameId, it means this invitation is for practice game
		if (SimpleXML.getTagValue(xml, "gameId") != null) {
			return new PracticeInvitation(xml);
		}
		// otherwise it's a matchmaking invitation
		return new MatchmakingInvitation(xml);
	}

	public Integer getInviteId() {
		return inviteId;
	}

	public void setInviteId(Integer inviteId) {
		this.inviteId = inviteId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getProfilIconId() {
		return profileIconId;
	}

	public void setProfilIconId(Integer profilIconId) {
		this.profileIconId = profilIconId;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public String toXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<inviteId>").append(inviteId).append("</inviteId>");
		builder.append("<userName>").append(userName).append("</userName>");
		builder.append("<profileIconId>").append(profileIconId).append("</profileIconId>");
		builder.append("<gameType>").append(gameType).append("</gameType>");
		builder.append("<mapId>").append(mapId).append("</mapId>");
		return builder.toString();
	}

}
