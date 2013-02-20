package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.utils.SimpleXML;

public class MatchmakingInvitation extends Invitation {

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
	
	private Integer queueId;
	private String gameMode;
	private String gameDifficulty;
	
	public MatchmakingInvitation() {
		
	}
	
	public MatchmakingInvitation(Integer inviteId, String username,
			Integer profilIconId, String gameType, Integer mapId, Integer queueId,
			String gameMode, String gameDifficulty) {
		super(inviteId, username, profilIconId, gameType, mapId);
		this.queueId = queueId;
		this.gameMode = gameMode;
		this.gameDifficulty = gameDifficulty;
	}
	
	public MatchmakingInvitation(String xml) {
		super(xml);
		this.queueId = SimpleXML.getIntTagValue(xml, "queueId");
		this.gameMode = SimpleXML.getTagValue(xml, "gameMode");
		this.gameDifficulty = SimpleXML.getTagValue(xml, "gameDifficulty");
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getGameDifficulty() {
		return gameDifficulty;
	}

	public void setGameDifficulty(String gameDifficulty) {
		this.gameDifficulty = gameDifficulty;
	}
	
	@Override
	public String toXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<body>");
		builder.append(super.toXML());
		builder.append("<queueId>").append(queueId).append("</queueId>");
		builder.append("<gameMode>").append(gameMode).append("</gameMode>");
		builder.append("<gameDifficulty>").append(gameDifficulty).append("</gameDifficulty>");
		builder.append("</body>");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MatchmakingInvitation [gameDifficulty=");
		builder.append(gameDifficulty);
		builder.append(", gameMode=");
		builder.append(gameMode);
		builder.append(", queueId=");
		builder.append(queueId);
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
