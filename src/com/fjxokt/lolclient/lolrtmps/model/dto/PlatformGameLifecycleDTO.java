package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class PlatformGameLifecycleDTO extends ClassType {

	private Integer reconnectDelay;
	private GameDTO game;
	private PlayerCredentialsDTO playerCredentials;
	private String gameName;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.PlatformGameLifecycleDTO";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.PlatformGameLifecycleDTO"; 
	}
	
	public PlatformGameLifecycleDTO(TypedObject to) {
		super();
		this.reconnectDelay = to.getInt("reconnectDelay");
		this.gameName = to.getString("gameName");
		TypedObject tobj = to.getTO("game");
		this.game = (tobj == null) ? null : new GameDTO(tobj);
		tobj = to.getTO("playerCredentials");
		this.playerCredentials = (tobj == null) ? null : new PlayerCredentialsDTO(tobj);
	}

	public Integer getReconnectDelay() {
		return reconnectDelay;
	}

	public GameDTO getGame() {
		return game;
	}

	public PlayerCredentialsDTO getPlayerCredentials() {
		return playerCredentials;
	}

	public String getGameName() {
		return gameName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlatformGameLifecycleDTO [game=");
		builder.append(game);
		builder.append(", gameName=");
		builder.append(gameName);
		builder.append(", playerCredentials=");
		builder.append(playerCredentials);
		builder.append(", reconnectDelay=");
		builder.append(reconnectDelay);
		builder.append("]");
		return builder.toString();
	}

}
