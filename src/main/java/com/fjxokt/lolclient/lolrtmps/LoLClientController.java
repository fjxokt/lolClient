package com.fjxokt.lolclient.lolrtmps;

import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.StoreAccountBalanceNotification;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.gvaneyck.rtmp.Callback;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

public interface LoLClientController extends Callback {

	// init method
	void initUser(String region, String clientVersion, String user, String pass);
	String getUser();
	String getPassword();
        
        LoLRTMPSClient getRTMPSClient();
        
        void notifyGameUpdated(GameDTO game, ClientEventType state);
	
	void notifyClientUpdate(ClientEventType state, SearchingForMatchNotification search);
	
	void notifyClientUpdate(ClientEventType state);
	
	void notifyClientUpdate(ClientEventType state, StoreAccountBalanceNotification balance);
	
	void notifyClientUpdate(ClientEventType state, ChampionTradeMessage msg);
	
	void notifyGameUpdated(GameDTO game, ClientEventType state, EndOfGameStats stats, 
			SearchingForMatchNotification search, ChampionTradeMessage msg,
			StoreAccountBalanceNotification balance);
        
        void setState(GameState newState);
        
        void setGame(GameDTO game);
        
        GameDTO getGame();
        
        /**
         * blocks until queue returns an object of type typeClass
         * @return 
         */
        TypedObject pollQueue(String typeClass);
	
}