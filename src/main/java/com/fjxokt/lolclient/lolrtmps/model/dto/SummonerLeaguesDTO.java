package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummonerLeaguesDTO extends ClassType {

	private List<LeagueListDTO> summonerLeagues;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.leagues.client.dto.SummonerLeaguesDTO";
	}
	
	public SummonerLeaguesDTO(TypedObject to) {
		super();
		this.summonerLeagues = new ArrayList<LeagueListDTO>();
		Object[] objs = to.getArray("summonerLeagues");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					summonerLeagues.add(new LeagueListDTO(ts));
				}
			}
		}
	}

	public List<LeagueListDTO> getSummonerLeagues() {
		return Collections.unmodifiableList(summonerLeagues);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerLeaguesDTO [summonerLeagues=");
		builder.append(summonerLeagues);
		builder.append("]");
		return builder.toString();
	}

}
