package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.LoLClientController;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.model.GameQueueConfig;
import com.fjxokt.lolclient.lolrtmps.model.MatchMakerParams;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class MatchmakerService {
    
        public static List<GameQueueConfig> getAvailableQueues(LoLRTMPSClient client) {
            
                Object[] queuesList = LoLClientService.getServiceResponseDataBodyArray(client, "matchmakerService", "getAvailableQueues", new Object[]{});
		
                if(queuesList == null){
                    return null;
                }
                
                List<Object> queuesAsObjects = Arrays.asList(queuesList);
                List<GameQueueConfig> queuesAsConfigs = new ArrayList<GameQueueConfig>(queuesAsObjects.size());
                
                for(Object o : queuesAsObjects){
                    TypedObject config = (TypedObject)o;
                    if(config != null){
                        queuesAsConfigs.add(new GameQueueConfig(config));
                    }
                }
                
                return queuesAsConfigs;
	}
	
	public static SearchingForMatchNotification attachToQueue(LoLClientController controller, MatchMakerParams params) {
            
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "matchmakerService", "attachToQueue", new Object[] { params.getTypedObject() });
                
                if (matchTo == null) {
                        return null;
                }
                
                SearchingForMatchNotification res = new SearchingForMatchNotification(matchTo);
		controller.notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
                
                return res;
	}
	
	public static SearchingForMatchNotification attachTeamToQueue(LoLClientController controller, MatchMakerParams params) {
                
                // TODO: look TextEdit with a error for DODGE QUEUE
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(controller.getRTMPSClient(), "matchmakerService", "attachToQueue", new Object[] { params.getTypedObject() });
                
                if (matchTo == null) {
                        return null;
                }
                
                SearchingForMatchNotification res = new SearchingForMatchNotification(matchTo);
		controller.notifyClientUpdate(ClientEventType.SEARCHING_FOR_MATCH, res);
                
                return res;
	}
	
	public static ResultMessage cancelFromQueueIfPossible(LoLRTMPSClient client, Double queueId) {
            
                Object cancelResult = LoLClientService.getServiceResponseDataBody(client, "matchmakerService", "cancelFromQueueIfPossible", new Object[] { queueId });
                
                if (cancelResult == null) {
                        return ResultMessage.ERROR;
                }
                
                Boolean cancelHappened = (Boolean) cancelResult;
                
                if(cancelHappened){
                    return ResultMessage.OK;
                }
                else{
                    return ResultMessage.ERROR;
                }
	}
	
	public static ResultMessage acceptInviteForMatchmakingGame(LoLRTMPSClient client, String invitationId) {
            
                TypedObject matchTo = LoLClientService.getTypedServiceResponseDataBody(client, "matchmakerService", "acceptInviteForMatchmakingGame", new Object[] { invitationId });
                
                if (matchTo == null) {
                        return ResultMessage.ERROR;
                }
                
                return ResultMessage.OK;
	}
}
