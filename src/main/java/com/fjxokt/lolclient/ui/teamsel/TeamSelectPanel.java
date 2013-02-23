package com.fjxokt.lolclient.ui.teamsel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.StartChampSelectDTO;
import com.fjxokt.lolclient.lolrtmps.model.GameObserver;
import com.fjxokt.lolclient.lolrtmps.model.Participant;
import com.fjxokt.lolclient.lolrtmps.model.utils.SpectatorsAllowed;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.services.GameService;
import com.fjxokt.lolclient.ui.TeamChatPanel;

public class TeamSelectPanel extends JPanel implements ActionListener, ClientListener {

	private static final long serialVersionUID = 1L;
	
	private JLabel title;
	private TeamPanel teamOnePan;
	private TeamPanel teamTwoPan;
	private ObsPanel obsPan;
	private TeamChatPanel chatPanel;
	private JButton leave;
	private JButton startChampSel;
	
	private static LoLClient client = LoLClient.getInst();
	
	private class TeamPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int teamId;
		private PlayerTable  teamTable;
		private JButton joinTeam;
		public TeamPanel(final int teamId, String title) {
			super(new BorderLayout());
			this.teamId = teamId;
			this.teamTable = new PlayerTable();
			
			int nbPlayersPerTeam = client.getCurrentGame().getMaxNumPlayers() / 2;
			teamTable.setPreferredSize(new Dimension(300, teamTable.getRowHeight() * nbPlayersPerTeam));
			teamTable.setMinimumSize(new Dimension(300, teamTable.getRowHeight() * nbPlayersPerTeam));
			
			setBorder(new EmptyBorder(10, 10, 10, 10));
			JLabel myTeamLb = new JLabel(title);
			myTeamLb.setFont(myTeamLb.getFont().deriveFont(18.0f));
			myTeamLb.setAlignmentX(Component.CENTER_ALIGNMENT);
			JPanel titlePan = new JPanel();
			titlePan.add(myTeamLb);
			add(titlePan, BorderLayout.BEFORE_FIRST_LINE);
			JPanel centerPan = new JPanel();
			centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.Y_AXIS));
			centerPan.setBorder(new EmptyBorder(5, 10, 5, 5));
			centerPan.add(teamTable);
			centerPan.add(Box.createVerticalStrut(10));
			joinTeam = new JButton("Join team");
			joinTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (client.isObserver()) {
						GameService.switchObserverToPlayer(LoLClient.getInst().getRTMPSClient(), client.getCurrentGame().getId(), teamId);
					}
					else {
						client.switchTeams();
					}
				}
			});
			joinTeam.setAlignmentX(Component.CENTER_ALIGNMENT);
			centerPan.add(joinTeam);
			add(centerPan, BorderLayout.CENTER);
			setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		public void update() {
			// update team members
			List<Participant> team = (teamId == 100) ? client.getCurrentGame().getTeamOne() 
					: client.getCurrentGame().getTeamTwo();
			PlayerTable.PlayerModel model = (PlayerTable.PlayerModel)teamTable.getModel();
			model.update(team);
			// enable or not switch team button (if other team is not full)
			if (client.getMyTeam() != team && teamTable.getModel().getRowCount() < client.getCurrentGame().getMaxNumPlayers() / 2) {
				joinTeam.setEnabled(true);
			}
			else {
				joinTeam.setEnabled(false);
			}
		}
	}
	
	private class ObsPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ObserverTable obsTable;
		private JButton joinObs;
		private int maxObs;
		private boolean obsEnabled;
		public ObsPanel(String title) {
			super(new BorderLayout());
			this.obsTable = new ObserverTable();
			
			SpectatorsAllowed spec = SpectatorsAllowed.getStateFromString(client.getCurrentGame().isSpectatorsAllowed());
			obsEnabled = !spec.equals(SpectatorsAllowed.NONE) && !spec.equals(SpectatorsAllowed.DROPINONLY);
			
			maxObs = client.getLoginDataPacketForUser().getClientSystemStates().getSpectatorSlotLimit();
			obsTable.setPreferredSize(new Dimension(200, obsTable.getRowHeight() * maxObs));
			obsTable.setMinimumSize(new Dimension(200, obsTable.getRowHeight() * maxObs));
			
			JLabel myTeamLb = new JLabel(title);
			myTeamLb.setFont(myTeamLb.getFont().deriveFont(18.0f));
			myTeamLb.setAlignmentX(Component.CENTER_ALIGNMENT);
			JPanel titlePan = new JPanel();
			titlePan.add(myTeamLb);
			add(titlePan, BorderLayout.BEFORE_FIRST_LINE);
			JPanel centerPan = new JPanel();
			centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.Y_AXIS));
			centerPan.setBorder(new EmptyBorder(5, 10, 5, 5));
			centerPan.add(obsTable);
			centerPan.add(Box.createVerticalStrut(10));
			joinObs = new JButton("Join observer");
			joinObs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (client.isPlayer()) {
						GameService.switchPlayerToObserver(LoLClient.getInst().getRTMPSClient(), client.getCurrentGame().getId());
					}
				}
			});
			joinObs.setAlignmentX(Component.CENTER_ALIGNMENT);
			centerPan.add(joinObs);
			add(centerPan, BorderLayout.CENTER);
			setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		public void update() {
			// update team members
			List<GameObserver> obs = client.getCurrentGame().getObservers();
			ObserverTable.ObserverModel model = (ObserverTable.ObserverModel)obsTable.getModel();
			model.update(obs);
			// enable or not switch team button (if other team is not full)
			if (client.isPlayer() && obsTable.getModel().getRowCount() < maxObs) {
				joinObs.setEnabled(true);
			}
			else {
				joinObs.setEnabled(false);
			}
			// if we are an observer
			if (client.isObserver()) {
				joinObs.setEnabled(false);
			}
			// we are a player
			else {
				// if specs is allowed
				if (obsEnabled && client.getCurrentGame().getObservers().size() < maxObs) {
					// we can observe if we're not the last player in the game
					if (client.getCurrentGame().getNumPlayers() > 1) {
						joinObs.setEnabled(true);					
					}
					else {
						joinObs.setEnabled(false);					
					}
				}
				else {
					joinObs.setEnabled(false);
				}
			}
		}
	}
	
	public TeamSelectPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		// game title
		JPanel titlePan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
		title = new JLabel();
		titlePan.add(title);
		this.add(titlePan, BorderLayout.BEFORE_FIRST_LINE);
		
		// center panel
		JPanel centerPan = new JPanel();
		centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.Y_AXIS));
		GridLayout grid = new GridLayout(1, 2);
		grid.setHgap(10);
		JPanel listsPan = new JPanel(grid);
		
		JPanel gridPan = new JPanel(new GridBagLayout());
		gridPan.setBorder(new EmptyBorder(0, 10, 0, 10));
		GridBagConstraints c = new GridBagConstraints();
		
		// panel team 1
		teamOnePan = new TeamPanel(100, "Team One");
		listsPan.add(teamOnePan);
		
		// panel team 2
		teamTwoPan = new TeamPanel(200, "Team Two");
		listsPan.add(teamTwoPan);
		
		// add listPan to grid
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.5;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.insets = new Insets(0,0,10,0);
		gridPan.add(listsPan, c);

		chatPanel = new TeamChatPanel();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
        c.weightx = 0.75;
        c.weighty = 0.5;
		c.gridwidth = 2;
		c.gridheight = 1;
		gridPan.add(chatPanel, c);
		
		obsPan = new ObsPanel("Observers");
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,5,10,0);
		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.25;
        c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridPan.add(obsPan, c);
		
		centerPan.add(gridPan);
		
		// buttons panel
		JPanel buttonsPan = new JPanel();
		
		leave = new JButton("Leave team select");
		leave.addActionListener(this);
		buttonsPan.add(leave);
