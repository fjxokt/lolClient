package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueListDTO extends ClassType {

	private String queue; // "RANKED_SOLO_5x5"
	private String name;
	private String tier; // "BRONZE"
	private String requestorsRank; // "III"
	private String requestorsName; // "fjxokt"
	private List<LeagueItemDTO> entries;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.leagues.pojo.LeagueListDTO";
	}
	
	public LeagueListDTO(TypedObject to) {
		super();
		this.queue = to.getString("queue");
		this.name = to.getString("name");
		this.tier = to.getString("tier");
		this.requestorsRank = to.getString("requestorsRank");
		this.requestorsName = to.getString("requestorsName");
		this.entries = new ArrayList<LeagueItemDTO>();
		Object[] objs = to.getArray("entries");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject te = (TypedObject)o;
				if (te != null) {
					entries.add(new LeagueItemDTO(te));
				}
			}
		}
	}

	public String getQueue() {
		return queue;
	}

	public String getName() {
		return name;
	}

	public String getTier() {
		return tier;
	}

	public String getRequestorsRank() {
		return requestorsRank;
	}

	public String getRequestorsName() {
		return requestorsName;
	}

	public List<LeagueItemDTO> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeagueListDTO [entries=");
		builder.append(entries);
		builder.append(", name=");
		builder.append(name);
		builder.append(", queue=");
		builder.append(queue);
		builder.append(", requestorsName=");
		builder.append(requestorsName);
		builder.append(", requestorsRank=");
		builder.append(requestorsRank);
		builder.append(", tier=");
		builder.append(tier);
		builder.append("]");
		return builder.toString();
	}

}
