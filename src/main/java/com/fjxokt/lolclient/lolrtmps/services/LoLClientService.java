package com.fjxokt.lolclient.lolrtmps.services;

import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;
import java.io.IOException;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class LoLClientService {
        
    /**
     * Returns the result object, which is a TypedObject container around the relevant data.
     * @param client
     * @param serviceName
     * @param actionName
     * @param parameters
     * @return 
     */
    public static TypedObject getServiceResultContainer(LoLRTMPSClient client, String serviceName, String actionName, Object[] parameters){
        try {
            int resultID = client.invoke(serviceName, actionName, parameters);
            client.join(resultID);
            TypedObject result = client.getResult(resultID);
            
            if (result == null || result.get("result").equals("_error")) {
                throw new IOException("Result is invalid");
            }
            else{
                return result;
            }
            
        } catch (IOException ex) {
            System.out.println("Unable to make request " + actionName + " to service " + serviceName);
            return null;
        }
    }
    
    /**
     * Executes the given service and doesn't attempt to fetch results. Returns true if it successfully completes, false otherwise.
     * @param client
     * @param serviceName
     * @param actionName
     * @param parameters
     * @return 
     */
    public static boolean executeService(LoLRTMPSClient client, String serviceName, String actionName, Object[] parameters){
        try {
            int resultID = client.invoke(serviceName, actionName, parameters);
            client.cancel(resultID);
            return true;
            
        } catch (IOException ex) {
            System.out.println("Unable to make request " + actionName + " to service " + serviceName);
            return false;
        }
    }
    
    /**
     * Fetches the service response container and then attempts to get the data body from that response.
     * @param client
     * @param serviceName
     * @param actionName
     * @param parameters
     * @return 
     */
    public static Object getServiceResponseDataBody(LoLRTMPSClient client, String serviceName, String actionName, Object[] parameters){
        TypedObject responseContainer = getServiceResultContainer(client, serviceName, actionName, parameters);
        if(responseContainer == null){
            return null;
        }
        else{
            return responseContainer.getTO("data").get("body");
        }
    }
    
    public static Object[] getServiceResponseDataBodyArray(LoLRTMPSClient client, String serviceName, String actionName, Object[] parameters){
        TypedObject responseContainer = getServiceResultContainer(client, serviceName, actionName, parameters);
        if(responseContainer == null){
            return null;
        }
        else{
            return responseContainer.getTO("data").getArray("body");
        }
    }
    
    /**
     * Fetches the service response container and then attempts to get the data body from that response as a TypedObject.
     * @param client
     * @param serviceName
     * @param actionName
     * @param parameters
     * @return 
     */
    public static TypedObject getTypedServiceResponseDataBody(LoLRTMPSClient client, String serviceName, String actionName, Object[] parameters){
        TypedObject responseContainer = getServiceResultContainer(client, serviceName, actionName, parameters);
        if(responseContainer == null){
            return null;
        }
        else{
            return responseContainer.getTO("data").getTO("body");
        }
    }    

    private LoLClientService() {
    }
    
}
