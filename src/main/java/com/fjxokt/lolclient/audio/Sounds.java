package com.fjxokt.lolclient.audio;

import org.jivesoftware.smack.packet.Presence;

import com.fjxokt.lolclient.ResourceConstants;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.messaging.ChatListener;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.fjxokt.lolclient.ui.chat.ChatPresenceType;

// TODO: also implements MessageListener for the chat sounds
public class Sounds implements ClientListener, ChatListener {

	private static final JSound LOGIN = new JSound(ResourceConstants.soundAssetsBasePackage + "login.mp3");
	private static final JSound PLAY = new JSound(ResourceConstants.soundAssetsBasePackage + "playbutton.mp3");
	private static final JSound MATCHMAKING_QUEUE = new JSound(ResourceConstants.soundAssetsBasePackage + "matchmakingqueued.mp3");
	private static final JSound LOCK = new JSound(ResourceConstants.soundAssetsBasePackage + "lock2.mp3");
	private static final JSound START_GAME = new JSound(ResourceConstants.soundAssetsBasePackage + "exitchampionselect.mp3");
	private static final JSound PHASE_CHANGED = new JSound(ResourceConstants.soundAssetsBasePackage + "phasechangedrums2.mp3");
	private static final JSound BACKGROUND_SELECT_CHAMP = new JSound(ResourceConstants.soundAssetsBasePackage + "ChmpSlct_Tutorial.mp3");
	private static final JSound JOIN_TEAM_SEL = new JSound(ResourceConstants.soundAssetsBasePackage + "join_chat.mp3");
	private static final JSound MESSAGE_RECEIVED = new JSound(ResourceConstants.soundAssetsBasePackage + "standard_msg_receive.mp3");

	public Sounds() {
		LoLClient.getInst().addGameListener(this);
		MessagingManager.getInst().addChatListener(this);
	}
	
	public void clientStateUpdated(ClientEvent e) {
		switch (e.getType()) {
		case LOGGED_IN:
                        LOGIN.play();
			break;
		case JOINING_MATCHMAKING:
                        MATCHMAKING_QUEUE.play();
			break;
		case JOINING_TEAM_SELECT:
		case TEAM_SELECT_UPDATE:
			JOIN_TEAM_SEL.play();
			break;
		case JOINING_CHAMP_SELECT:
		case RETURNING_LOBBY:
		case RETURNING_TEAM_SELECT:
		case LEAVING_CHAMP_SELECT:
                        BACKGROUND_SELECT_CHAMP.play();
			break;
		case JOINING_POST_CHAMPION_SELECT:
                        PHASE_CHANGED.play();
			break;
		case LOCK_CHAMPION:
                        LOCK.play();
			break;
		}
	}

	public void buddyMessageReceived(String userId, String message) {}

	public void buddyPresenceChanged(String userId, Presence presence,
			ChatPresenceType type) {}

	public void gameMessageReceived(GameDTO game, String user, String message) {
                     MESSAGE_RECEIVED.play();
	}
	
}
