package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.List;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameTypeConfigDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlatformGameLifecycleDTO;
import com.gvaneyck.rtmp.TypedObject;

public class LoginDataPacket extends ClassType {

	private Double ipBalance;
	private Double rpBalance;
	private Boolean matchMakingEnabled;
	private Integer maxPracticeGameSize;
	private Double timeUntilFirstWinOfDay;
	private String competitiveRegion;
	private PlatformGameLifecycleDTO reconnectInfo;
	private AllSummonerData allSummonerData;
	private List<GameTypeConfigDTO> gameTypeConfigs;
	private PlayerStatSummaries playerStatSummaries;
	private ClientSystemStatesNotification clientSystemStates;
	private SummonerCatalog summonerCatalog;
	private List<String> languages;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.clientfacade.domain.LoginDataPacket";
	}
	
	public LoginDataPacket(TypedObject to) {
		this.ipBalance = to.getDouble("ipBalance");
		this.rpBalance = to.getDouble("rpBalance");
		this.matchMakingEnabled = to.getBool("matchMakingEnabled");
		this.maxPracticeGameSize = to.getInt("maxPracticeGameSize");
		this.timeUntilFirstWinOfDay = to.getDouble("timeUntilFirstWinOfDay");
		this.competitiveRegion = to.getString("competitiveRegion");
		
		TypedObject tobj = to.getTO("reconnectInfo");
		this.reconnectInfo = (tobj == null) ? null : new PlatformGameLifecycleDTO(tobj);
		tobj = to.getTO("allSummonerData");
		this.allSummonerData = (tobj == null) ? null : new AllSummonerData(tobj);
		
		this.gameTypeConfigs = new ArrayList<GameTypeConfigDTO>();
		Object[] configs = to.getArray("gameTypeConfigs");
		if (configs != null) {
			for (Object o : configs) {
				TypedObject co = (TypedObject)o;
				if (co != null) {
					gameTypeConfigs.add(new GameTypeConfigDTO(co));
				}
			}
		}
		
		tobj = to.getTO("playerStatSummaries");
		this.playerStatSummaries = (tobj == null) ? null : new PlayerStatSummaries(tobj);
		
		this.languages = new ArrayList<String>();
		configs = to.getArray("languages");
		if (configs != null) {
			for (Object o : configs) {
				String locale = (String)o;
				if (locale != null) {
					languages.add(locale);
				}
			}
		}
		
		tobj = to.getTO("clientSystemStates");
		this.clientSystemStates = (tobj == null) ? null : new ClientSystemStatesNotification(tobj);
		
		tobj = to.getTO("summonerCatalog");
		this.summonerCatalog = (tobj == null) ? null : new SummonerCatalog(tobj);
	}

	public Double getIpBalance() {
		return ipBalance;
	}
	
	public void setIpBalance(Double ipBalance) {
		this.ipBalance = ipBalance;
	}

	public Double getRpBalance() {
		return rpBalance;
	}
	
	public void setRpBalance(Double rpBalance) {
		this.rpBalance = rpBalance;
	}

	public Boolean getMatchMakingEnabled() {
		return matchMakingEnabled;
	}

	public PlatformGameLifecycleDTO getReconnectInfo() {
		return reconnectInfo;
	}

	public AllSummonerData getAllSummonerData() {
		return allSummonerData;
	}
	
	public void setAllSummonerData(AllSummonerData allSummonerData) {
		this.allSummonerData = allSummonerData;
	}

	public Integer getMaxPracticeGameSize() {
		return maxPracticeGameSize;
	}

	public Double getTimeUntilFirstWinOfDay() {
		return timeUntilFirstWinOfDay;
	}

	public String getCompetitiveRegion() {
		return competitiveRegion;
	}

	public List<GameTypeConfigDTO> getGameTypeConfigs() {
		return gameTypeConfigs;
	}
	
	public PlayerStatSummaries getPlayerStatSummaries() {
		return playerStatSummaries;
	}
	
	public ClientSystemStatesNotification getClientSystemStates() {
		return clientSystemStates;
	}
	
	public SummonerCatalog getSummonerCatalog() {
		return summonerCatalog;
	}
	
	public List<String> getLanguages() {
		return languages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginDataPacket [ipBalance=");
		builder.append(ipBalance);
		builder.append(", rpBalance=");
		builder.append(rpBalance);
		builder.append(", matchMakingEnabled=");
		builder.append(matchMakingEnabled);
		builder.append(", reconnectInfo=");
		builder.append(reconnectInfo);
		builder.append(", allSummonerData=");
		builder.append(allSummonerData);
		builder.append(", maxPracticeGameSize=");
		builder.append(maxPracticeGameSize);
		builder.append(", timeUntilFirstWinOfDay=");
		builder.append(timeUntilFirstWinOfDay);
		builder.append(", competitiveRegion=");
		builder.append(competitiveRegion);
		builder.append(", gameTypeConfigs=");
		builder.append(gameTypeConfigs);		
		builder.append(", playerStatSummaries=");
		builder.append(playerStatSummaries);
		builder.append(", clientSystemStates=");
		builder.append(clientSystemStates);
		builder.append(", summonerCatalog=");
		builder.append(summonerCatalog);
		builder.append(", languages=");
		builder.append(languages);
		builder.append("]");
		return builder.toString();
	}

}
