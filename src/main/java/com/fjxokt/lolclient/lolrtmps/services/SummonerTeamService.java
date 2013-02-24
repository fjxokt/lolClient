package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamDTO;
import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.gvaneyck.rtmp.TypedObject;

/**
 *
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class SummonerTeamService {
    
        public static PlayerDTO createPlayer(LoLRTMPSClient client) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "createPlayer", new Object[] {});
                
                if (teamTo == null) {
                    return null;
                }

                return new PlayerDTO(teamTo);
	}
	
	public static TeamDTO createTeam(LoLRTMPSClient client, String teamName, String teamTag) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "createTeam", new Object[] { teamName, teamTag });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
	}
	
	public static TeamDTO invitePlayer(LoLRTMPSClient client, Double summonerId, TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "invitePlayer", new Object[] { summonerId, teamId.getTypedObject() });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
                
	}
	
	public static TeamDTO findTeamById(LoLRTMPSClient client, TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamById", new Object[] { teamId.getTypedObject() });
                
                if (teamTo == null) {
                    return null;
                }
			
		return new TeamDTO(teamTo);
	}
	
	public static TeamDTO findTeamByTag(LoLRTMPSClient client, String tag) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamByTag", new Object[] { tag });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
 
	}
	
	public static TeamDTO findTeamByName(LoLRTMPSClient client, String name) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "findTeamByName", new Object[] { name });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
	}
	
	public static TeamDTO disbandTeam(LoLRTMPSClient client, TeamId teamId) {
                TypedObject teamTo = LoLClientService.getTypedServiceResponseDataBody(client, "summonerTeamService", "disbandTeam", new Object[] { teamId.getTypedObject() });
                
                if (teamTo == null) {
                        return null;
                }

                return new TeamDTO(teamTo);
	}
	
	public static Boolean isNameValidAndAvailable(LoLRTMPSClient client, String teamName) {
                Object isValid = LoLClientService.getServiceResponseDataBody(client, "summonerTeamService", "isNameValidAndAvailable", new Object[] { teamName});
                
                if (isValid == null) {
                        return null;
                }

                return (Boolean)isValid;
	}
	
	public static Boolean isTagValidAndAvailable(LoLRTMPSClient client, String tagName) {
                Object isValid = LoLClientService.getServiceResponseDataBody(client, "summonerTeamService", "isTagValidAndAvailable", new Object[] { tagName});
                
                if (isValid == null) {
                        return null;
                }

                return (Boolean)isValid;
	}

    private SummonerTeamService() {
    }
}
