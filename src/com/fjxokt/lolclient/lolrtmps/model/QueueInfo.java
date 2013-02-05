package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class QueueInfo extends ClassType {

	private Double queueId;
	private Integer queueLength;
	private Double waitTime;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.matchmaking.QueueInfo";
	}
	
	public QueueInfo(TypedObject to) {
		super();
		this.queueId = to.getDouble("queueId");
		this.queueLength = to.getInt("queueLength");
		this.waitTime = to.getDouble("waitTime");
	}

	public Double getQueueId() {
		return queueId;
	}

	public Integer getQueueLength() {
		return queueLength;
	}

	public Double getWaitTime() {
		return waitTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueueInfo [queueId=");
		builder.append(queueId);
		builder.append(", queueLength=");
		builder.append(queueLength);
		builder.append(", waitTime=");
		builder.append(waitTime);
		builder.append("]");
		return builder.toString();
	}

}
