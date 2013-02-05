package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class QueueDodger extends PlayerJoinFailure {

	private Double dodgePenaltyRemainingTime;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.matchmaking.QueueDodger";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.matchmaking.QueueDodger";
	}
	
	public QueueDodger(TypedObject to) {
		super();
		this.reasonFailed = to.getString("reasonFailed");
		this.dodgePenaltyRemainingTime = to.getDouble("dodgePenaltyRemainingTime");
		TypedObject tobj = to.getTO("summoner");
		this.summoner = (tobj == null) ? null : new Summoner(tobj);
	}

	public Double getDodgePenaltyRemainingTime() {
		return dodgePenaltyRemainingTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueueDodger [dodgePenaltyRemainingTime=");
		builder.append(dodgePenaltyRemainingTime);
		builder.append(", reasonFailed=");
		builder.append(reasonFailed);
		builder.append(", summoner=");
		builder.append(summoner);
		builder.append("]");
		return builder.toString();
	}

}
