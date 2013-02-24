package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.fjxokt.lolclient.lolrtmps.model.TeamStatSummary;
import com.gvaneyck.rtmp.TypedObject;
import java.util.Date;

public class TeamDTO extends ClassType {
	
	private Date createDate;
	private Date modifyDate;
	private Date lastJoinDate;
	private String status;
	private String name;
	private String tag;
	private RosterDTO roster;
	private TeamId teamId;
	private TeamStatSummary teamStatSummary;

	@Override
	protected String getTypeName() {
		return "com.riotgames.team.dto.TeamDTO";
	}
	
	public TeamDTO(TypedObject to) {
		super();
		this.createDate = to.getDate("createDate");
		this.modifyDate = to.getDate("modifyDate");
		this.lastJoinDate = to.getDate("lastJoinDate");
		this.status = to.getString("status");
		this.name = to.getString("name");
		this.tag = to.getString("tag");
		TypedObject obj = to.getTO("roster");
		this.roster = (obj == null) ? null : new RosterDTO(obj);
		obj = to.getTO("teamId");
		this.teamId = (obj == null) ? null : new TeamId(obj);
		obj = to.getTO("teamStatSummary");
		this.teamStatSummary = (obj == null) ? null : new TeamStatSummary(obj);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public Date getLastJoinDate() {
		return lastJoinDate;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public RosterDTO getRoster() {
		return roster;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public TeamStatSummary getTeamStatSummary() {
		return teamStatSummary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamDTO [createDate=");
		builder.append(createDate);
		builder.append(", modifyDate=");
		builder.append(modifyDate);
		builder.append(", lastJoinDate=");
		builder.append(lastJoinDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", name=");
		builder.append(name);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", roster=");
		builder.append(roster);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append(", teamStatSummary=");
		builder.append(teamStatSummary);
		builder.append("]");
		return builder.toString();
	}

}
