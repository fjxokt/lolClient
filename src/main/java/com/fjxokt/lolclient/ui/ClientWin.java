package com.fjxokt.lolclient.ui;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.services.LoginService;
import com.fjxokt.lolclient.messaging.MessagingManager;
import com.fjxokt.lolclient.ui.champsel.ChampSelectWin;
import com.fjxokt.lolclient.ui.teamsel.TeamSelectPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientWin extends JFrame implements ClientListener {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel cards;
	private LobbyPanel lobbyPanel;
	private TeamSelectPanel teamSelPanel;
	private CreateGamePanel createGamePanel;
	private GameInProgressPanel recoPanel;
	private EndOfGameStatsPanel statsPanel;
	
	private LoLClient client = LoLClient.getInst();
	
	public ClientWin(boolean newSummoner, boolean gameInProgress) {
		super();
		this.setTitle("NoAir pvp.net - Connected to " + LoginService.getRegion(client.getRTMPSClient()) + " - " +
				client.getLoginDataPacketForUser().getIpBalance().intValue() + " IP / " + 
				client.getLoginDataPacketForUser().getRpBalance().intValue() + " RP");
        this.setSize(750, 600);
        this.setPreferredSize(new Dimension(750, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
        // our cards panel
        cards = new JPanel(new CardLayout());
        
        // add game listener
        client.addGameListener(this);
        
        // lobby panel
        lobbyPanel = new LobbyPanel(cards);
        cards.add(lobbyPanel, "LOBBY_PANEL");
        
        // reco panel
        recoPanel = new GameInProgressPanel();
        if (gameInProgress) {
        	recoPanel.update();
        }
        cards.add(recoPanel, "RECO_PANEL");
        
        statsPanel = new EndOfGameStatsPanel(cards);
        cards.add(statsPanel, "EOG_STATS_PANEL");
        
        createGamePanel = new CreateGamePanel(cards);
        cards.add(createGamePanel, "CREATE_GAME_PANEL");
		
        this.setContentPane(cards);
        this.setVisible(true);
        
        if (newSummoner) {        	
        	System.out.println("Welcome new summoner! Need to manage this!");
        }
        
        MessagingManager.getInst().createInvite("");
        
        // if game in progress show the window
        if (gameInProgress) {
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "RECO_PANEL");	
        }
        else {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        	public void run() {
	                lobbyPanel.refreshPracticeGames();
	        	}
	        });
        }
	}

	public void clientStateUpdated(final ClientEvent e) {
		System.out.println("RECEIVED EVENT of type: " + e.getType());
		switch (e.getType()) {
		case LOGIN_NEW_SUMMONER:
			System.out.println("WE HAVE A NEW SUMMONER!!");
			break;
		case JOINING_TEAM_SELECT:
			if (teamSelPanel == null) {
				teamSelPanel = new TeamSelectPanel();
				cards.add(teamSelPanel, "TEAM_PANEL");
			}
			else {
				cards.remove(teamSelPanel);
				teamSelPanel = new TeamSelectPanel();
				cards.add(teamSelPanel, "TEAM_PANEL");
			}
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "TEAM_PANEL");			
			break;
		case JOINING_CHAMP_SELECT:
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new ChampSelectWin(e.getGame());
				}
			});
			break;
		case BANNED_FROM_GAME:
		case RETURNING_LOBBY:
			cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "LOBBY_PANEL");
			if (e.getType().equals(ClientEventType.BANNED_FROM_GAME)) {
				JOptionPane.showMessageDialog(this, "You have been banned from the game", "Banned!", JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		case GAME_CLIENT_CONNECTED_TO_SERVER:
			cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "RECO_PANEL");
			recoPanel.update();
			break;
		case END_OF_GAME_STATS:
			cl = (CardLayout)(cards.getLayout());
			statsPanel.update(e.getEndOfGameStats());
			cl.show(cards, "EOG_STATS_PANEL");
			break;
		case LOGGED_OUT:
			System.exit(0);
			break;
		}

	}

}
