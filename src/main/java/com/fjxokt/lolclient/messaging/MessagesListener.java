package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;

public interface MessagesListener {
	
	void messageReceived(GameDTO game, String user, String message);

}
