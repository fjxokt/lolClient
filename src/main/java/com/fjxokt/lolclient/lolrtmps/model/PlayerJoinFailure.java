package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public abstract class PlayerJoinFailure extends ClassType {
	
	protected String reasonFailed;
	protected Summoner summoner;
	
	public static PlayerJoinFailure createPlayerJoinFailure(TypedObject to) {
		if (to.type.equals(QueueDodger.getTypeClass())) {
			return new QueueDodger(to);
		}
		else if (to.type.equals(FailedJoinPlayer.getTypeClass())) {
			return new FailedJoinPlayer(to);
		}
		return null;
	}
	
	public String getReasonFailed() {
		return reasonFailed;
	}
	
	public Summoner getSummoner() {
		return summoner;
	}

}
