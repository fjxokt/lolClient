package com.fjxokt.lolclient.lolrtmps.model.utils;

public enum TerminatedCondition {
	
	NOT_TERMINATED(0, "NOT_TERMINATED"), TERMINATED(1, "TERMINATED"), TERMINATED_IN_ERROR(3, "TERMINATED_IN_ERROR"), LOBBY_EXPIRED(4, "LOBBY_EXPIRED");
	
	private int id;
	private String name;
	
	TerminatedCondition(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static TerminatedCondition getStateFromString(String state) {
		TerminatedCondition[] values = TerminatedCondition.values();
		for (TerminatedCondition s : values) {
			if (s.getName().equals(state)) {
				return s;
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TerminatedCondition [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
