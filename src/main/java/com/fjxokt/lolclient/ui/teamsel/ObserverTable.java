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

import com.fjxokt.lolclient.lolrtmps.model.GameObserver;
import com.fjxokt.lolclient.lolrtmps.model.PublicSummoner;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

public class ObserverTable extends JTable implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	///////////////
	// table model
	///////////////
	
	public class ObserverModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private List<GameObserver> obs;
		public ObserverModel(List<GameObserver> obs) {
			super();
			update(obs);
		}
		public int getColumnCount() {
			return 1;
		}
		public int getRowCount() {
			return obs.size();
		}
		public Object getValueAt(int row, int col) {
			return obs.get(row);
		}
		public void update(List<GameObserver> players) {
			this.obs = players;	
			fireTableDataChanged();
		}
		public boolean isCellEditable(int row, int col) {
			return true;
		}
	}
	
	///////////////
	// table cell
	///////////////
	
	private class ObserverCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		private final JPanel pan = new JPanel(new BorderLayout());
		private final JButton kick = new JButton("kick");
		private final JLabel playerLb = new JLabel("");
		
		public ObserverCellRenderer(final JTable table) {
			playerLb.setFont(getFont().deriveFont(16.0f));
			pan.add(playerLb, BorderLayout.CENTER);
			JPanel p = new JPanel(new FlowLayout());
			kick.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							final LoLClient client = LoLClient.getInst();
							// get the summoner id from his name
							final PublicSummoner summoner = client.getSummonerByName(playerLb.getText());
							System.out.println("Ban observer: " + playerLb.getText() + " id: " + summoner.getSummonerId());
							// ban !
							client.banObserverFromGame(client.getCurrentGame().getId(), summoner.getAcctId());							
						}
					});
				}
			});
			p.add(kick);
			pan.add(p, BorderLayout.AFTER_LINE_ENDS);
		}

		private Component prepareCell(Object value) {
			// get player name
			GameObserver obs = (GameObserver)value;
			playerLb.setText(obs.getSummonerName());
			
			String myName = LoLClient.getInst().getMySummoner().getInternalName();
			String ownerName = LoLClient.getInst().getCurrentGame().getOwnerSummary().getSummonerInternalName();
			// I can't kick if I'm not the game owner and I can't kick myself !
			if (myName.equals(ownerName) && !myName.equals(obs.getSummonerInternalName())) {
				kick.setVisible(true);
			}
			else {
				kick.setVisible(false);
			}
			return pan;
		}
		
		public Component getTableCellRendererComponent(JTable arg0,
				Object value, boolean arg2, boolean arg3, int arg4, int arg5) {
			return prepareCell(value);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean arg2, int arg3, int arg4) {
			return prepareCell(value);
		}

		public Object getCellEditorValue() {
			return null;
		}
	}
	
	///////////////
	// table
	///////////////
	
	public ObserverTable() {
		super();
		setModel(new ObserverModel(new ArrayList<GameObserver>()));
		getColumnModel().getColumn(0).setCellRenderer(new ObserverCellRenderer(this));
		getColumnModel().getColumn(0).setCellEditor(new ObserverCellRenderer(this));
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