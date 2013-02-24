package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: getGameMapSet(no params) , doesn't seems to exist
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class GameMapService {
    
        public static List<GameMap> getGameMapList(LoLRTMPSClient client) {
            
                Object[] mapsList = LoLClientService.getServiceResponseDataBodyArray(client, "gameMapService", "getGameMapList", new Object[]{});
		
                if(mapsList == null){
                    return null;
                }
                
                List<Object> mapsAsObjects = Arrays.asList(mapsList);
                List<GameMap> mapsAsGameMaps = new ArrayList<GameMap>(mapsAsObjects.size());
                
                for(Object o : mapsAsObjects){
                    TypedObject quantity = (TypedObject)o;
                    if(quantity != null){
                        mapsAsGameMaps.add(new GameMap(quantity));
                    }
                }
                
                return mapsAsGameMaps;
	}

    private GameMapService() {
    }
}
