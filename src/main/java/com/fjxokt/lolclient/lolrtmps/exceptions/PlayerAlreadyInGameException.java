package com.fjxokt.lolclient.lolrtmps.exceptions;

public class PlayerAlreadyInGameException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PlayerAlreadyInGameException(String message) {
		super(message);
	}

}
