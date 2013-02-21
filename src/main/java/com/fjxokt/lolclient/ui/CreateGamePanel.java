package com.fjxokt.lolclient.ui;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fjxokt.lolclient.lolrtmps.exceptions.PlayerAlreadyInGameException;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameMapEnabledDTO;
import com.fjxokt.lolclient.lolrtmps.model.ClientSystemStatesNotification;
import com.fjxokt.lolclient.lolrtmps.model.GameMap;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameModeTypeConfig;
import com.fjxokt.lolclient.lolrtmps.model.utils.Map;
import com.fjxokt.lolclient.lolrtmps.model.utils.SpectatorsAllowed;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

public class CreateGamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static LoLClient client = LoLClient.getInst();

	public CreateGamePanel(final JPanel cards) {
		super(new GridLayout(0, 2));
		
		ClientSystemStatesNotification states = client.getLoginDataPacketForUser().getClientSystemStates();
		List<GameMapEnabledDTO> gameMapEnabledDTOList = states.getGameMapEnabledDTOList();
		final Integer minPlayers = states.getMinNumPlayersForPracticeGame();
		
		// team size (will be added to cintainer after the game map)
		JLabel teamSizeLb = new JLabel("Team size: ", JLabel.TRAILING);		
		DefaultComboBoxModel model2 = new DefaultComboBoxModel();
		final JComboBox teamSizeCb = new JComboBox(model2);
		teamSizeCb.setSelectedIndex(teamSizeCb.getItemCount()-1);
		
		// game map
		JLabel gameMapLb = new JLabel("Game map: ", JLabel.TRAILING);
		add(gameMapLb);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (GameMapEnabledDTO g : gameMapEnabledDTOList) {
			model.addElement(Map.getMapFromId(g.getGameMapId()));
		}
		final JComboBox gameMapCb = new JComboBox(model);
		gameMapCb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Map m = (Map)gameMapCb.getSelectedItem();
				if (m != null) {
					// when a map is selected we update the max nb of players for it
					DefaultComboBoxModel model = (DefaultComboBoxModel)teamSizeCb.getModel();
					model.removeAllElements();
					int imax = m.getMaxPlayers() / 2;
					for (int i=imax; i>0; i--) {
						model.addElement(i);
					}
					
				}
			}
		});
		gameMapCb.setSelectedIndex(0);
		add(gameMapCb);

		add(teamSizeLb);
		add(teamSizeCb);

		// spectator type
		JLabel specLb = new JLabel("Allow spectators: ", JLabel.TRAILING);
		add(specLb);
		model2 = new DefaultComboBoxModel(SpectatorsAllowed.values());
		final JComboBox specCb = new JComboBox(model2);
		specCb.setSelectedIndex(0);
		add(specCb);
		
		// game type
		JLabel gameTypeLb = new JLabel("Game type: ", JLabel.TRAILING);
		add(gameTypeLb);
		JComboBox gameTypeCb = new JComboBox(GameModeTypeConfig.values());
		add(gameTypeCb);
		
		// game's name
		JLabel gameNameLb = new JLabel("Name: ", JLabel.TRAILING);
		add(gameNameLb);
		String sumName = (client.getMySummoner() == null) ? "" : client.getMySummoner().getName();
		final JTextField gameNameTf = new JTextField(sumName + "'s game");
		add(gameNameTf);
		
		// password
		JLabel pwdLb = new JLabel("Password (Optional): ", JLabel.TRAILING);
		add(pwdLb);
		final JPasswordField pwdTf = new JPasswordField();
		add(pwdTf);

		// back to lobby
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "LOBBY_PANEL");
			}
		});
		add(quit);
		
		// create the game
		JButton create = new JButton("Create game");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// get our data
					int maxPlayers = ((Integer)teamSizeCb.getSelectedItem()) * 2;
					String gameName = gameNameTf.getText();
					Map selMap = (Map)gameMapCb.getSelectedItem();
					String pwd = new String(pwdTf.getPassword());
					String specAllow = ((SpectatorsAllowed)specCb.getSelectedItem()).getName();
					// create game (if it worked, the event will be caught by the Lobby that will then create the team select room)
					client.createPracticeGame(gameName, selMap.getGameMode().getName(), 
							GameMap.createDummy(selMap.getMapId(), maxPlayers, minPlayers), 
							maxPlayers, minPlayers, pwd,specAllow);
				} catch (PlayerAlreadyInGameException e) {
					e.printStackTrace();
				}
			}
		});
		add(create);
	}

}
