package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.dto.LeagueListDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SummonerLeaguesDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.QueueType;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class LeaguesService {
        
        public static SummonerLeaguesDTO getAllMyLeagues(LoLRTMPSClient client) {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllMyLeagues", new Object[] {});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public static SummonerLeaguesDTO getAllLeaguesForPlayer(LoLRTMPSClient client, Double summonerId) {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllLeaguesForPlayer", new Object[] {summonerId});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public static SummonerLeaguesDTO getLeaguesForTeam(LoLRTMPSClient client, String teamId) {
            
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getAllLeaguesForPlayer", new Object[] {teamId});
                
                if (leagueTo == null) {
                        return null;
                }

                return new SummonerLeaguesDTO(leagueTo);
	}
	
	public static LeagueListDTO getChallengerLeague(LoLRTMPSClient client, QueueType type) {
                TypedObject leagueTo = LoLClientService.getTypedServiceResponseDataBody(client, "leaguesServiceProxy", "getChallengerLeague", new Object[] {type.getType() });
                
                if (leagueTo == null) {
                        return null;
                }

                return new LeagueListDTO(leagueTo);
	}
}
