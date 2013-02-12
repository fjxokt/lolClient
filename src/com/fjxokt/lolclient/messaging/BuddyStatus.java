package com.fjxokt.lolclient.messaging;

import com.fjxokt.lolclient.utils.SimpleXML;

public class BuddyStatus {
	
	/*
	 * buddy status:
	 *  <body>
	 *  	<profileIcon>0</profileIcon>
	 *  	<level>5</level>
	 *  	<wins>1</wins>
	 *  	<leaves>1</leaves>
	 *  	<odinWins>1</odinWins>
	 *  	<odinLeaves>0</odinLeaves>
	 *  	<queueType />
	 *  	<rankedWins>0</rankedWins>
	 *  	<rankedLosses>0</rankedLosses>
	 *  	<rankedRating>0</rankedRating>
	 *  	<tier>UNRANKED</tier><statusMsg />
	 *  	<skinname>Ryze</skinname>
	 *  	<gameQueueType>NORMAL_3x3</gameQueueType>
	 *  	<gameStatus>inGame</gameStatus> OR outOfGame
	 *  	<isObservable>ALL</isObservable> (PROBABLY ONLY IF IN GAME)
	 *  	<timeStamp>1358179214080</timeStamp>
	 *  	<dropInSpectateGameId>DaniixHard</dropInSpectateGameId>
	 *  	<featuredGameData>null</featuredGameData>
	 *  </body>
	 */
	
	private Integer profileIcon;
	private Integer level;
	private Integer wins;
	private Integer leaves;
	private Integer odinWins;
	private Integer odinLeaves;
	private String queueType;
	private Integer rankedWins;
	private Integer rankedLosses;
	private Integer rankedRating;
	private String tier;
	private String skinname;
	private String gameQueueType;
	private String gameStatus;
	private String isObservable;
	private Double timeStamp;
	private String dropInSpectateGameId;
	private String featuredGameData; // TODO: check what is this
	
	public BuddyStatus() {
		
	}

	public BuddyStatus(Integer profileIcon, Integer level, Integer wins,
			Integer leaves, Integer odinWins, Integer odinLeaves,
			String queueType, Integer rankedWins, Integer rankedLosses,
			Integer rankedRating, String tier, String skinname,
			String gameQueueType, String gameStatus, String isObservable,
			Double timeStamp, String dropInSpectateGameId,
			String featuredGameData) {
		super();
		this.profileIcon = profileIcon;
		this.level = level;
		this.wins = wins;
		this.leaves = leaves;
		this.odinWins = odinWins;
		this.odinLeaves = odinLeaves;
		this.queueType = queueType;
		this.rankedWins = rankedWins;
		this.rankedLosses = rankedLosses;
		this.rankedRating = rankedRating;
		this.tier = tier;
		this.skinname = skinname;
		this.gameQueueType = gameQueueType;
		this.gameStatus = gameStatus;
		this.isObservable = isObservable;
		this.timeStamp = timeStamp;
		this.dropInSpectateGameId = dropInSpectateGameId;
		this.featuredGameData = featuredGameData;
	}
	
	public BuddyStatus(String xml) {
		this.profileIcon = SimpleXML.getIntTagValue(xml, "profileIcon");
		this.level = SimpleXML.getIntTagValue(xml, "level");
		this.wins = SimpleXML.getIntTagValue(xml, "wins");
		this.leaves = SimpleXML.getIntTagValue(xml, "leaves");
		this.odinWins = SimpleXML.getIntTagValue(xml, "odinWins");
		this.odinLeaves = SimpleXML.getIntTagValue(xml, "odinLeaves");
		this.queueType = SimpleXML.getTagValue(xml, "queueType");
		this.rankedWins = SimpleXML.getIntTagValue(xml, "rankedWins");
		this.rankedLosses = SimpleXML.getIntTagValue(xml, "rankedLosses");
		this.rankedRating = SimpleXML.getIntTagValue(xml, "rankedRating");
		this.tier = SimpleXML.getTagValue(xml, "tier");
		this.skinname = SimpleXML.getTagValue(xml, "skinname");
		this.gameQueueType = SimpleXML.getTagValue(xml, "gameQueueType");
		this.gameStatus = SimpleXML.getTagValue(xml, "gameStatus");
		this.isObservable = SimpleXML.getTagValue(xml, "isObservable");
		this.timeStamp = SimpleXML.getDoubleTagValue(xml, "timeStamp");
		this.dropInSpectateGameId = SimpleXML.getTagValue(xml, "dropInSpectateGameId");
		this.featuredGameData = SimpleXML.getTagValue(xml, "featuredGameData");
	}

	public Integer getProfileIcon() {
		return profileIcon;
	}

