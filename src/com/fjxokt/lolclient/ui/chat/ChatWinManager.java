package com.fjxokt.lolclient.ui.chat;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Presence;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.messaging.ChatListener;
import com.fjxokt.lolclient.messaging.MessagingManager;

public class ChatWinManager implements ChatListener {
	
	private static ChatWinManager instance;
	
	private ChatWin buddies;
	private Map<String, BuddyChatWin> map;
	
	private ChatWinManager() {
		map = new HashMap<String, BuddyChatWin>();
	}
	
	public static ChatWinManager getInst() {
		if (instance == null) {
			instance = new ChatWinManager();
		}
		return instance;
	}
	
	public ChatWin getMainWin() {
		if (buddies == null) {
			buddies = new ChatWin();
		}
		return buddies;
	}
	
	public BuddyChatWin getWindow(String buddyId) {
		BuddyChatWin win = map.get(buddyId);
		if (win == null) {
			win = new BuddyChatWin(MessagingManager.getInst().getBuddyFromId(buddyId));
			map.put(buddyId, win);
		}
		win.setVisible(true);
		return win;
	}

	@Override
	public void buddyMessageReceived(String userId, String message) {
		BuddyChatWin window = getWindow(userId);
		window.getAnswer(MessagingManager.getInst().getBuddyFromId(userId).getName(), message);
	}

	@Override
	public void gameMessageReceived(GameDTO game, String user, String message) {
		// nothing to do here
	}

	@Override
	public void buddyPresenceChanged(String userId, Presence presence, ChatPresenceType type) {
		getMainWin().refreshList();
	}
}
