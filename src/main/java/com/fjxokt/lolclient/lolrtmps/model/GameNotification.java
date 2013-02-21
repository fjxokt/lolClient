package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class GameNotification extends ClassType {

	private String messageCode;
	private String type;
	private String messageArgument;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.message.GameNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.message.GameNotification";
	}

	public GameNotification(TypedObject to) {
		this(to.getString("messageCode"), to.getString("type"), to.getString("messageArgument"));
	}
	
	public GameNotification(String messageCode, String type, String messageArgument) {
		super();
		this.messageCode = messageCode;
		this.type = type;
		this.messageArgument = messageArgument;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public String getType() {
		return type;
	}
	
	public String getMessageArgument() {
		return messageArgument;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameNotification [messageArgument=");
		builder.append(messageArgument);
		builder.append(", messageCode=");
		builder.append(messageCode);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
