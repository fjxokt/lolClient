package com.fjxokt.lolclient.ui.champsel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.fjxokt.lolclient.ResourceConstants;
import com.fjxokt.lolclient.audio.JSound;
import com.fjxokt.lolclient.model.Champion;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.services.GameService;
import com.fjxokt.lolclient.utils.ResourcesManager;

public class SelectChampPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JList list;
	private DefaultListModel listModel;
	
	private LoLClient client = LoLClient.getInst();
	
	private class ChampionIconListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
			ChampionDTO champ = (ChampionDTO)value;
			Champion champion = ResourcesManager.getInst().getChampion(champ.getChampionId());
			
			String filePath = ResourceConstants.champIconPath + champion.getIconPath();
			ImageIcon img = ResourcesManager.getInst().getIcon(filePath, false, 70, 70);
			label.setIcon(img);
			label.setText("");
			
			if (client.isChampionSelected(client.getCurrentGame(), champ.getChampionId())) {
				label.setEnabled(false);
			}
			else {
				label.setEnabled(true);
			}
			
			return label;
		}
	}
	
	public SelectChampPanel(List<ChampionDTO> champs) {
		super(new BorderLayout());
					
		// build list
		listModel = new DefaultListModel();
		for (ChampionDTO champ : champs) {
			listModel.addElement(champ);
		}
		list = new JList(listModel);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new ChampionIconListRenderer());
        list.setBackground(Color.black);
        list.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent e) {
				// allows champ select if not observer
				if (client.isPlayer()) {
					JList list = (JList)e.getSource();
					ChampionDTO champ = (ChampionDTO)list.getSelectedValue();
					Champion c = ResourcesManager.getInst().getChampion(champ.getChampionId());
					if (champ != null) {
						GameService.selectChampion(LoLClient.getInst().getRTMPSClient(), c.getId());
						new JSound(ResourceConstants.soundsChampsPath + c.getSelectSoundPath().replaceAll(ResourceConstants.language, "")).play();
					}
				}
			}
		});
		
		JTextField filter = new JTextField("Filter...");
		add(filter, BorderLayout.BEFORE_FIRST_LINE);
		
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, BorderLayout.CENTER);
	}
	public void refresh() {
		System.out.println("refresh champ sel list");
		list.setModel(list.getModel());
	}
}