package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.Rune;
import com.fjxokt.lolclient.lolrtmps.model.RuneCombiner;
import com.fjxokt.lolclient.lolrtmps.model.RuneQuantity;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerActiveBoostsDTO;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: Rune has to be "serializable" (as they are sent to the server)
 * TODO: findChampionById : doesn't work
 * TODO: isStoreEnabled(no params) : doesn't work
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class InventoryService {
    
        public static SummonerActiveBoostsDTO getSumonerActiveBoosts(LoLRTMPSClient client) {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "inventoryService", "getSumonerActiveBoosts", new Object[] {});
                
                if (playerStatTo == null) {
                        return null;
                }

                return new SummonerActiveBoostsDTO(playerStatTo);
	}

	public static List<ChampionDTO> getAvailableChampions(LoLRTMPSClient client) {
            
                Object[] championsResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "getAvailableChampions", new Object[] {});
		
                if(championsResult == null){
                    return null;
                }
                
                List<Object> championsAsObjects = Arrays.asList(championsResult);
                List<ChampionDTO> championAsChamps = new ArrayList<ChampionDTO>(championsAsObjects.size());
                
                for(Object o : championsAsObjects){
                    TypedObject champ = (TypedObject)o;
                    if(champ != null){
                        championAsChamps.add(new ChampionDTO(champ));
                    }
                }
                
                return championAsChamps;
	}
	
	public static List<String> retrieveInventoryTypes(LoLRTMPSClient client) {
            
                Object[] inventoryResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "retrieveInventoryTypes", new Object[] {});
		
                if(inventoryResult == null){
                    return null;
                }
                
                List<Object> inventoryAsObjects = Arrays.asList(inventoryResult);
                List<String> inventoryAsString = new ArrayList<String>(inventoryAsObjects.size());
                
                for(Object type : inventoryAsObjects){
                        inventoryAsString.add((String)type);
                }
                
                return inventoryAsString;
	}
	
	public static List<RuneCombiner> getAllRuneCombiners(LoLRTMPSClient client) {
            
                Object[] championsResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "getAllRuneCombiners", new Object[] {});
		
                if(championsResult == null){
                    return null;
                }
                
                List<Object> runesAsObjects = Arrays.asList(championsResult);
                List<RuneCombiner> runesAsCombiners = new ArrayList<RuneCombiner>(runesAsObjects.size());
                
                for(Object o : runesAsObjects){
                    TypedObject combiner = (TypedObject)o;
                    if(combiner != null){
                        runesAsCombiners.add(new RuneCombiner(combiner));
                    }
                }
                
                return runesAsCombiners;
	}
	
	public static List<RuneQuantity> useRuneCombiner(LoLRTMPSClient client, Integer runeCombinerId, List<Rune> runes) {
            
                // change list to object[]
                Object[] robjs = new Object[runes.size()];
                for (int i=0; i< runes.size(); i++) {
                        Rune rune = runes.get(i);
                        robjs[i] = (rune == null) ? null : rune.getTypedObject();
                }
                
                Object[] runesResult = LoLClientService.getServiceResponseDataBodyArray(client, "inventoryService", "useRuneCombiner", new Object[] { runeCombinerId, robjs });
		
                if(runesResult == null){
                    return null;
                }
                
                List<Object> runesAsObjects = Arrays.asList(runesResult);
                List<RuneQuantity> runesAsQuantities = new ArrayList<RuneQuantity>(runesAsObjects.size());
                
                for(Object o : runesAsObjects){
                    TypedObject quantity = (TypedObject)o;
                    if(quantity != null){
                        runesAsQuantities.add(new RuneQuantity(quantity));
                    }
                }
                
                return runesAsQuantities;
	}

    private InventoryService() {
    }
}