//		
		startChampSel = new JButton("Start champ select");
		startChampSel.addActionListener(this);
		buttonsPan.add(startChampSel);
		
		this.add(centerPan, BorderLayout.CENTER);
		this.add(buttonsPan, BorderLayout.AFTER_LAST_LINE);
		
		// register for events
		LoLClient.getInst().addGameListener(this);
		
		// update
		update();
	}
	
	public void update() {
		// update game info
		title.setText("Game '" + client.getCurrentGame().getName() + "' (owner: " +
								client.getCurrentGame().getOwnerSummary().getSummonerName() + ")");
		
		// update team one
		teamOnePan.update();
		// and team two
		teamTwoPan.update();
		// update obs panel
		obsPan.update();
		
		// if game owner we can start the game
		startChampSel.setEnabled(client.isGameOwner());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == leave) {
			System.out.println("leaving team select: " + GameService.quitGame(LoLClient.getInst()));
		}
		else if (e.getSource() == startChampSel) {
			GameDTO game = client.getCurrentGame();
			StartChampSelectDTO res = GameService.startChampionSelection(LoLClient.getInst().getRTMPSClient(), game.getId(), game.getNumPlayers());
			System.out.println("startchamselect = " + res);
		}
	}

	public void clientStateUpdated(final ClientEvent e) {
		switch (e.getType()) {
		case RETURNING_TEAM_SELECT:
		case TEAM_SELECT_UPDATE:
			update();
			break;
			// this case is handled by the LobbyWin class
//		case JOINING_CHAMP_SELECT:
//			javax.swing.SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					new ChampSelectWin(e.getGame());
//				}
//			});
//			break;
		case BANNED_FROM_GAME:
		case RETURNING_LOBBY:
			LoLClient.getInst().removeGameListener(this);
			break;
		}
	}

}
