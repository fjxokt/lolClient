package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RosterDTO extends ClassType {

	private Double ownerId;
	private List<TeamMemberInfoDTO> memberList;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.team.dto.RosterDTO";
	}
	
	public RosterDTO(TypedObject to) {
		super();
		this.ownerId = to.getDouble("ownerId");
		this.memberList = new ArrayList<TeamMemberInfoDTO>();
		Object[] objs = to.getArray("memberList");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tm = (TypedObject)o;
				if (tm != null) {
					memberList.add(new TeamMemberInfoDTO(tm));
				}
			}
		}
	}

	public Double getOwnerId() {
		return ownerId;
	}

	public List<TeamMemberInfoDTO> getMemberList() {
		return Collections.unmodifiableList(memberList);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RosterDTO [ownerId=");
		builder.append(ownerId);
		builder.append(", memberList=");
		builder.append(memberList);
		builder.append("]");
		return builder.toString();
	}

}
