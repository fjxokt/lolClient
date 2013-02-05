package com.fjxokt.lolclient.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fjxokt.lolclient.lolrtmps.model.EndOfGameStats;

public class EndOfGameStatsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextArea text;
	
	public EndOfGameStatsPanel(final JPanel cards) {
		super(new BorderLayout());
				
		Box box = new Box(BoxLayout.Y_AXIS);
		box.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		JLabel l = new JLabel("End of game stats");
		l.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.add(l);
		
		pan.add(Box.createVerticalStrut(10));
		
		text = new JTextArea();
		text.setLineWrap(true);
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		JScrollPane scroll = new JScrollPane(text);
		pan.add(scroll);
		
		box.add(Box.createVerticalGlue());
		box.add(pan);
		box.add(Box.createVerticalGlue());

		this.add(box, BorderLayout.CENTER);
		
		JButton close = new JButton("Close stats");
		close.setAlignmentX(Component.CENTER_ALIGNMENT);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "LOBBY_PANEL");
			}
		});
		
		this.add(close, BorderLayout.AFTER_LAST_LINE);
	}
	
	public void update(EndOfGameStats stats) {
		text.setText(stats.toString());
	}

}
