package com.fjxokt.lolclient.lolrtmps.services;

import com.fjxokt.lolclient.lolrtmps.model.AggregatedStats;
import com.fjxokt.lolclient.lolrtmps.model.ChampionStatInfo;
import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;
import com.fjxokt.lolclient.lolrtmps.model.PlayerLifetimeStats;
import com.fjxokt.lolclient.lolrtmps.model.RecentGames;
import com.fjxokt.lolclient.lolrtmps.model.TeamId;
import com.fjxokt.lolclient.lolrtmps.model.dto.TeamAggregatedStatsDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameMode;
import com.fjxokt.lolclient.lolrtmps.model.utils.PlayerBaseLevel;
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
public class PlayerStatsService {
    
        public static ResultMessage processEloQuestionaire(LoLRTMPSClient client, PlayerBaseLevel level) {
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "processEloQuestionaire", new Object[] { level.getLevel() });
                
                if (playerStatTo == null) {
                        return ResultMessage.ERROR;
                }

                return ResultMessage.OK;
	}
	
	public static PlayerLifetimeStats retrievePlayerStatsByAccountId(LoLRTMPSClient client, Integer accountId) {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "retrievePlayerStatsByAccountId", new Object[] { accountId, "CURRENT"  });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new PlayerLifetimeStats(playerStatTo);
	}
	
	public static List<ChampionStatInfo> retrieveTopPlayedChampions(LoLRTMPSClient client, Integer accountId, GameMode mode) {
            
                Object[] championStats = LoLClientService.getServiceResponseDataBodyArray(client, "playerStatsService", "retrieveTopPlayedChampions", new Object[] { accountId, mode.getName() });
		
                if(championStats == null){
                    return null;
                }
                
                List<Object> championStatsAsObjects = Arrays.asList(championStats);
                List<ChampionStatInfo> championStatsAsStats = new ArrayList<ChampionStatInfo>(championStatsAsObjects.size());
                
                for(Object o : championStatsAsObjects){
                    TypedObject champStat = (TypedObject)o;
                    if(champStat != null){
                        championStatsAsStats.add(new ChampionStatInfo(champStat));
                    }
                }
                
                return championStatsAsStats;
	}
	
	public static AggregatedStats getAggregatedStats(LoLRTMPSClient client, Integer accountId, GameMode mode) {
                
		// TODO: check if it's always "CURRENT"
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getAggregatedStats", new Object[] { accountId, mode.getName(), "CURRENT"  });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new AggregatedStats(playerStatTo);
	}
	
	public static List<TeamAggregatedStatsDTO> getTeamAggregatedStats(LoLRTMPSClient client, String teamId) {
            
                Object[] teamStats = LoLClientService.getServiceResponseDataBodyArray(client, "playerStatsService", "getTeamAggregatedStats", new Object[] { teamId });
		
                if(teamStats == null){
                    return null;
                }
                
                List<Object> teamStatsAsObjects = Arrays.asList(teamStats);
                List<TeamAggregatedStatsDTO> teamStatsAsStats = new ArrayList<TeamAggregatedStatsDTO>(teamStatsAsObjects.size());
                
                for(Object o : teamStatsAsObjects){
                    TypedObject teamStat = (TypedObject)o;
                    if(teamStat != null){
                        teamStatsAsStats.add(new TeamAggregatedStatsDTO(teamStat));
                    }
                }
                
                return teamStatsAsStats;
	}
	
	public static EndOfGameStats getTeamEndOfGameStats(LoLRTMPSClient client, TeamId teamId, Double gameId) {
                
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getTeamEndOfGameStats", new Object[] { teamId.getTypedObject(), gameId });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new EndOfGameStats(playerStatTo);
	}
	
	public static RecentGames getRecentGames(LoLRTMPSClient client, Integer accountId) {
            
                TypedObject playerStatTo = LoLClientService.getTypedServiceResponseDataBody(client, "playerStatsService", "getRecentGames", new Object[] { accountId });
                
                if (playerStatTo == null) {
                        return null;
                }

                return new RecentGames(playerStatTo);
	}

    private PlayerStatsService() {
    }
}
