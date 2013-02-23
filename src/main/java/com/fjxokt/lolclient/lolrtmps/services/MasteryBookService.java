package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class MasteryBookService {
    
        public static MasteryBookDTO getMasteryBook(LoLRTMPSClient client, Double summonerId) {
                TypedObject masteryTo = LoLClientService.getTypedServiceResponseDataBody(client, "masteryBookService", "getMasteryBook", new Object[] { summonerId });
                
                if (masteryTo == null) {
                        return null;
                }

                return new MasteryBookDTO(masteryTo);
	}
	
	public static MasteryBookDTO saveMasteryBook(LoLRTMPSClient client, MasteryBookDTO book) {
                TypedObject masteryTo = LoLClientService.getTypedServiceResponseDataBody(client, "masteryBookService", "saveMasteryBook", new Object[] { book.getTypedObject() });
                
                if (masteryTo == null) {
                        return null;
                }
                
                return new MasteryBookDTO(masteryTo);
	}
}
