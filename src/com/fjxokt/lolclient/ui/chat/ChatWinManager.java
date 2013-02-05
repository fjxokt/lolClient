package com.fjxokt.lolclient.ui.chat;

import java.util.HashMap;
import java.util.Map;

import com.fjxokt.lolclient.messaging.MessagingManager;

public class ChatWinManager {
	
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

}
