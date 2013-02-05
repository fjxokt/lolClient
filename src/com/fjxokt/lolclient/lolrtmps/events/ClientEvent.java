package com.fjxokt.lolclient.lolrtmps.events;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.ChampionTradeMessage;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.StoreAccountBalanceNotification;

public class ClientEvent {
	
	private GameDTO updatedGame;
	private ClientEventType clientEvent;
	private EndOfGameStats stats;
	private SearchingForMatchNotification search;
	private ChampionTradeMessage tradeMsg;
	private StoreAccountBalanceNotification balance;
	
	public ClientEvent(GameDTO updatedGame, ClientEventType state, EndOfGameStats stats,
			SearchingForMatchNotification search, ChampionTradeMessage tradeMsg,
			StoreAccountBalanceNotification balance) {
		this.updatedGame = updatedGame;
		this.clientEvent = state;
		this.stats = stats;
		this.search = search;
		this.tradeMsg = tradeMsg;
		this.balance = balance;
	}
	
	public ClientEvent(ClientEventType state) {
		this.clientEvent = state;
	}
	
	public GameDTO getGame() {
		return updatedGame;
	}
	
	public EndOfGameStats getEndOfGameStats() {
		return stats;
	}
	
	public SearchingForMatchNotification getSearchNotification() {
		return search;
	}
	
	public ChampionTradeMessage getTradeMessage() {
		return tradeMsg;
	}
	
	public StoreAccountBalanceNotification getBalance() {
		return balance;
	}
	
	public ClientEventType getType() {
		return clientEvent;
	}

	
}
