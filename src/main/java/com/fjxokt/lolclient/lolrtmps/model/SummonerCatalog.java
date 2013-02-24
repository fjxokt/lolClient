package com.fjxokt.lolclient.lolrtmps.model;

import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummonerCatalog extends ClassType {

	private List<TalentGroup> talentTree;
	private List<RuneSlot> spellBookConfig;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.SummonerCatalog";
	}
	
	public SummonerCatalog(TypedObject to) {
		super();
		
		this.talentTree = new ArrayList<TalentGroup>();
		Object[] objs = to.getArray("talentTree");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tto = (TypedObject)o;
				if (tto != null) {
					talentTree.add(new TalentGroup(tto));
				}
			}
		}
		
		this.spellBookConfig = new ArrayList<RuneSlot>();
		objs = to.getArray("spellBookConfig");
		if (objs != null) {
			for (Object o : objs) {
				TypedObject tto = (TypedObject)o;
				if (tto != null) {
					spellBookConfig.add(new RuneSlot(tto));
				}
			}
		}
	}

	public List<TalentGroup> getTalentTree() {
		return Collections.unmodifiableList(talentTree);
	}

	public List<RuneSlot> getSpellBookConfig() {
		return Collections.unmodifiableList(spellBookConfig);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SummonerCatalog [talentTree=");
		builder.append(talentTree);
		builder.append(", spellBookConfig=");
		builder.append(spellBookConfig);
		builder.append("]");
		return builder.toString();
	}

}
