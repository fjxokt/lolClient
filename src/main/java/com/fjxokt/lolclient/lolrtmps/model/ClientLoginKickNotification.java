package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class ClientLoginKickNotification extends ClassType {

	private String sessionToken;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.messaging.ClientLoginKickNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.messaging.ClientLoginKickNotification";
	}	
	public ClientLoginKickNotification(TypedObject to) {
		this.sessionToken = to.getString("sessionToken");
	}
	
	public String getSessionToken() {
		return sessionToken;
	}

	@Override
	public String toString() {
		return "ClientLoginKickNotification [sessionToken=" + sessionToken
				+ "]";
	}

}
