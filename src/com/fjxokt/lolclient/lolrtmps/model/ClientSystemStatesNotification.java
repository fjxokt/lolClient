package com.fjxokt.lolclient.lolrtmps.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fjxokt.lolclient.lolrtmps.model.dto.GameMapEnabledDTO;
import com.gvaneyck.rtmp.TypedObject;

public class ClientSystemStatesNotification extends ClassType {

	private Boolean displayPromoGamesPlayedEnabled;
	private Boolean runeUniquePerSpellBook;
	private Boolean tournamentSendStatsEnabled;
	private Boolean modularGameModeEnabled;
	private Boolean buddyNotesEnabled;
	private String observableCustomGameModes;
	private Integer minNumPlayersForPracticeGame;
	private Boolean localeSpecificChatRoomsEnabled;
	private Boolean teamServiceEnabled;
	private Integer spectatorSlotLimit;
	private Integer maxMasteryPagesOnServer;
	private Boolean storeCustomerEnabled;
	private Boolean tribunalEnabled;
	private Boolean practiceGameEnabled;
	private Boolean advancedTutorialEnabled;
	private Boolean observerModeEnabled;
	private Boolean archivedStatsEnabled;
	private Integer clientHeartBeatRateSeconds;
	private Boolean kudosEnabled;
	
	private List<String> observableGameModes;
	
	private List<GameMapEnabledDTO> gameMapEnabledDTOList;
	
