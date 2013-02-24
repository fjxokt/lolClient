package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.utils.BotRole;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotComposition extends ClassType {

	private Integer mapId;
	private Integer weight;
	private List<BotRole> roles;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.bot.BotComposition";
	}
	
	public BotComposition(TypedObject to) {
		super();
		this.mapId = to.getInt("mapId");
		this.weight = to.getInt("weight");
		this.roles = new ArrayList<BotRole>();
		Object[] objs = to.getArray("roles");
		if (objs != null) {
			for (Object o : objs) {
				String tr = (String)o;
				if (tr != null) {
					roles.add(BotRole.getRoleFromString(tr));
				}
			}
		}
	}

	public Integer getMapId() {
		return mapId;
	}

	public Integer getWeight() {
		return weight;
	}

	public List<BotRole> getRoles() {
		return Collections.unmodifiableList(roles);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BotComposition [mapId=");
		builder.append(mapId);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}

}
