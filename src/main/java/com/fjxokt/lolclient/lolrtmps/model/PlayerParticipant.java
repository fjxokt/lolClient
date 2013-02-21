package com.fjxokt.lolclient.lolrtmps.model;

import java.util.Date;

import com.gvaneyck.rtmp.TypedObject;

public class PlayerParticipant extends Participant {
	
	private Integer accountId;
	private Integer summonerId;
	private Integer profileIconId;
	private boolean teamOwner;
	private Date dateOfBirth;
	private Integer queueRating;
	private Integer pickMode;
	private Integer pickTurn;
	private Integer teamParticipantId;
	private boolean clientInSynch;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.PlayerParticipant";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.PlayerParticipant";
	}
	
	public PlayerParticipant(TypedObject oo) {
		super();
		this.accountId = oo.getInt("accountId");
		this.summonerName = oo.getString("summonerName");
		this.summonerInternalName = oo.getString("summonerInternalName");
		this.summonerId = oo.getInt("summonerId");
		this.profileIconId = oo.getInt("profileIconId");
		this.teamOwner = oo.getBool("teamOwner");
		this.dateOfBirth = oo.getDate("dateOfBirth");
		this.queueRating = oo.getInt("queueRating");
		this.botDifficulty = oo.getString("botDifficulty");
		this.pickMode = oo.getInt("pickMode");
		this.pickTurn = oo.getInt("pickTurn");
		this.teamParticipantId = oo.getInt("teamParticipantId");
		this.clientInSynch = oo.getBool("clientInSynch");
	}

	public PlayerParticipant(Integer accountId, String summonerName,
			String summonerInternalName, Integer summonerId, Integer profileIconId,
			boolean teamOwner, Date dateOfBirth, Integer queueRating,
			String botDifficulty, Integer pickMode, Integer pickTurn,
			Integer teamParticipantId, boolean clientInSynch) {
		super();
		this.accountId = accountId;
		this.summonerName = summonerName;
		this.summonerInternalName = summonerInternalName;
		this.summonerId = summonerId;
		this.profileIconId = profileIconId;
		this.teamOwner = teamOwner;
		this.dateOfBirth = dateOfBirth;
		this.queueRating = queueRating;
		this.botDifficulty = botDifficulty;
		this.pickMode = pickMode;
		this.pickTurn = pickTurn;
		this.teamParticipantId = teamParticipantId;
		this.clientInSynch = clientInSynch;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public Integer getSummonerId() {
		return summonerId;
	}

	public Integer getProfileIconId() {
		return profileIconId;
	}

	public boolean isTeamOwner() {
		return teamOwner;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Integer getQueueRating() {
		return queueRating;
	}

	public Integer getPickMode() {
		return pickMode;
	}

	public Integer getPickTurn() {
		return pickTurn;
	}

	public Integer getTeamParticipantId() {
		return teamParticipantId;
	}

	public boolean isClientInSynch() {
		return clientInSynch;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerParticipant [accountId=");
		builder.append(accountId);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append(", summonerInternalName=");
		builder.append(summonerInternalName);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append(", teamOwner=");
		builder.append(teamOwner);
		builder.append(", dateOfBirth=");
		builder.append(dateOfBirth);
		builder.append(", queueRating=");
		builder.append(queueRating);
		builder.append(", botDifficulty=");
		builder.append(botDifficulty);
		builder.append(", pickMode=");
		builder.append(pickMode);
		builder.append(", pickTurn=");
		builder.append(pickTurn);
		builder.append(", teamParticipantId=");
		builder.append(teamParticipantId);
		builder.append(", clientInSynch=");
		builder.append(clientInSynch);
		builder.append("]");
		return builder.toString();
	}

}