	private List<Integer> inactiveOdinSpellIdList;
	private List<Integer> inactiveTutorialSpellIdList;
	private List<Integer> inactiveSpellIdList;
	private List<Integer> inactiveAramSpellIdList;
	private List<Integer> inactiveChampionIdList;
	private List<Integer> inactiveClassicSpellIdList;
	private List<Integer> enabledQueueIdsList;
	private List<Integer> freeToPlayChampionIdList;
	private List<Integer> unobtainableChampionSkinIDList;
	private List<Integer> practiceGameTypeConfigIdList;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.systemstate.ClientSystemStatesNotification";
	}
	
	public static String getTypeClass() {
		return "com.riotgames.platform.systemstate.ClientSystemStatesNotification";
	}
	
	private List<Integer> fillList(TypedObject to, String key) {
		List<Integer> res = new ArrayList<Integer>();
		Object[] objs = to.getArray("inactiveOdinSpellIdList");
		if (objs != null) {
			for (Object o : objs) {
				res.add((Integer)o);
			}
		}
		return res;
	}
	
	public ClientSystemStatesNotification(TypedObject to) {
		super();
		
		this.displayPromoGamesPlayedEnabled = to.getBool("displayPromoGamesPlayedEnabled");
		this.runeUniquePerSpellBook = to.getBool("runeUniquePerSpellBook");
		this.tournamentSendStatsEnabled = to.getBool("tournamentSendStatsEnabled");
		this.modularGameModeEnabled = to.getBool("modularGameModeEnabled");
		this.buddyNotesEnabled = to.getBool("buddyNotesEnabled");
		this.observableCustomGameModes = to.getString("observableCustomGameModes");
		this.minNumPlayersForPracticeGame = to.getInt("minNumPlayersForPracticeGame");
		this.localeSpecificChatRoomsEnabled = to.getBool("localeSpecificChatRoomsEnabled");
		this.teamServiceEnabled = to.getBool("teamServiceEnabled");
		this.spectatorSlotLimit = to.getInt("spectatorSlotLimit");
		this.maxMasteryPagesOnServer = to.getInt("maxMasteryPagesOnServer");
		this.storeCustomerEnabled = to.getBool("storeCustomerEnabled");
		this.tribunalEnabled = to.getBool("tribunalEnabled");
		this.practiceGameEnabled = to.getBool("practiceGameEnabled");
		this.advancedTutorialEnabled = to.getBool("advancedTutorialEnabled");
		this.observerModeEnabled = to.getBool("observerModeEnabled");
		this.archivedStatsEnabled = to.getBool("archivedStatsEnabled");
		this.clientHeartBeatRateSeconds = to.getInt("clientHeartBeatRateSeconds");
		this.kudosEnabled = to.getBool("kudosEnabled");

		this.observableGameModes = new ArrayList<String>();
		Object[] objs = to.getArray("observableGameModes");
		if (objs != null) {
			for (Object o : objs) {
				observableGameModes.add((String)o);
			}
		}
		
		this.gameMapEnabledDTOList = new ArrayList<GameMapEnabledDTO>();
		objs = to.getArray("gameMapEnabledDTOList");
		if (objs != null) {
			for (Object o : objs) {
				@SuppressWarnings("unchecked")
				Map<String, Integer> map = (HashMap<String, Integer>)o;
				gameMapEnabledDTOList.add(new GameMapEnabledDTO(map.get("gameMapId"), map.get("minPlayers")));
			}
		}
		
		this.inactiveOdinSpellIdList = fillList(to, "inactiveOdinSpellIdList");
		this.inactiveTutorialSpellIdList = fillList(to, "inactiveTutorialSpellIdList");
		this.inactiveSpellIdList = fillList(to, "inactiveSpellIdList");
		this.inactiveAramSpellIdList = fillList(to, "inactiveAramSpellIdList");
		this.inactiveChampionIdList = fillList(to, "inactiveChampionIdList");
		this.inactiveClassicSpellIdList = fillList(to, "inactiveClassicSpellIdList");
		this.enabledQueueIdsList = fillList(to, "enabledQueueIdsList");
		this.freeToPlayChampionIdList = fillList(to, "freeToPlayChampionIdList");
		this.unobtainableChampionSkinIDList = fillList(to, "unobtainableChampionSkinIDList");
		this.practiceGameTypeConfigIdList = fillList(to, "practiceGameTypeConfigIdList");
	}

	public Boolean getDisplayPromoGamesPlayedEnabled() {
		return displayPromoGamesPlayedEnabled;
	}

	public Boolean getRuneUniquePerSpellBook() {
		return runeUniquePerSpellBook;
	}

	public Boolean getTournamentSendStatsEnabled() {
		return tournamentSendStatsEnabled;
	}

	public Boolean getModularGameModeEnabled() {
		return modularGameModeEnabled;
	}

	public Boolean getBuddyNotesEnabled() {
		return buddyNotesEnabled;
	}

	public String getObservableCustomGameModes() {
		return observableCustomGameModes;
	}

	public Integer getMinNumPlayersForPracticeGame() {
		return minNumPlayersForPracticeGame;
	}

	public Boolean getLocaleSpecificChatRoomsEnabled() {
		return localeSpecificChatRoomsEnabled;
	}

	public Boolean getTeamServiceEnabled() {
		return teamServiceEnabled;
	}

	public Integer getSpectatorSlotLimit() {
		return spectatorSlotLimit;
	}

	public Integer getMaxMasteryPagesOnServer() {
		return maxMasteryPagesOnServer;
	}

	public Boolean getStoreCustomerEnabled() {
		return storeCustomerEnabled;
	}

	public Boolean getTribunalEnabled() {
		return tribunalEnabled;
	}

	public Boolean getPracticeGameEnabled() {
		return practiceGameEnabled;
	}

	public Boolean getAdvancedTutorialEnabled() {
		return advancedTutorialEnabled;
	}

	public Boolean getObserverModeEnabled() {
		return observerModeEnabled;
	}

	public Boolean getArchivedStatsEnabled() {
		return archivedStatsEnabled;
	}

	public Integer getClientHeartBeatRateSeconds() {
		return clientHeartBeatRateSeconds;
	}

	public Boolean getKudosEnabled() {
		return kudosEnabled;
	}

	public List<String> getObservableGameModes() {
		return observableGameModes;
	}

	public List<GameMapEnabledDTO> getGameMapEnabledDTOList() {
		return gameMapEnabledDTOList;
	}

	public List<Integer> getInactiveOdinSpellIdList() {
		return inactiveOdinSpellIdList;
	}

	public List<Integer> getInactiveTutorialSpellIdList() {
		return inactiveTutorialSpellIdList;
	}

	public List<Integer> getInactiveSpellIdList() {
		return inactiveSpellIdList;
	}

	public List<Integer> getInactiveAramSpellIdList() {
		return inactiveAramSpellIdList;
	}

	public List<Integer> getInactiveChampionIdList() {
		return inactiveChampionIdList;
	}

	public List<Integer> getInactiveClassicSpellIdList() {
		return inactiveClassicSpellIdList;
	}

	public List<Integer> getEnabledQueueIdsList() {
		return enabledQueueIdsList;
	}

	public List<Integer> getFreeToPlayChampionIdList() {
		return freeToPlayChampionIdList;
	}

	public List<Integer> getUnobtainableChampionSkinIDList() {
		return unobtainableChampionSkinIDList;
	}

	public List<Integer> getPracticeGameTypeConfigIdList() {
		return practiceGameTypeConfigIdList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientSystemStatesNotification [displayPromoGamesPlayedEnabled=");
		builder.append(displayPromoGamesPlayedEnabled);
		builder.append(", runeUniquePerSpellBook=");
		builder.append(runeUniquePerSpellBook);
		builder.append(", tournamentSendStatsEnabled=");
		builder.append(tournamentSendStatsEnabled);
		builder.append(", modularGameModeEnabled=");
		builder.append(modularGameModeEnabled);
		builder.append(", buddyNotesEnabled=");
		builder.append(buddyNotesEnabled);
		builder.append(", observableCustomGameModes=");
		builder.append(observableCustomGameModes);
		builder.append(", minNumPlayersForPracticeGame=");
		builder.append(minNumPlayersForPracticeGame);
		builder.append(", localeSpecificChatRoomsEnabled=");
		builder.append(localeSpecificChatRoomsEnabled);
		builder.append(", teamServiceEnabled=");
		builder.append(teamServiceEnabled);
		builder.append(", spectatorSlotLimit=");
		builder.append(spectatorSlotLimit);
		builder.append(", maxMasteryPagesOnServer=");
		builder.append(maxMasteryPagesOnServer);
		builder.append(", storeCustomerEnabled=");
		builder.append(storeCustomerEnabled);
		builder.append(", tribunalEnabled=");
		builder.append(tribunalEnabled);
		builder.append(", practiceGameEnabled=");
		builder.append(practiceGameEnabled);
		builder.append(", advancedTutorialEnabled=");
		builder.append(advancedTutorialEnabled);
		builder.append(", observerModeEnabled=");
		builder.append(observerModeEnabled);
		builder.append(", archivedStatsEnabled=");
		builder.append(archivedStatsEnabled);
		builder.append(", clientHeartBeatRateSeconds=");
		builder.append(clientHeartBeatRateSeconds);
		builder.append(", kudosEnabled=");
		builder.append(kudosEnabled);
		builder.append(", observableGameModes=");
		builder.append(observableGameModes);
		builder.append(", gameMapEnabledDTOList=");
		builder.append(gameMapEnabledDTOList);
		builder.append(", inactiveOdinSpellIdList=");
		builder.append(inactiveOdinSpellIdList);
		builder.append(", inactiveTutorialSpellIdList=");
		builder.append(inactiveTutorialSpellIdList);
		builder.append(", inactiveSpellIdList=");
		builder.append(inactiveSpellIdList);
		builder.append(", inactiveAramSpellIdList=");
		builder.append(inactiveAramSpellIdList);
		builder.append(", inactiveChampionIdList=");
		builder.append(inactiveChampionIdList);
		builder.append(", inactiveClassicSpellIdList=");
		builder.append(inactiveClassicSpellIdList);
		builder.append(", enabledQueueIdsList=");
		builder.append(enabledQueueIdsList);
		builder.append(", freeToPlayChampionIdList=");
		builder.append(freeToPlayChampionIdList);
		builder.append(", unobtainableChampionSkinIDList=");
		builder.append(unobtainableChampionSkinIDList);
		builder.append(", practiceGameTypeConfigIdList=");
		builder.append(practiceGameTypeConfigIdList);
		builder.append("]");
		return builder.toString();
	}

}
