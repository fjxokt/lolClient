package com.fjxokt.lolclient.lolrtmps.model.utils;

import com.gvaneyck.rtmp.TypedObject;

public class ResultMessage {
	
	private String message;
	private Type type;
	
	public static ResultMessage OK = new ResultMessage(Type.OK, "OK");
	public static ResultMessage ERROR = new ResultMessage(Type.ERROR, "ERROR");
	public static ResultMessage LOGIN_FAILED = new ResultMessage(Type.LOGIN_FAILED, "Incorrect username or password");
	public static ResultMessage INCORRECT_PWD = new ResultMessage(Type.INCORRECT_PWD, "Incorrect password");

	public enum Type {
		OK, ERROR, LOGIN_FAILED, INCORRECT_PWD;
	}
	
	public ResultMessage() {
		type = Type.OK;
	}
	
	public ResultMessage(Type type) {
		this.type = type;
	}
	
	public ResultMessage(Type type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public static ResultMessage getResultMessageFromError(TypedObject result) {
		String classErr = result.getTO("data").getTO("rootCause").getString("rootCauseClassname");
		
		// TODO: what to do with all that
		if (classErr.equals("com.riotgames.platform.game.InvalidGameStateException")) {
			
		}
		else if (classErr.equals("com.riotgames.platform.game.GameNotFoundException")) {
			
		}
		else if (classErr.equals("com.riotgames.platform.game.GameFullException")) {
			
		}
		else if (classErr.equals("com.riotgames.platform.game.IncorrectPasswordException")) {
			
		}
		
		ResultMessage res = new ResultMessage(Type.ERROR, classErr);
		return res;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean equals(ResultMessage other) {
		return type.equals(other.getType());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultMessage [message=");
		builder.append(message);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
