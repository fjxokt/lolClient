package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.LoLClientController;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import java.io.IOException;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class LoginService {
    
        public static ResultMessage login(LoLClientController controller) {
		try {
			// login
			controller.getRTMPSClient().connectAndLogin();
		} catch (IOException e) {
			return ResultMessage.LOGIN_FAILED;
		}
		controller.setState(GameState.IDLE);
		controller.notifyClientUpdate(ClientEventType.LOGGED_IN);
		return ResultMessage.OK;
	}
	
	public static void logout(LoLClientController controller) {
		if (controller.getRTMPSClient() != null) {
			controller.getRTMPSClient().close();			
		}
		controller.setState(GameState.DISCONNECTED);
		controller.notifyClientUpdate(ClientEventType.LOGGED_OUT);
		System.out.println("logout !");
	}
	
	public static boolean isLoggedIn(LoLRTMPSClient client) {
		return client.isLoggedIn();
	}

	public static String getRegion(LoLRTMPSClient client) {
		return client.getRegion();
	}
	
	public static Integer getAccountId(LoLRTMPSClient client) {
		return client.getAccountID();
	}
	
	public static String getStoreUrl(LoLRTMPSClient client) {
            
                Object urlObject = LoLClientService.getServiceResponseDataBody(client, "loginService", "getStoreUrl", new Object[]{});
                
                if(urlObject == null){
                    return "";
                }
                
                return (String) urlObject;
	}

    private LoginService() {
    }
}
