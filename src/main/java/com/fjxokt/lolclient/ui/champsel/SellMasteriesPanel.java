package com.fjxokt.lolclient.ui.champsel;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.SpellBookPageDTO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class SellMasteriesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JComboBox runes;
	private JComboBox masteries;
	
	private LoLClient client = LoLClient.getInst();
	
	public SellMasteriesPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("Runes & Masteries");
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel titlePan = new JPanel(new BorderLayout());
		titlePan.add(title);
		add(titlePan);
					
		// spell book
		SpellBookDTO book = client.getSpellBook();
					
		DefaultComboBoxModel model = new DefaultComboBoxModel(book.getBookPages().toArray());
		runes = new JComboBox(model);
		runes.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
                    @Override
			public Component getListCellRendererComponent(JList list,Object value,
                      int index,boolean isSelected,boolean cellHasFocus)
			{
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				SpellBookPageDTO page = (SpellBookPageDTO)value;
				if (page != null) {						
					label.setText(page.getName());
				}
				return label;
			}
		});
		runes.setSelectedItem(client.getDefaultSpellBookPage());
		runes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox b = (JComboBox)e.getSource();
				SpellBookPageDTO p = (SpellBookPageDTO)b.getSelectedItem();
				// if the page is not already active
				if (p != null && !p.getCurrent()) {
					System.out.println("setting rune page: " + client.selectDefaultSpellBookPage(p));
				}
			}
		});
		
		add(runes);
		
		// masteries book
		MasteryBookDTO bookm = client.getMasteryBook();
		System.out.println(bookm);
		
		DefaultComboBoxModel model2 = new DefaultComboBoxModel(bookm.getBookPages().toArray());
		masteries = new JComboBox(model2);
		masteries.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
                    @Override
			public Component getListCellRendererComponent(JList list,Object value,
                      int index,boolean isSelected,boolean cellHasFocus)
			{
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				MasteryBookPageDTO page = (MasteryBookPageDTO)value;
				if (page != null) {						
					label.setText(page.getName());
				}
				return label;
			}
		});
		masteries.setSelectedItem(client.getDefaultMasteryBookPage());
		masteries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox b = (JComboBox)e.getSource();
				MasteryBookPageDTO p = (MasteryBookPageDTO)b.getSelectedItem();
				// if the page is not already active
				if (p != null && !p.getCurrent()) {
					// we have to call saveMAsteyBook with the boolean current set to True to the new one
					System.out.println("setting mastery page: " + client.selectDefaultMasteryBookPage(p));
				}
			}
		});
		
		add(masteries);
	}
	
}
