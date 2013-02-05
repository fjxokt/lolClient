package com.fjxokt.lolclient.ui.champsel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fjxokt.lolclient.utils.ResourcesManager;

public class SellSpellsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton bspell1;
	private JButton bspell2;
	
	private int iconSize = 35;
	
	public SellSpellsPanel(int spell1, int spell2) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("Spells");
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel titlePan = new JPanel(new BorderLayout());
		titlePan.add(title);
		add(titlePan);
		
		JPanel iconsPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		String path1 = ChampSelectWin.pathSumSpellPic + "spell" + spell1 + ".png";
		String path2 = ChampSelectWin.pathSumSpellPic + "spell" + spell2 + ".png";
		bspell1 = new JButton(ResourcesManager.getInst().getIcon(path1, false, iconSize, iconSize));
		bspell1.setPreferredSize(new Dimension(iconSize + 5, iconSize + 5));
		bspell2 = new JButton(ResourcesManager.getInst().getIcon(path2, false, iconSize, iconSize));
		bspell2.setPreferredSize(new Dimension(iconSize + 5, iconSize + 5));
		iconsPan.add(bspell1);
		iconsPan.add(bspell2);
		
		add(iconsPan);
	}
}