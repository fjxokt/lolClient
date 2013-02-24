package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChampionDTO extends ClassType {
	
	private Integer championId;
	private Boolean owned;
	private Boolean active;
	private Boolean rankedPlayEnabled;
	private Boolean freeToPlay;
	private Boolean botEnabled;
	private List<ChampionSkinDTO> championSkins;

	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.catalog.champion.ChampionDTO";
	}
	
	public ChampionDTO(TypedObject to) {
		super();
		this.botEnabled = to.getBool("botEnabled");
		this.active = to.getBool("active");
		this.freeToPlay = to.getBool("freeToPlay");
		this.championId = to.getInt("championId");
		this.owned = to.getBool("owned");
		this.rankedPlayEnabled = to.getBool("rankedPlayEnabled");
		
		this.championSkins = new ArrayList<ChampionSkinDTO>();
		Object[] skins = to.getArray("championSkins");
		if (skins != null) {
			for (Object so : skins) {
				TypedObject sto = (TypedObject)so;
				if (sto != null) {
					championSkins.add(new ChampionSkinDTO(sto));
				}
			}
		}
	}

	public ChampionDTO(boolean botEnabled, boolean active, boolean freeToPlay,
			Integer championId, boolean owned, boolean rankedPlayEnabled,
			List<ChampionSkinDTO> championSkins) {
		super();
		this.botEnabled = botEnabled;
		this.active = active;
		this.freeToPlay = freeToPlay;
		this.championId = championId;
		this.owned = owned;
		this.rankedPlayEnabled = rankedPlayEnabled;
		this.championSkins = championSkins;
	}

	public boolean isBotEnabled() {
		return botEnabled;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isFreeToPlay() {
		return freeToPlay;
	}

	public Integer getChampionId() {
		return championId;
	}

	public boolean isOwned() {
		return owned;
	}

	public boolean isRankedPlayEnabled() {
		return rankedPlayEnabled;
	}

	public List<ChampionSkinDTO> getChampionSkins() {
		return Collections.unmodifiableList(championSkins);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChampionDTO [championId=");
		builder.append(championId);
		builder.append(", owned=");
		builder.append(owned);
		builder.append(", active=");
		builder.append(active);
		builder.append(", rankedPlayEnabled=");
		builder.append(rankedPlayEnabled);
		builder.append(", freeToPlay=");
		builder.append(freeToPlay);
		builder.append(", botEnabled=");
		builder.append(botEnabled);
		builder.append(", championSkins=");
		builder.append(championSkins);
		builder.append("]");
		return builder.toString();
	}

}
