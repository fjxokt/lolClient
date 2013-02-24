package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.ui.chat.ChatPresenceType;
import org.jivesoftware.smack.packet.Presence;

public interface ChatListener {
	
	void gameMessageReceived(GameDTO game, String user, String message);
	void buddyMessageReceived(String userId, String message);
	void buddyPresenceChanged(String userId, Presence presence, ChatPresenceType type);

}
