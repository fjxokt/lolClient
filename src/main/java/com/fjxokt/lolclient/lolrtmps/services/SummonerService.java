package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.AllSummonerData;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.model.dto.AllPublicSummonerDataDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * // TODO: getAllSummonerDataByAccount
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class SummonerService {
    
    public static AllSummonerData createDefaultSummoner(LoLRTMPSClient client, String summonerName) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "createDefaultSummoner", new Object[] { summonerName });
                
                if (summonerTo == null) {
                        return null;
                }

                return new AllSummonerData(summonerTo);
	}
	
	public static ResultMessage saveSeenTutorialFlag(LoLRTMPSClient client) {
            TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "createDefaultSummoner", new Object[] {});
                
            if (summonerTo == null) {
                    return ResultMessage.ERROR;
            }

            return ResultMessage.OK;
	}

	public static ResultMessage updateProfileIconId(LoLRTMPSClient client, Integer iconId) {
            TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "updateProfileIconId", new Object[] {iconId});
                
            if (summonerTo == null) {
                    return ResultMessage.ERROR;
            }

            return ResultMessage.OK;
	}

	public static PublicSummoner getSummonerByName(LoLRTMPSClient client, String name) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "getSummonerByName", new Object[] { name });
                
                if (summonerTo == null) {
                        return null;
                }

                return new PublicSummoner(summonerTo);
	}
	
	public static String getSummonerInternalNameByName(LoLRTMPSClient client, String summonerName) {
                Object summonerTo = LoLClientService.getServiceResponseDataBody(client, "summonerService", "getSummonerInternalNameByName", new Object[] { summonerName });
                
                if (summonerTo == null) {
                        return null;
                }

                return (String) summonerTo;
	}
	
	public static List<String> getSummonerNames(LoLRTMPSClient client, Integer[] summonerIds) {
            
                Object[] summonerNames = LoLClientService.getServiceResponseDataBodyArray(client, "summonerService", "getSummonerNames", new Object[] { summonerIds });
		
                if(summonerNames == null){
                    return null;
                }
                
                List<Object> summonerNamesAsObjects = Arrays.asList(summonerNames);
                List<String> summonerNamesAsStrings = new ArrayList<String>(summonerNamesAsObjects.size());
                
                for(Object o : summonerNamesAsObjects){
                    summonerNamesAsStrings.add((String)o);
                }
                
                return summonerNamesAsStrings;
	}
	
	public static AllPublicSummonerDataDTO getAllPublicSummonerDataByAccount(LoLRTMPSClient client, Integer accountId) {
                TypedObject summonerTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerService", "getAllPublicSummonerDataByAccount", new Object[] { accountId });
                
                if (summonerTo == null) {
                        return null;
                }

                return new AllPublicSummonerDataDTO(summonerTo);
	}
}
