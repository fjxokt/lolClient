package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.SummonerRuneInventory;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class SummonerRuneService {
    
        public static SummonerRuneInventory getSummonerRuneInventory(LoLRTMPSClient client, Double summonerId) {
            
                TypedObject runeTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerRuneService", "getSummonerRuneInventory", new Object[] { summonerId });
                
                if (runeTo == null) {
                        return null;
                }
                
                return new SummonerRuneInventory(runeTo);
	}

    private SummonerRuneService() {
    }
}
