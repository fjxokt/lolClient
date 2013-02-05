package com.fjxokt.lolclient.audio;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

/*
 * some docs here:
 * http://docs.oracle.com/cd/E17802_01/j2se/javase/technologies/desktop/media/jmf/2.1.1/apidocs/javax/media/Player.html
 */

public class AudioManager {

	private static AudioManager instance;
	private Map<String, Player> map;
	private boolean state = true;
	
	private AudioManager() {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		map = new HashMap<String, Player>();
	}
	
	public void playSound(final String mp3) {
		if (!state) {
			return;
		}
		//Thread th = new Thread() {
			//public void run() {
				Player player;
				try {
					player = Manager.createPlayer(new MediaLocator(new File(mp3).toURI().toURL()));
					player.start();
					map.put(mp3, player);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
		//};
		//th.start();
	}
	
	public void stopSound(String mp3) {
		Player p = map.get(mp3);
		if (p != null) {
			p.stop();
		}
	}
	
	public void stopAllSounds() {
		for (String mp3 : map.keySet()) {
			Player p = map.get(mp3);
			p.stop();
		}
	}
	
	public void setEnabled(boolean state) {
		this.state = state;
	}
	
	public static AudioManager getInst() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}
	
}
