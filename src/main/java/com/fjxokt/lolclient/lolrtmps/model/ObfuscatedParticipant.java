package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class ObfuscatedParticipant extends Participant {
	
	private Boolean clientInSynch;
	private Integer pickMode;
	private Integer gameUniqueId;
	private Integer badges;
	private Integer index;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.ObfuscatedParticipant";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.ObfuscatedParticipant";
	}
	
	public ObfuscatedParticipant(TypedObject to) {
		super();
		this.clientInSynch = to.getBool("clientInSynch");
		this.pickMode = to.getInt("pickMode");
		this.gameUniqueId = to.getInt("gameUniqueId");
		this.badges = to.getInt("badges");
		this.index = to.getInt("index");
	}

	public Boolean getClientInSynch() {
		return clientInSynch;
	}

	public Integer getPickMode() {
		return pickMode;
	}

	public Integer getGameUniqueId() {
		return gameUniqueId;
	}

	public Integer getBadges() {
		return badges;
	}

	public Integer getIndex() {
		return index;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObfuscatedParticipant [badges=");
		builder.append(badges);
		builder.append(", clientInSynch=");
		builder.append(clientInSynch);
		builder.append(", gameUniqueId=");
		builder.append(gameUniqueId);
		builder.append(", index=");
		builder.append(index);
		builder.append(", pickMode=");
		builder.append(pickMode);
		builder.append("]");
		return builder.toString();
	}

}
