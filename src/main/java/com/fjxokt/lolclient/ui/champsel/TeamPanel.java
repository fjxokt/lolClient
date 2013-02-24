package com.fjxokt.lolclient.ui.champsel;

import com.fjxokt.lolclient.ResourceConstants;
import com.fjxokt.lolclient.lolrtmps.model.Participant;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerChampionSelectionDTO;
import com.fjxokt.lolclient.model.Champion;
import com.fjxokt.lolclient.utils.ResourcesManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class TeamPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JList list;
	
	private class ChampSelectCellRenderer extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = 1L;
		
		private JPanel mainPan;
		private JLabel sumName;
		private JButton chmpI;
		private JLabel spell1;
		private JLabel spell2;

		ChampSelectCellRenderer() {
			setOpaque(true);
			mainPan = new JPanel();
			mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
			
			sumName = new JLabel("");
			sumName.setAlignmentX(Component.LEFT_ALIGNMENT);
			JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
			top.add(sumName);
			mainPan.add(top);
			
			JPanel iconsPanel = new JPanel(new GridLayout(1, 2));
			
			chmpI = new JButton();
			chmpI.setBorder(BorderFactory.createEmptyBorder());
			iconsPanel.add(chmpI);
			
			JPanel spellsPan = new JPanel(new GridLayout(2, 1));
			spell1 = new JLabel("", JLabel.CENTER);
			spell1.setBorder(BorderFactory.createEmptyBorder());
			spell2 = new JLabel("", JLabel.CENTER);
			spell2.setBorder(BorderFactory.createEmptyBorder());
			spellsPan.add(spell1);
			spellsPan.add(spell2);
			
			iconsPanel.add(spellsPan);
			
			mainPan.add(iconsPanel);
		}
		
		public Component getListCellRendererComponent(JList list,Object value,
	                      int index,boolean isSelected,boolean cellHasFocus)
		{
			ChampSelection res = (ChampSelection)value;
			
			sumName.setText(res.getName());
			
			Champion champ = ResourcesManager.getInst().getChampion(res.getChampId());
			
			String champIconPath = ResourceConstants.champIconPath + champ.getIconPath();
			chmpI.setIcon(ResourcesManager.getInst().getIcon(champIconPath, false, 60, 60));	
			String path1 = ResourceConstants.spellIconPath + "spell" + res.getSpell1() + ".png";
			spell1.setIcon(ResourcesManager.getInst().getIcon(path1, false, 30, 30));
			String path2 = ResourceConstants.spellIconPath + "spell" + res.getSpell2() + ".png";
			spell2.setIcon(ResourcesManager.getInst().getIcon(path2, false, 30, 30));

			return mainPan;
		}
	}
	
	
	public TeamPanel(int teamSize, GameDTO game, List<Participant> players) {
		super();
		setOpaque(false);
		
		list = new JList();
		list.setVisibleRowCount(teamSize);
		list.setOpaque(false);
		list.setCellRenderer(new ChampSelectCellRenderer());
		list.setFixedCellHeight(90);
		list.setFixedCellWidth(130);
		updateGame(game, players);

		JScrollPane scroll = new JScrollPane(list);
//		scroll.setMinimumSize(new Dimension(160, scroll.getPreferredSize().height));
		
		add(scroll, BorderLayout.CENTER);
	}
	
	public void updateGame(GameDTO game, List<Participant> players) {
		List<PlayerChampionSelectionDTO> champs = game.getPlayerChampionSelections();
		DefaultListModel model = new DefaultListModel();
		
		for (Participant id : players) {
			PlayerChampionSelectionDTO infoChamp = null;
			for (PlayerChampionSelectionDTO pc : champs) {
				if (pc.getSummonerInternalName().equals(id.getSummonerInternalName())) {
					infoChamp = pc;
					break;
				}
			}
			if (infoChamp == null) {
				ChampSelection champ = new ChampSelection(id.getSummonerName(), -1, -1, 0);
				model.addElement(champ);
			}
			else {
				ChampSelection champ = null;
				if (infoChamp.getChampionId() == 0) {
					champ = new ChampSelection(id.getSummonerName(), -1, -1, 0);
				}
				else {
					champ = new ChampSelection(id.getSummonerName(),
							infoChamp.getSpell1Id(), infoChamp.getSpell2Id(), infoChamp.getChampionId());
				}
				model.addElement(champ);
			}
		}
		list.setModel(model);
	}
	
}
