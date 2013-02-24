package com.fjxokt.lolclient.ui;

import com.fjxokt.lolclient.ResourceConstants;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.services.GameService;
import com.fjxokt.lolclient.utils.ResourcesManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JoinMatchPopup extends JFrame implements ClientListener {
	private static final long serialVersionUID = 1L;
	
	private StatusPanel statusUsers;
	
	private class StatusPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel[] guys;
		StatusPanel() {
			super(new FlowLayout(FlowLayout.CENTER));
			
		}
		public void update(String status) {
			if (guys == null) {
				guys = new JLabel[status.length()];
				for (int i=0; i<status.length(); i++) {
					guys[i] = new JLabel();
					add(guys[i]);
				}
			}
			for (int i=0; i<status.length(); i++) {
				JLabel guy = guys[i];
				if (status.charAt(i) == '0') {
					guy.setIcon(ResourcesManager.getInst().getIcon(ResourceConstants.genericClientImagesPath + "guy_blue.png", false));
				}
				else if (status.charAt(i) == '1') {
					guy.setIcon(ResourcesManager.getInst().getIcon(ResourceConstants.genericClientImagesPath +  "guy_green.png", false));
				}
				else {
					guy.setIcon(ResourcesManager.getInst().getIcon(ResourceConstants.genericClientImagesPath +  "guy_red.png", false));
				}
			}
			revalidate();
		}
	}
	
	public JoinMatchPopup() {
		this.setTitle("Game found!");
        this.setPreferredSize(new Dimension(320, 130));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel textPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPan.add(new JLabel("A game has been found, let's go ?"));
        
        statusUsers = new StatusPanel();
        statusUsers.setBorder(new EmptyBorder(0, 0, 10, 0));
        statusUsers.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPan.add(statusUsers);
        
        mainPanel.add(textPan);
        
        JPanel buttons = new JPanel();
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameService.acceptPoppedGame(LoLClient.getInst().getRTMPSClient(), true);
			}
		});
        buttons.add(ok);
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameService.acceptPoppedGame(LoLClient.getInst().getRTMPSClient(), false);
			}
		});
        buttons.add(cancel);
        
        mainPanel.add(buttons);
        this.setContentPane(mainPanel);
        
        // create the client listener
        LoLClient.getInst().addGameListener(JoinMatchPopup.this);
        
        this.pack();
        this.setVisible(true);
	}
	
	public void clientStateUpdated(ClientEvent e) {
		switch (e.getType()) {
		case MATCHMAKING_UPDATE:
			statusUsers.update(e.getGame().getStatusOfParticipants());
			break;
		case RETURNING_LOBBY:
		case JOINING_CHAMP_SELECT:
			LoLClient.getInst().removeGameListener(JoinMatchPopup.this);
			this.dispose();
			break;
		}
	}
}