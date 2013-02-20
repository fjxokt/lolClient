package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class PlayerCredentialsDTO extends ClassType {
	
	private String handshakeToken;
	private Double gameId;
	private String serverIp;
	private Integer serverPort;
	private String encryptionKey;
	private Boolean observer;
	private String observerServerIp;
	private Integer observerServerPort;
	private String observerEncryptionKey;
	private Double summonerId;
	private String summonerName;
	private Double playerId;
	private Integer championId;
	private Integer lastSelectedSkinIndex;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.PlayerCredentialsDto";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.PlayerCredentialsDto";
	}
	
	public PlayerCredentialsDTO(TypedObject to) {
		super();
		this.handshakeToken = to.getString("handshakeToken");
		this.gameId = to.getDouble("gameId");
		this.serverIp = to.getString("serverIp");
		this.serverPort = to.getInt("serverPort");
		this.encryptionKey = to.getString("encryptionKey");
		this.observer = to.getBool("observer");
		this.observerServerIp = to.getString("observerServerIp");
		this.observerServerPort = to.getInt("observerServerPort");
		this.observerEncryptionKey = to.getString("observerEncryptionKey");
		this.summonerId = to.getDouble("summonerId");
		this.summonerName = to.getString("summonerName");
		this.playerId = to.getDouble("playerId");
		this.championId = to.getInt("championId");
		this.lastSelectedSkinIndex = to.getInt("lastSelectedSkinIndex");
	}

	public String getHandshakeToken() {
		return handshakeToken;
	}

	public Double getGameId() {
		return gameId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public Boolean getObserver() {
		return observer;
	}

	public String getObserverServerIp() {
		return observerServerIp;
	}

	public Integer getObserverServerPort() {
		return observerServerPort;
	}

	public String getObserverEncryptionKey() {
		return observerEncryptionKey;
	}

	public Double getSummonerId() {
		return summonerId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public Double getPlayerId() {
		return playerId;
	}

	public Integer getChampionId() {
		return championId;
	}

	public Integer getLastSelectedSkinIndex() {
		return lastSelectedSkinIndex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerCredentialsDTO [handshakeToken=");
		builder.append(handshakeToken);
		builder.append(", gameId=");
		builder.append(gameId);
		builder.append(", serverIp=");
		builder.append(serverIp);
		builder.append(", serverPort=");
		builder.append(serverPort);
		builder.append(", encryptionKey=");
		builder.append(encryptionKey);
		builder.append(", observer=");
		builder.append(observer);
		builder.append(", observerServerIp=");
		builder.append(observerServerIp);
		builder.append(", observerServerPort=");
		builder.append(observerServerPort);
		builder.append(", observerEncryptionKey=");
		builder.append(observerEncryptionKey);
		builder.append(", summonerId=");
		builder.append(summonerId);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append(", playerId=");
		builder.append(playerId);
		builder.append(", championId=");
		builder.append(championId);
		builder.append(", lastSelectedSkinIndex=");
		builder.append(lastSelectedSkinIndex);
		builder.append("]");
		return builder.toString();
	}

}
