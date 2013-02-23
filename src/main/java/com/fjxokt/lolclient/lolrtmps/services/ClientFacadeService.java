package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.LoLClientController;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.model.LoginDataPacket;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.gvaneyck.rtmp.JSON;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.Arrays;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class ClientFacadeService {
    
        public static LoginDataPacket getLoginDataPacketForUser(LoLClientController controller) {
            
                TypedObject loginTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "clientFacadeService", "getLoginDataPacketForUser", new Object[] {});
		
                if(loginTo == null){
                    return null;
                }
                
                LoginDataPacket login = new LoginDataPacket(loginTo);
			
                // check if a game in running
                PlatformGameLifecycleDTO gameLifeCycle = login.getReconnectInfo();
                if (gameLifeCycle != null) {
                        System.out.println("You have a game in progress : " + gameLifeCycle);
                        controller.setGame(gameLifeCycle.getGame());
                        controller.setState(GameState.GAME_IN_PROGRESS);
                        controller.notifyGameUpdated(gameLifeCycle.getGame(), ClientEventType.GAME_IN_PROGRESS);
                }

                return login;
	}
	
	public static Integer[] callKudos(LoLRTMPSClient client, String commandName, Double summonerId) {
            
                TypedObject input = new TypedObject();
                input.put("commandName", "TOTALS");
                input.put("summonerId", summonerId);
                
                TypedObject kudosTo = LoLClientService.getTypedServiceResponseDataBody(client, "clientFacadeService", "callKudos", new Object[] {input.toString()});
                
                if(kudosTo == null){
                    return null;
                }
                
                String value = kudosTo.getTO("data").getTO("body").getString("value");
                
                TypedObject kudosResult = (TypedObject)JSON.parse(value);
                
                if(kudosResult == null){
                    return null;
                }
                
                Object[] kudosResults = kudosResult.getArray("totals");
                
                if(kudosResults == null){
                    return null;
                }
                
                return Arrays.asList(kudosResults).toArray(new Integer[kudosResults.length]);
	}
}
