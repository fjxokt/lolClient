package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerIconInventoryDTO;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class SummonerIconService {
    
        public static SummonerIconInventoryDTO getSummonerIconInventory(LoLRTMPSClient client, Double summonerId) {
            
                TypedObject iconTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerIconService", "getSummonerIconInventory", new Object[] { summonerId });
                
                if (iconTo == null) {
                        return null;
                }

                return new SummonerIconInventoryDTO(iconTo);
	}

    private SummonerIconService() {
    }
}
