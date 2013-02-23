package com.fjxokt.lolclient.ui.champsel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionSkinDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerChampionSelectionDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.services.GameService;

public class SellChampionSkinPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JComboBox skins;
	
	private LoLClient client = LoLClient.getInst();
	
	public SellChampionSkinPanel() {
		super(new GridLayout(1,2));
		
		add(new JLabel("Champion skins: ", JLabel.TRAILING));
		skins = new JComboBox();
		skins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the selected champ if we have one
				PlayerChampionSelectionDTO mychamp = client.getMyPlayer();
				if (mychamp != null) {
					ChampionSkinDTO skin = (ChampionSkinDTO)skins.getSelectedItem();
					if (skin != null) {
						ResultMessage res = GameService.selectChampionSkin(LoLClient.getInst().getRTMPSClient(), skin.getChampionId(), skin.getSkinId());
						if (res.equals(ResultMessage.ERROR)) {
							System.out.println("Could not select skin " + skin);
						}
						else {
							System.out.println("selected skin " + skin.getSkinId() + " for champ " + skin.getChampionId());
						}
						// NO EVENT IS SEND WHEN CHANGING SKIN, I HAVE TO MANAGE THIS MYSELF
					}
				}
			}
		});
		add(skins);
	}
	
	public void update() {
		// prepare combobox model
		DefaultComboBoxModel model = null;
		// get the selected champ if we have one
		PlayerChampionSelectionDTO mychamp = client.getMyPlayer();
		if (mychamp != null && !mychamp.getChampionId().equals(0)) {
			System.out.println("I selected a champ, should see a skin update !!! : " + mychamp);
			List<ChampionSkinDTO> skinsForChampion = client.getSkinsForChampion(client.getMyPlayer().getChampionId());
			for (ChampionSkinDTO ski : skinsForChampion) {
				System.out.println(ski);
			}
			// create and assign the model to the combobox
			model = new DefaultComboBoxModel(skinsForChampion.toArray());
			skins.setModel(model);
			// find the current selected skin
			ChampionSkinDTO selSkin = null;
			for (ChampionSkinDTO skin : skinsForChampion) {
				if (skin.getSkinId().equals(mychamp.getSelectedSkinIndex())) {
					selSkin = skin;
					break;
				}
			}
			skins.setSelectedItem(selSkin);
		}
		else {
			model = new DefaultComboBoxModel();
			skins.setModel(model);
		}
	}
	
}
