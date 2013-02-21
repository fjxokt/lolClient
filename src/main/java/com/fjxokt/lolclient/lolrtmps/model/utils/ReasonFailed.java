package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum ReasonFailed {

	RANKED_NUM_CHAMPS("RANKED_NUM_CHAMPS");
	
	private String name;
	
	ReasonFailed(String name) {
		this.name = name;
	}
	
	public static ReasonFailed getSReasonFromString(String state) {
		ReasonFailed[] values = ReasonFailed.values();
		for (ReasonFailed s : values) {
			if (s.getName().equals(state)) {
				return s;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
	
}

