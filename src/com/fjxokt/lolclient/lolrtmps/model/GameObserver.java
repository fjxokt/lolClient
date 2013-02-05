package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class GameObserver extends ClassType {
	
	private Integer accountId;
	private String summonerName;
	private String summonerInternalName;
	private Integer summonerId;
	private Integer profileIconId;
	private String botDifficulty;	// "NONE"
	private Integer pickMode;
	private Integer pickTurn;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.GameObserver";
	}
	
	public GameObserver(TypedObject to) {
		super();
		this.accountId = to.getInt("accountId");
		this.summonerName = to.getString("summonerName");
		this.summonerInternalName = to.getString("summonerInternalName");
		this.summonerId = to.getInt("summonerId");
		this.profileIconId = to.getInt("profileIconId");
		this.botDifficulty = to.getString("botDifficulty");
		this.pickMode = to.getInt("pickMode");
		this.pickTurn = to.getInt("pickTurn");
	}

	public GameObserver(Integer accountId, String summonerName,
			String summonerInternalName, Integer summonerId,
			Integer profileIconId, String botDifficulty, Integer pickMode,
			Integer pickTurn) {
		super();
		this.accountId = accountId;
		this.summonerName = summonerName;
		this.summonerInternalName = summonerInternalName;
		this.summonerId = summonerId;
		this.profileIconId = profileIconId;
		this.botDifficulty = botDifficulty;
		this.pickMode = pickMode;
		this.pickTurn = pickTurn;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public String getSummonerInternalName() {
		return summonerInternalName;
	}

	public Integer getSummonerId() {
		return summonerId;
	}

	public Integer getProfileIconId() {
		return profileIconId;
	}

	public String getBotDifficulty() {
		return botDifficulty;
	}

	public Integer getPickMode() {
		return pickMode;
	}

	public Integer getPickTurn() {
		return pickTurn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameObserver [accountId=");
		builder.append(accountId);
		builder.append(", botDifficulty=");
		builder.append(botDifficulty);
		builder.append(", pickMode=");
		builder.append(pickMode);
		builder.append(", pickTurn=");
		builder.append(pickTurn);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", summonerInternalName=");
		builder.append(summonerInternalName);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append("]");
		return builder.toString();
	}

}
