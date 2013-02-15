package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum QueueType {
	
	RANKED_SOLO_5x5("RANKED_SOLO_5x5"), RANKED_TEAM_5x5("RANKED_TEAM_5x5"), RANKED_TEAM_3x3("RANKED_TEAM_3x3"),
	NORMAL_3x3("NORMAL_3x3"), ODIN_UNRANKED("ODIN_UNRANKED"), NORMAL("NORMAL"), NONE("NONE"),
	BOT("BOT"), BOT_3x3("BOT_3x3");
	
	private String type;
	
	QueueType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public static QueueType getTypeFromString(String type) {
		QueueType[] values = QueueType.values();
		for (QueueType s : values) {
			if (s.getType().equals(type)) {
				return s;
			}
		}
		return null;
	}

}
