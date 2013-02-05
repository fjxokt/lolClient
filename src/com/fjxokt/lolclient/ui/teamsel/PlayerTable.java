package com.fjxokt.lolclient.ui.teamsel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerChampionSelectionDTO;
import com.fjxokt.lolclient.lolrtmps.model.BotParticipant;
import com.fjxokt.lolclient.lolrtmps.model.Participant;
import com.fjxokt.lolclient.lolrtmps.model.PlayerParticipant;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

public class PlayerTable extends JTable implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	///////////////
	// table model
	///////////////
	
	public class PlayerModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private List<Participant> players;
		public PlayerModel(List<Participant> players) {
			super();
			update(players);
		}
		@Override
		public int getColumnCount() {
			return 1;
		}
		@Override
		public int getRowCount() {
			return players.size();
		}
		@Override
		public Object getValueAt(int row, int col) {
			return players.get(row);
		}
		public void update(List<Participant> players) {
			this.players = players;	
			fireTableDataChanged();
		}
		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}
	}
	
	///////////////
	// table cell
	///////////////
	
	private class PlayerCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		private final JPanel pan = new JPanel(new BorderLayout());
		private final JButton kick = new JButton("kick");
		private final JLabel playerLb = new JLabel("");
		private Participant player;
		
		public PlayerCellRenderer(final JTable table) {
			playerLb.setFont(getFont().deriveFont(18.0f));
			pan.add(playerLb, BorderLayout.CENTER);
			JPanel p = new JPanel(new FlowLayout());
			kick.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							final LoLClient client = LoLClient.getInst();
							if (player instanceof PlayerParticipant) {
								// get the summoner id from his name
								final PublicSummoner summoner = client.getSummonerByName(playerLb.getText());
								System.out.println("Ban user: " + playerLb.getText() + " id: " + summoner.getSummonerId());
								// ban !
								client.banUserFromGame(client.getCurrentGame().getId(), summoner.getAcctId());
							}
							else {
								BotParticipant bot = (BotParticipant)player;
								List<PlayerChampionSelectionDTO> champs = client.getCurrentGame().getPlayerChampionSelections();
								for (PlayerChampionSelectionDTO champ : champs) {
									System.out.println("CHAMP: " + champ);
									if (champ.getSummonerInternalName().equals(bot.getSummonerInternalName())) {
										System.out.println("ban bot: " + client.removeBotChampion(champ.getChampionId(), bot));
										break;
									}
								}
							}
							
						}
					});
				}
			});
			p.add(kick);
			pan.add(p, BorderLayout.AFTER_LINE_ENDS);
		}

		private Component prepareCell(Object value) {
			// get player name
			player = (Participant)value;
			playerLb.setText(player.getSummonerName());
			
			String myName = LoLClient.getInst().getMySummoner().getInternalName();
			String ownerName = LoLClient.getInst().getCurrentGame().getOwnerSummary().getSummonerInternalName();
			// I can't kick if I'm not the game owner and I can't kick myself !
			if (myName.equals(ownerName) && !myName.equals(player.getSummonerInternalName())) {
				kick.setVisible(true);
			}
			else {
				kick.setVisible(false);
			}
			return pan;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable arg0,
				Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
			return prepareCell(value);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean arg2, int arg3, int arg4) {
			return prepareCell(value);
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}
	}
	
	///////////////
	// table
	///////////////
	
	public PlayerTable() {
		super();
		setModel(new PlayerModel(new ArrayList<Participant>()));
		getColumnModel().getColumn(0).setCellRenderer(new PlayerCellRenderer(this));
		getColumnModel().getColumn(0).setCellEditor(new PlayerCellRenderer(this));
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		setRowHeight(35);
		addMouseMotionListener(this);
	}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		java.awt.Point p = e.getPoint();
        int row = rowAtPoint(p);
		editCellAt(row, 0);
	}
}