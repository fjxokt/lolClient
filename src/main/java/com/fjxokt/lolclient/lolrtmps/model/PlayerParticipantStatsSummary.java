package com.fjxokt.lolclient.lolrtmps.model;

import com.fjxokt.lolclient.lolrtmps.model.dto.RawStatDTO;
import com.gvaneyck.rtmp.TypedObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerParticipantStatsSummary extends ClassType {

	private Double userId;
	private String summonerName;
	private Double level;
	private Integer profileIconId;
	private Double gameId;
	private String skinName;
	private Integer elo;
	private Boolean leaver;
	private Double leaves;
	private Double teamId;
	private Integer eloChange;
	private List<RawStatDTO> statistics;
	private Boolean botPlayer;
	private Double spell1Id;
	private Double spell2Id;
	private Double wins;
	private Double losses;
	
	@Override
	protected String getTypeName() {
		return "com.riotgames.platform.statistics.PlayerParticipantStatsSummary";
	}
	
	public PlayerParticipantStatsSummary(TypedObject to) {
		super();
		this.userId = to.getDouble("userId");
		this.summonerName = to.getString("summonerName");
		this.level = to.getDouble("level");
		this.profileIconId = to.getInt("profileIconId");
		this.gameId = to.getDouble("gameId");
		this.skinName = to.getString("skinName");
		this.elo = to.getInt("elo");
		this.leaver = to.getBool("leaver");
		this.leaves = to.getDouble("leaves");
		this.teamId = to.getDouble("teamId");
		this.eloChange = to.getInt("eloChange");
		this.botPlayer = to.getBool("botPlayer");
		this.spell1Id = to.getDouble("spell1Id");
		this.spell2Id = to.getDouble("spell2Id");
		this.wins = to.getDouble("wins");
		this.losses = to.getDouble("losses");
		
		statistics = new ArrayList<RawStatDTO>();
		Object[] stats = to.getArray("statistics");
		if (stats != null) {
			for (Object o : stats) {
				TypedObject ts = (TypedObject)o;
				if (ts != null) {
					statistics.add(new RawStatDTO(ts));
				}
			}
		}
	}

	public Double getUserId() {
		return userId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public Double getLevel() {
		return level;
	}

	public Integer getProfileIconId() {
		return profileIconId;
	}

	public Double getGameId() {
		return gameId;
	}

	public String getSkinName() {
		return skinName;
	}

	public Integer getElo() {
		return elo;
	}

	public Boolean getLeaver() {
		return leaver;
	}

	public Double getLeaves() {
		return leaves;
	}

	public Double getTeamId() {
		return teamId;
	}

	public Integer getEloChange() {
		return eloChange;
	}

	public List<RawStatDTO> getStatistics() {
		return Collections.unmodifiableList(statistics);
	}

	public Boolean getBotPlayer() {
		return botPlayer;
	}

	public Double getSpell1Id() {
		return spell1Id;
	}

	public Double getSpell2Id() {
		return spell2Id;
	}

	public Double getWins() {
		return wins;
	}

	public Double getLosses() {
		return losses;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerParticipantStatsSummary [userId=");
		builder.append(userId);
		builder.append(", summonerName=");
		builder.append(summonerName);
		builder.append(", level=");
		builder.append(level);
		builder.append(", profileIconId=");
		builder.append(profileIconId);
		builder.append(", gameId=");
		builder.append(gameId);
		builder.append(", skinName=");
		builder.append(skinName);
		builder.append(", elo=");
		builder.append(elo);
		builder.append(", leaver=");
		builder.append(leaver);
		builder.append(", leaves=");
		builder.append(leaves);
		builder.append(", teamId=");
		builder.append(teamId);
		builder.append(", eloChange=");
		builder.append(eloChange);
		builder.append(", statistics=");
		builder.append(statistics);
		builder.append(", botPlayer=");
		builder.append(botPlayer);
		builder.append(", spell1Id=");
		builder.append(spell1Id);
		builder.append(", spell2Id=");
		builder.append(spell2Id);
		builder.append(", wins=");
		builder.append(wins);
		builder.append(", losses=");
		builder.append(losses);
		builder.append("]");
		return builder.toString();
	}

}
