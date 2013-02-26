package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.Arrays;

public class MatchMakerParams extends ClassType {

	private Integer[] queueIds;
	private String botDifficulty;
	private String invitationId;
	// TODO: these 3 variables !
	private Integer[] team;
	private Object teamId;
	private Object languages;
	
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.matchmaking.MatchMakerParams";
	}
	
	public TypedObject getTypedObject() {
		TypedObject res = new TypedObject(getTypeName());
		
		res.put("botDifficulty", botDifficulty);
		res.put("invitationId", invitationId);
		res.put("team", TypedObject.makeArrayCollection(team));
		res.put("teamId", teamId);
		res.put("languages", languages);
		res.put("queueIds", TypedObject.makeArrayCollection(queueIds));
		
		return res;
	}
	
	public MatchMakerParams(Integer[] queueIds, String botDifficulty,
			String invitationId, Integer[] team, Object teamId, Object languages) {
		super();
		this.queueIds = queueIds;
		this.botDifficulty = botDifficulty;
		this.invitationId = invitationId;
		this.team = team;
		this.teamId = teamId;
		this.languages = languages;
	}
	
	public MatchMakerParams(Integer[] queueIds) {
		this(queueIds, "", null, null, null, null);
	}
	
	public MatchMakerParams(Integer[] queueIds, String invitationId, Integer[] team) {
		this(queueIds, "", invitationId, team, null, null);
	}

	public Integer[] getQueueIds() {
		return queueIds;
	}

	public void setQueueIds(Integer[] queueIds) {
		this.queueIds = queueIds;
	}

	public String getBotDifficulty() {
		return botDifficulty;
	}

	public void setBotDifficulty(String botDifficulty) {
		this.botDifficulty = botDifficulty;
	}

	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public Object getTeam() {
		return team;
	}

	public void setTeam(Integer[] team) {
		this.team = team;
	}

	public Object getTeamId() {
		return teamId;
	}

	public void setTeamId(Object teamId) {
		this.teamId = teamId;
	}

	public Object getLanguages() {
		return languages;
	}

	public void setLanguages(Object languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MatchMakerParams [botDifficulty=");
		builder.append(botDifficulty);
		builder.append(", invitationId=");
		builder.append(invitationId);
		builder.append(", languages=");
		builder.append(languages);
		builder.append(", queueIds=");
		builder.append(Arrays.toString(queueIds));
		builder.append(", team=");
		builder.append(Arrays.toString(team));
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append("]");
		return builder.toString();
	}


}
