package com.fjxokt.lolclient.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

<<<<<<< HEAD
import org.jivesoftware.smack.packet.Presence;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.messaging.ChatListener;
=======
import com.fjxokt.lolclient.audio.AudioManager;
import com.fjxokt.lolclient.audio.Sounds;
import com.fjxokt.lolclient.messaging.MessagesListener;
>>>>>>> parent of 45c7aed... Changes on the messaging part
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.fjxokt.lolclient.ui.chat.ChatPresenceType;

public class TeamChatPanel extends JPanel implements MessagesListener {

	private static final long serialVersionUID = 1L;
	
	private JTextField field;
	private JTextArea chat;
	
	public TeamChatPanel() {
		super(new BorderLayout(0, 5));

		// chat textarea
		JPanel chatPan = new JPanel(new BorderLayout());
		
		// label
		JLabel jLabel = new JLabel("Chat");
		jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		chatPan.add(new JPanel(new FlowLayout(FlowLayout.LEFT)).add(jLabel), BorderLayout.BEFORE_FIRST_LINE);
		
		chat = new JTextArea();
		chat.setEditable(false);
		chat.setLineWrap(true);
		
		// use this panel to display the text from bottom to top
		JPanel panel = new JPanel(new BorderLayout());  
        panel.setBackground(chat.getBackground());  
        panel.setBorder(chat.getBorder());  
        chat.setBorder(null);  
        panel.add(chat, BorderLayout.SOUTH);
        
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setPreferredSize(new Dimension(300, 100));
		chatPan.add(scroll, BorderLayout.CENTER);
		
		add(chatPan, BorderLayout.CENTER);
		
		// text pan
		JPanel fieldPan = new JPanel(new BorderLayout(5, 0));
		field = new JTextField();
		// user press enter
		field.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		fieldPan.add(field, BorderLayout.CENTER);
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		fieldPan.add(send, BorderLayout.AFTER_LINE_ENDS);
		add(fieldPan, BorderLayout.AFTER_LAST_LINE);
		
		// add messages listener
		MessagingManager.getInst().addMessagesListener(this);
	}
	
	public void sendMessage() {
		if (field.getText().isEmpty()) {
			return;
		}
		GameDTO currentGame = LoLClient.getInst().getCurrentGame();
		MessagingManager.getInst().sendMessage(currentGame, field.getText());
		field.setText("");
	}

	@Override
<<<<<<< HEAD
	public void gameMessageReceived(GameDTO game, String user, String message) {
=======
	public void messageReceived(GameDTO game, String user, String message) {
		if (message.startsWith("<body>") && message.endsWith("</body>")) {
			return;
		}
		AudioManager.getInst().playSound(Sounds.MESSAGE_RECEIVED);
>>>>>>> parent of 45c7aed... Changes on the messaging part
		chat.setText(chat.getText() + "[" + user + "] : " + message + "\n");
		chat.getCaret().setDot(chat.getDocument().getLength());
	}

<<<<<<< HEAD
	@Override
	public void buddyMessageReceived(String userId, String message) {
		// nothing to do here
	}
	@Override
	public void buddyPresenceChanged(String userId, Presence presence, ChatPresenceType type) {
		// nothing to do here		
	}

=======
>>>>>>> parent of 45c7aed... Changes on the messaging part
}
