package com.fjxokt.lolclient.audio;

import com.fjxokt.lolclient.Constants;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

// TODO: also implements MessageListener for the chat sounds
public class Sounds implements ClientListener {

	public static final String LOGIN = Constants.soundsPath + "login.mp3";
	public static final String PLAY = Constants.soundsPath + "playbutton.mp3";
	public static final String MATCHMAKING_QUEUE = Constants.soundsPath + "matchmakingqueued.mp3";
	public static final String LOCK = Constants.soundsPath + "lock2.mp3";
	public static final String START_GAME = Constants.soundsPath + "exitchampionselect.mp3";
	public static final String PHASE_CHANGED = Constants.soundsPath + "phasechangedrums2.mp3";
	public static final String BACKGROUND_SELECT_CHAMP = Constants.soundsPath + "ChmpSlct_Tutorial.mp3";
	public static final String JOIN_TEAM_SEL = Constants.soundsPath + "join_chat.mp3";
	public static final String MESSAGE_RECEIVED = Constants.soundsPath + "standard_msg_receive.mp3";
	public static final String PM_RECEIVED = Constants.soundsPath + "pm_receive.mp3";

	public Sounds() {
		LoLClient.getInst().addGameListener(this);
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
	
}
