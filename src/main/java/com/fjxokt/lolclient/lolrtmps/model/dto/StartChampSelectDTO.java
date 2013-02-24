package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.fjxokt.lolclient.lolrtmps.model.FailedJoinPlayer;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartChampSelectDTO extends ClassType {

	private List<FailedJoinPlayer> invalidPlayers;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.StartChampSelectDTO";
	}
	
	public StartChampSelectDTO(TypedObject to) {
		super();
		this.invalidPlayers = new ArrayList<FailedJoinPlayer>();
		Object[] objs = to.getArray("invalidPlayers");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ti = (TypedObject)o;
				if (ti != null) {
					invalidPlayers.add(new FailedJoinPlayer(ti));
				}
			}
		}
	}

	public List<FailedJoinPlayer> getInvalidPlayers() {
		return Collections.unmodifiableList(invalidPlayers);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StartChampSelectDTO [invalidPlayers=");
		builder.append(invalidPlayers);
		builder.append("]");
		return builder.toString();
	}

}
