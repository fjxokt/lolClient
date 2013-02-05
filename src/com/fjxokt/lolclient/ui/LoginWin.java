package com.fjxokt.lolclient.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fjxokt.lolclient.Constants;
import com.fjxokt.lolclient.dao.QueryManager;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

public class LoginWin extends JFrame implements ActionListener, ClientListener {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JTextField username;
	private JPasswordField password;
	private JComboBox region;
	private JButton login;
	private JLabel status;
	
	private LoLClient client = LoLClient.getInst();
	
	private boolean newSummoner;
	private boolean gameInProgress;
	
	public LoginWin() {
		
		this.setTitle("NoAir pvp.net - Login");
        this.setSize(250, 230);
        this.setPreferredSize(new Dimension(250, 230));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        mainPanel = new JPanel();
        this.setContentPane(mainPanel);
        
        mainPanel.add(new JLabel("Username: "));
        username = new JTextField("fjxokttest", 15);
        username.addKeyListener(new KeyEnterListener());
        mainPanel.add(username);
        
        mainPanel.add(new JLabel("Password: "));
        password = new JPasswordField("fjxokttest0", 15);
        password.addKeyListener(new KeyEnterListener());
        mainPanel.add(password);
        
        mainPanel.add(new JLabel("    Region: "));
        region = new JComboBox(new Object[]{"NA", "EUW", "EUNE", "PBE"});
        mainPanel.add(region);
        
        login = new JButton("Login");
        login.setPreferredSize(new Dimension(140, 30));
        login.addActionListener(this);
        mainPanel.add(login);
        
        status = new JLabel("");
        mainPanel.add(status);
        
        client.addGameListener(this);
        this.setVisible(true);
        
        // TODO: to remove: used to test if it works
        //QueryManager query = QueryManager.getInst();
        //query.connect("gameStats_en_US.sqlite");
        
       /* List<Champion> champions = LoLDao.getChampions();
        System.out.println("" + champions.size() + " champions fetched");
        for (Champion champ : champions) {
        	System.out.println(champ);
        }*/
        
        //System.out.println(LoLDao.getChampion(101));
        
        //System.out.println(LoLDao.getItem(3005));
        
        /*List<Item> items = LoLDao.getItems();
        System.out.println("" + items.size() + " items fetched");
        for (Item item : items) {
        	System.out.println(item);
        }*/
        
        /*ResultSet rs = query.executeQuery("select * from champions");
    	try {
			while(rs.next()) {
				// read the result set
				System.out.println("name = " + rs.getString("item_name"));
				System.out.println("price = " + rs.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			login();
		}
	}
	
	private class KeyEnterListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				login();
			}
		}
		public void keyPressed(KeyEvent e) {}
	}
	
	private void login() {
		// TODO: fetch correct version number from game data
		String clientVersion = "3.01.13_01_31_12_31";
		// "1.74.13_01_15_19_05"
		client.initUser((String)region.getSelectedItem(), clientVersion, username.getText(), new String(password.getPassword()));
		
		login.setText("Logging in...");
		status.setText("");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ResultMessage res = LoLClient.getInst().login();
				if (res.getType().equals(ResultMessage.Type.OK)) {
					// load DB
					QueryManager query = QueryManager.getInst();
			        query.load(Constants.sqlFile);
					// run the main win
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ClientWin(newSummoner, gameInProgress);
						}
					});
					// remove listener
					LoLClient.getInst().removeGameListener(LoginWin.this);
					LoginWin.this.dispose();
				}
				else {
					login.setText("Login");
					status.setText(res.getMessage());
				}
			}
		});
	}

	@Override
	public void clientStateUpdated(ClientEvent e) {
		if (e.getType().equals(ClientEventType.LOGIN_NEW_SUMMONER)) {
			newSummoner = true;
		}
		else if (e.getType().equals(ClientEventType.GAME_IN_PROGRESS)) {
			gameInProgress = true;
		}
	}

}
