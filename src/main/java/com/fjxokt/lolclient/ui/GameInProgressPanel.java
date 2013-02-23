package com.fjxokt.lolclient.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.services.GameService;

public class GameInProgressPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel text;
	private JButton decline;
	
	private final LoLClient client = LoLClient.getInst();
	
	public GameInProgressPanel() {
		super(new BorderLayout());
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		text = new JLabel();
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.add(text);
		
		pan.add(Box.createVerticalStrut(15));
		
		JButton reco = new JButton("Reconnect now!");
		reco.setAlignmentX(Component.CENTER_ALIGNMENT);
		reco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: do something
			}
		});
		pan.add(reco);
		
		pan.add(Box.createVerticalStrut(10));
		
		decline = new JButton("Decline reconnect!");
		decline.setAlignmentX(Component.CENTER_ALIGNMENT);
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (LoLClient.getInst().isObserver()) {
					ResultMessage res = GameService.declineObserverReconnect(LoLClient.getInst());
					System.out.println("decline obs reco: " + res);
				}
			}
		});
		pan.add(decline);
		
		box.add(Box.createVerticalGlue());
		box.add(pan);
		box.add(Box.createVerticalGlue());

		this.add(box);
	}
	
	public void update() {
		text.setText("You have a game in progress : " + client.getCurrentGame().getName());
		if (!client.isObserver()) {
			decline.setVisible(false);
		}
	}

}
