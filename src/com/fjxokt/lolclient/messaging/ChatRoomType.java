package com.fjxokt.lolclient.messaging;

public enum ChatRoomType {
	
	PRIVATE("pr"), ARRANGING_GAME("ag"), PUBLIC("pu"), GLOBAL("gl"), RANKED_TEAM("tm"), QUEUED("aq"),
	POST_GAME("pg"), ARRANGING_PRACTICE("ap"), CHAMPION_SELECT1("c1"), CHAMPION_SELECT2("c2");
	
	private String type;

	static final String PREFIX_DELIMITER = "~";
	
	ChatRoomType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
