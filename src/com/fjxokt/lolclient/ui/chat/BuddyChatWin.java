package com.fjxokt.lolclient.ui.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;

import com.fjxokt.lolclient.messaging.MessagingManager;

public class BuddyChatWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea chat;
	private JTextField field;

	public BuddyChatWin(final RosterEntry buddy) {
		super("Chat with " + buddy.getName());
	    setResizable(true);
	    setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	    getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
	    
		chat = new JTextArea();
		chat.setLineWrap(true);
		chat.setEditable(false);
		JScrollPane scroll = new JScrollPane(chat);
		add(scroll, BorderLayout.CENTER);
		
		JPanel pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		field = new JTextField("");
		field.setPreferredSize(new Dimension(205, 25));
		field.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage(buddy);
					field.requestFocus();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage(buddy);
				field.requestFocus();
			}
		});
		pan.add(field);
		pan.add(send);
		
		add(pan, BorderLayout.AFTER_LAST_LINE);
				
		setPreferredSize(new Dimension(300, 200));
	    
        pack();
        field.requestFocusInWindow();
        setVisible(true);  
	}
	
	public void sendMessage(RosterEntry buddy) {
		// do not send empty strings
		if (field.getText().isEmpty()) {
			return;
		}
		try {
			MessagingManager.getInst().sendMessage(field.getText(), buddy.getUser());
			addMessage(formatMessage(MessagingManager.getInst().getMyName(), field.getText()));
			field.setText("");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	public void getAnswer(String user, String msg) {
		addMessage(formatMessage(user, msg));
		setVisible(true);
		requestFocus();
	}
	
	private void addMessage(String message) {
		chat.setText(chat.getText() + message + "\n");
	}
	
	private String formatMessage(String user, String message) {
		return getTime() + " " + user + " : " + message;
	}
	
	private String getTime() {
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    	return sdf.format(cal.getTime());
	}

}
