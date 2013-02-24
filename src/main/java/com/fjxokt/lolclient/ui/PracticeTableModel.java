package com.fjxokt.lolclient.ui;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.lolrtmps.model.utils.Map;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PracticeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final String[] colNames = {"lock", "Name", "Owner", "Slots", "Specs", "Map"};
	
	private List<PracticeGameSearchResult> originalGames;
	private List<PracticeGameSearchResult> games;
	private String filter = null;
	private boolean showPrivate = false;
	private Integer maxSpecs = LoLClient.getInst().getLoginDataPacketForUser().getClientSystemStates().getSpectatorSlotLimit();
	
	public PracticeTableModel(List<PracticeGameSearchResult> games) {
		this.games = games;
		this.originalGames = games;
		filter(filter, showPrivate);
	}
	
	public void updateList(List<PracticeGameSearchResult> games) {
		this.games = games;
		this.originalGames = games;
		filter(filter, showPrivate);
	}
	
    @Override
	public String getColumnName(int col) {
        return colNames[col];
    }
	
	public PracticeGameSearchResult getRow(int row) {
		return games.get(row);
	}
	
	public void filter(String filter) {
		filter(filter, showPrivate);
	}
	
	public void filter(boolean showPrivateGames) {
		filter(filter, showPrivateGames);
	}
	
	public void filter(String filter, boolean showPrivateGames) {
		this.filter = filter;
		this.showPrivate = showPrivateGames;
		if ((filter == null || filter.isEmpty())
				&& showPrivateGames) {
			games = originalGames;
		}
		else {
			games = new ArrayList<PracticeGameSearchResult>();
			for (PracticeGameSearchResult pr : originalGames) {
				// if we don't want priv games we skip them
				if (pr.isPrivateGame() && !showPrivateGames) {
					continue;
				}
				// if no filter we add the game
				if (filter == null) {
					games.add(pr);
					continue;
				}
				// if our filter match we add it
				if (pr.getName().toLowerCase().contains(filter)) {
					games.add(pr);
				}
			}
		}
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public int getRowCount() {
		return games.size();
	}

	public Object getValueAt(int row, int col) {
		PracticeGameSearchResult g = games.get(row);
		switch (col) {
		case 0:
			return g.isPrivateGame() ? " X" : "";
		case 1:
			return g.getName();
		case 2:
			return g.getOwner().getSummonerName();
		case 3:
			return "" + g.getNumPlayers() + "/" + g.getMaxNumPlayers();
		case 4:
			return g.getSpectatorCount() + "/" + maxSpecs;
		case 5:
			return Map.getMapFromId(g.getGameMapId()).getMapName();
		
		}
		return null;
	}

}