	public void setProfileIcon(Integer profileIcon) {
		this.profileIcon = profileIcon;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLeaves() {
		return leaves;
	}

	public void setLeaves(Integer leaves) {
		this.leaves = leaves;
	}

	public Integer getOdinWins() {
		return odinWins;
	}

	public void setOdinWins(Integer odinWins) {
		this.odinWins = odinWins;
	}

	public Integer getOdinLeaves() {
		return odinLeaves;
	}

	public void setOdinLeaves(Integer odinLeaves) {
		this.odinLeaves = odinLeaves;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public Integer getRankedWins() {
		return rankedWins;
	}

	public void setRankedWins(Integer rankedWins) {
		this.rankedWins = rankedWins;
	}

	public Integer getRankedLosses() {
		return rankedLosses;
	}

	public void setRankedLosses(Integer rankedLosses) {
		this.rankedLosses = rankedLosses;
	}

	public Integer getRankedRating() {
		return rankedRating;
	}

	public void setRankedRating(Integer rankedRating) {
		this.rankedRating = rankedRating;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getSkinname() {
		return skinname;
	}

	public void setSkinname(String skinname) {
		this.skinname = skinname;
	}

	public String getGameQueueType() {
		return gameQueueType;
	}

	public void setGameQueueType(String gameQueueType) {
		this.gameQueueType = gameQueueType;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getIsObservable() {
		return isObservable;
	}

	public void setIsObservable(String isObservable) {
		this.isObservable = isObservable;
	}

	public Double getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Double timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDropInSpectateGameId() {
		return dropInSpectateGameId;
	}

	public void setDropInSpectateGameId(String dropInSpectateGameId) {
		this.dropInSpectateGameId = dropInSpectateGameId;
	}

	public String getFeaturedGameData() {
		return featuredGameData;
	}

	public void setFeaturedGameData(String featuredGameData) {
		this.featuredGameData = featuredGameData;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuddyStatus [dropInSpectateGameId=");
		builder.append(dropInSpectateGameId);
		builder.append(", featuredGameData=");
		builder.append(featuredGameData);
		builder.append(", gameQueueType=");
		builder.append(gameQueueType);
		builder.append(", gameStatus=");
		builder.append(gameStatus);
		builder.append(", isObservable=");
		builder.append(isObservable);
		builder.append(", leaves=");
		builder.append(leaves);
		builder.append(", level=");
		builder.append(level);
		builder.append(", odinLeaves=");
		builder.append(odinLeaves);
		builder.append(", odinWins=");
		builder.append(odinWins);
		builder.append(", profileIcon=");
		builder.append(profileIcon);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", rankedLosses=");
		builder.append(rankedLosses);
		builder.append(", rankedRating=");
		builder.append(rankedRating);
		builder.append(", rankedWins=");
		builder.append(rankedWins);
		builder.append(", skinname=");
		builder.append(skinname);
		builder.append(", tier=");
		builder.append(tier);
		builder.append(", timeStamp=");
		builder.append(timeStamp);
		builder.append(", wins=");
		builder.append(wins);
		builder.append("]");
		return builder.toString();
	}

	public String toXML() {
		StringBuilder builder = new StringBuilder();
		builder.append("<body>");
		builder.append("<profileIcon>").append(profileIcon).append("</profileIcon>");
		builder.append("<level>").append(level).append("</level>");
		builder.append("<wins>").append(wins).append("</wins>");
		builder.append("<leaves>").append(leaves).append("</leaves>");
		builder.append("<odinWins>").append(odinWins).append("</odinWins>");
		builder.append("<odinLeaves>").append(odinLeaves).append("</odinLeaves>");
		builder.append("<queueType>").append(queueType).append("</queueType>");
		builder.append("<rankedWins>").append(rankedWins).append("</rankedWins>");
		builder.append("<rankedLosses>").append(rankedLosses).append("</rankedLosses>");
		builder.append("<rankedRating>").append(rankedRating).append("</rankedRating>");
		builder.append("<tier>").append(tier).append("</tier>");
		builder.append("<skinname>").append(skinname).append("</skinname>");
		builder.append("<gameQueueType>").append(gameQueueType).append("</gameQueueType>");
		builder.append("<gameStatus>").append(gameStatus).append("</gameStatus>");
		builder.append("<isObservable>").append(isObservable).append("</isObservable>");
		builder.append("<timeStamp>").append(timeStamp).append("</timeStamp>");
		builder.append("<dropInSpectateGameId>").append(dropInSpectateGameId).append("</dropInSpectateGameId>");
		builder.append("<featuredGameData>").append(featuredGameData).append("</featuredGameData>");
		builder.append("</body>");
		return builder.toString();
	}

}
