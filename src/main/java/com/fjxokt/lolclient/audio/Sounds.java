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

	public static final String LOGIN = ResourceConstants.soundAssetsBasePackage + "login.mp3";
	public static final String PLAY = ResourceConstants.soundAssetsBasePackage + "playbutton.mp3";
	public static final String MATCHMAKING_QUEUE = ResourceConstants.soundAssetsBasePackage + "matchmakingqueued.mp3";
	public static final String LOCK = ResourceConstants.soundAssetsBasePackage + "lock2.mp3";
	public static final String START_GAME = ResourceConstants.soundAssetsBasePackage + "exitchampionselect.mp3";
	public static final String PHASE_CHANGED = ResourceConstants.soundAssetsBasePackage + "phasechangedrums2.mp3";
	public static final String BACKGROUND_SELECT_CHAMP = ResourceConstants.soundAssetsBasePackage + "ChmpSlct_Tutorial.mp3";
	public static final String JOIN_TEAM_SEL = ResourceConstants.soundAssetsBasePackage + "join_chat.mp3";
	public static final String MESSAGE_RECEIVED = ResourceConstants.soundAssetsBasePackage + "standard_msg_receive.mp3";

	public Sounds() {
		LoLClient.getInst().addGameListener(this);
		MessagingManager.getInst().addChatListener(this);
	}
	
	public void clientStateUpdated(ClientEvent e) {
		switch (e.getType()) {
		case LOGGED_IN:
	        AudioManager.getInst().playSound(Sounds.LOGIN);
			break;
		case JOINING_MATCHMAKING:
	        AudioManager.getInst().playSound(Sounds.MATCHMAKING_QUEUE);
			break;
		case JOINING_TEAM_SELECT:
		case TEAM_SELECT_UPDATE:
			AudioManager.getInst().playSound(Sounds.JOIN_TEAM_SEL);
			break;
		case JOINING_CHAMP_SELECT:
	        AudioManager.getInst().playSound(Sounds.BACKGROUND_SELECT_CHAMP);
			break;
		case RETURNING_LOBBY:
		case RETURNING_TEAM_SELECT:
		case LEAVING_CHAMP_SELECT:
	        AudioManager.getInst().stopSound(Sounds.BACKGROUND_SELECT_CHAMP);
			break;
		case JOINING_POST_CHAMPION_SELECT:
	        AudioManager.getInst().playSound(Sounds.PHASE_CHANGED);
			break;
		case LOCK_CHAMPION:
	        AudioManager.getInst().playSound(Sounds.LOCK);
			break;
		}
	}

	@Override
	public void buddyMessageReceived(String userId, String message) {}

	@Override
	public void buddyPresenceChanged(String userId, Presence presence,
			ChatPresenceType type) {}

	@Override
	public void gameMessageReceived(GameDTO game, String user, String message) {
		AudioManager.getInst().playSound(Sounds.MESSAGE_RECEIVED);
	}
	
}
