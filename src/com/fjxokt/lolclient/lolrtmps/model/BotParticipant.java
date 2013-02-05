package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;

public class BotParticipant extends Participant {
	
	private String teamId;
	private Integer botSkillLevel;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.BotParticipant";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.game.BotParticipant";
	}
	
	public BotParticipant(TypedObject oo) {
		this.botDifficulty = oo.getString("botDifficulty");
		this.summonerInternalName = oo.getString("summonerInternalName");
		this.summonerName = oo.getString("summonerName");
		this.teamId = oo.getString("teamId");
		this.botSkillLevel = oo.getInt("botSkillLevel");
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("botDifficulty", botDifficulty);
		res.put("summonerInternalName", summonerInternalName);
		res.put("summonerName", summonerName);
		res.put("teamId", teamId);
		res.put("botSkillLevel", botSkillLevel);
		
		return res;
	}

	public String getTeamId() {
		return teamId;
	}

	public Integer getBotSkillLevel() {
		return botSkillLevel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BotParticipant [botDifficulty=");
		builder.append(botDifficulty);
		builder.append(", summonerInternalName=");
		builder.append(summonerInternalName);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append(", botSkillLevel=");
		builder.append(botSkillLevel);
		builder.append("]");
		return builder.toString();
	}
	
}
