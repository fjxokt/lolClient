package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public abstract class Participant extends ClassType {
	
	protected String summonerName;
	protected String summonerInternalName;
	protected String botDifficulty;
	
	public static Participant createParticipant(TypedObject to) {
		if (to.type.equals(PlayerParticipant.getTypeClass())) {
			return new PlayerParticipant(to);
		}
		else if (to.type.equals(BotParticipant.getTypeClass())) {
			return new BotParticipant(to);
		}
		else if (to.type.equals(ObfuscatedParticipant.getTypeClass())) {
			return new ObfuscatedParticipant(to);
		}
		return null;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public String getSummonerInternalName() {
		return summonerInternalName;
	}

	public String getBotDifficulty() {
		return botDifficulty;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		return summonerInternalName.equals(other.getSummonerInternalName());
	}

}
