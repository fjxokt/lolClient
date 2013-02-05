package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.gvaneyck.rtmp.TypedObject;

// same class as SpellBookDTO
public class SpellBook extends SpellBookDTO {
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.summoner.spellbook.SpellBook";
	}
	
	public SpellBook(TypedObject to) {
		super(to);
	}

}
