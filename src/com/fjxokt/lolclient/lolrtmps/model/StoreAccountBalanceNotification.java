package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class StoreAccountBalanceNotification extends ClassType {

	private Double ip;
	private Double rp;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.messaging.StoreAccountBalanceNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.messaging.StoreAccountBalanceNotification";
	}
	
	public StoreAccountBalanceNotification(TypedObject to) {
		super();
		this.ip = to.getDouble("ip");
		this.rp = to.getDouble("rp");
	}

	public Double getIp() {
		return ip;
	}

	public Double getRp() {
		return rp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StoreAccountBalanceNotification [ip=");
		builder.append(ip);
		builder.append(", rp=");
		builder.append(rp);
		builder.append("]");
		return builder.toString();
	}

}
