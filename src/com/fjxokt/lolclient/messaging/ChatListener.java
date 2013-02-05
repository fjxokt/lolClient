package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;

public interface ChatListener {
	
	void gameMessageReceived(GameDTO game, String user, String message);
	void buddyMessageReceived(String userId, String message);
	// TODO: userPresenceChanged

}
