package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class SpellBookService {
    
        public static SpellBookDTO getSpellBook(LoLRTMPSClient client, Double summonerId) {
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService", "getSpellBook", new Object[] { summonerId });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookDTO(spellTo);
	}
	
	public static SpellBookPageDTO selectDefaultSpellBookPage(LoLRTMPSClient client, SpellBookPageDTO page) {
                
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService", "selectDefaultSpellBookPage", new Object[] { page.getTypedObject() });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookPageDTO(spellTo);
	}
	
	public static SpellBookDTO saveSpellBook(LoLRTMPSClient client, SpellBookDTO book) {
            
                TypedObject spellTo = LoLClientService.getTypedServiceResponseDataBody(client, "spellBookService","saveSpellBook", new Object[] { book.getTypedObject() });
                
                if (spellTo == null) {
                        return null;
                }
                
                return new SpellBookDTO(spellTo);
	}

    private SpellBookService() {
    }
}
