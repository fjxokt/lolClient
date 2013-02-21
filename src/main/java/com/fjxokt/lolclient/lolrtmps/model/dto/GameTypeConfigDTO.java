package com.fjxokt.lolclient.lolrtmps.model.dto;

import com.fjxokt.lolclient.lolrtmps.model.ClassType;
import com.gvaneyck.rtmp.TypedObject;

public class GameTypeConfigDTO extends ClassType {

	private Integer id;
	private String name;
	private Integer mainPickTimerDuration;
	private Boolean exclusivePick;
	private Boolean allowTrades;
	private String pickMode;
	private Integer maxAllowableBans;
	private Integer banTimerDuration;
	private Integer postPickTimerDuration;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.game.GameTypeConfigDTO";
	}
	
	public GameTypeConfigDTO(TypedObject to) {
		super();
		this.id = to.getInt("id");
		this.name = to.getString("name");
		this.mainPickTimerDuration = to.getInt("mainPickTimerDuration");
		this.exclusivePick = to.getBool("exclusivePick");
		this.allowTrades = to.getBool("allowTrades");
		this.pickMode = to.getString("pickMode");
		this.maxAllowableBans = to.getInt("maxAllowableBans");
		this.banTimerDuration = to.getInt("banTimerDuration");
		this.postPickTimerDuration = to.getInt("postPickTimerDuration");
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getMainPickTimerDuration() {
		return mainPickTimerDuration;
	}

	public Boolean getExclusivePick() {
		return exclusivePick;
	}

	public Boolean getAllowTrades() {
		return allowTrades;
	}

	public String getPickMode() {
		return pickMode;
	}

	public Integer getMaxAllowableBans() {
		return maxAllowableBans;
	}

	public Integer getBanTimerDuration() {
		return banTimerDuration;
	}

	public Integer getPostPickTimerDuration() {
		return postPickTimerDuration;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameTypeConfigDTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", mainPickTimerDuration=");
		builder.append(mainPickTimerDuration);
		builder.append(", exclusivePick=");
		builder.append(exclusivePick);
		builder.append(", allowTrades=");
		builder.append(allowTrades);
		builder.append(", pickMode=");
		builder.append(pickMode);
		builder.append(", maxAllowableBans=");
		builder.append(maxAllowableBans);
		builder.append(", banTimerDuration=");
		builder.append(banTimerDuration);
		builder.append(", postPickTimerDuration=");
		builder.append(postPickTimerDuration);
		builder.append("]");
		return builder.toString();
	}

}
