package com.fjxokt.lolclient.ui.champsel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientEventType;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.dto.ChampionDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.dto.PlayerChampionSelectionDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.ui.TeamChatPanel;
import com.fjxokt.lolclient.utils.Countdown;

public class ChampSelectWin extends JFrame implements ActionListener, ClientListener {

	private static final long serialVersionUID = 1L;
	
//	public static final String pathChampPic = "/Applications/Games/iLoL 2.0 Open Beta.app/Contents/Resources/PvP.net.app/Contents/Resources/assets/storeImages/content/champions/";
//	public static final String pathSumSpellPic = "/Applications/Games/iLoL 2.0 Open Beta.app/Contents/Resources/PvP.net.app/Contents/Resources/assets/images/championScreens/";
	public static final String pathChampPic = "champions/";
	public static final String pathSumSpellPic = "images/spells/";
		
	// components
	private JPanel mainPanel;
	
	private TeamPanel myTeamPanel;
	private TeamPanel otherTeamPanel;
	private SelectChampPanel champSelPanel;
	private SellSpellsPanel sellSpellsPanel;
	private SellChampionSkinPanel sellChampSkinPanel;
	private TeamChatPanel chatPanel;
	
	private JButton lockChampion;
	private JButton leaveGame;
	
	private JLabel count;
	private Countdown countdown;
	
	private static LoLClient client = LoLClient.getInst();
	private boolean isObserver;
	
	
	public ChampSelectWin(GameDTO game) {
		super("Champion Selection");
				
		// register listener
		client.addGameListener(this);
		
		// start our countdown thread
        count = new JLabel("");
        count.setFont(count.getFont().deriveFont(18.0f));
       
        // are we observer ?
        isObserver = client.isObserver();
		
		this.setSize(840, 660);
        this.setPreferredSize(new Dimension(840, 660));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        mainPanel = new JPanel(new BorderLayout());
        
        JPanel centeredBorderLayout = new JPanel(new BorderLayout());
        
        myTeamPanel = new TeamPanel(game.getMaxNumPlayers() / 2, game, isObserver ? client.getCurrentGame().getTeamOne() : client.getMyTeam());
        centeredBorderLayout.add(myTeamPanel, BorderLayout.LINE_START);
        
        otherTeamPanel = new TeamPanel(game.getMaxNumPlayers() / 2, game, isObserver ? client.getCurrentGame().getTeamTwo() : client.getOtherTeam());
        centeredBorderLayout.add(otherTeamPanel, BorderLayout.LINE_END);

        // cham sel panel
        List<ChampionDTO> champs = client.getAvailableChampions(client.getCurrentGame());
        champSelPanel = new SelectChampPanel(champs);
        
        // center panel
        JPanel centerPanel = new JPanel();
        BoxLayout bl = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(bl);
        
        // countdown
        JPanel topPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        count.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPan.add(count);
        centerPanel.add(topPan);
        
        // TABS
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Champions", champSelPanel);
       
        JPanel skinsPan = new JPanel();
        tabbedPane.addTab("Skins", skinsPan);
        
        JPanel tabPan = new JPanel(new BorderLayout());
        tabPan.add(tabbedPane);
        
        centerPanel.add(tabPan);
        
        
        JPanel optionsPan = new JPanel();
        
        // if we're not observer we display these options
        if (!isObserver) {

	        // select champion skins
	        sellChampSkinPanel = new SellChampionSkinPanel();
	        skinsPan.add(sellChampSkinPanel);
	        
	        // select spells and masteries
	        SellMasteriesPanel mpan = new SellMasteriesPanel();
	        optionsPan.add(mpan);
	        optionsPan.add(Box.createHorizontalStrut(10));
	        
	        // select summoner spells
        	PlayerChampionSelectionDTO me = client.getMyPlayer(game);
	        sellSpellsPanel = new SellSpellsPanel(me.getSpell1Id(), me.getSpell2Id());
	        optionsPan.add(sellSpellsPanel);
	        optionsPan.add(Box.createHorizontalStrut(10));
        }
        
        // bottom pan
        JPanel bottomPan = new JPanel(new GridLayout(2, 1, 0, 5));
        
        lockChampion = new JButton("Lock champion");
        lockChampion.setEnabled(!isObserver);
        lockChampion.addActionListener(this);
        bottomPan.add(lockChampion);
        
        leaveGame = new JButton("Leave game");
        leaveGame.addActionListener(this);
        bottomPan.add(leaveGame);
        
        optionsPan.add(bottomPan);
        
        optionsPan.setPreferredSize(new Dimension(optionsPan.getPreferredSize().width, 80));
        centerPanel.add(optionsPan);
        
        centeredBorderLayout.add(centerPanel, BorderLayout.CENTER);
        
        //new GridBagLayout()
        mainPanel.add(centeredBorderLayout, BorderLayout.CENTER);
        
        chatPanel = new TeamChatPanel();
        chatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(chatPanel, BorderLayout.AFTER_LAST_LINE);
        
        // countdown
        long countdownTime = 0;
        if (client.getLastClientEventSent().equals(ClientEventType.JOINING_POST_CHAMPION_SELECT)) {
        	countdownTime = 1000 * client.getGameTypeConfig(game.getGameTypeConfigId()).getPostPickTimerDuration();
        	lockChampion.setEnabled(false);
        }
        else {
        	countdownTime = 1000 * client.getGameTypeConfig(game.getGameTypeConfigId()).getMainPickTimerDuration();
        }
        countdown = new Countdown(new Countdown.CountdownUpdater() {
			@Override
			public void countdownOver() {}
			@Override
			public void tick(long duration) {
				count.setText("" + (int)(duration/1000));
			}
		}, countdownTime);
        countdown.start();
        
        // ok
        this.setContentPane(mainPanel);
        this.setVisible(true);
	}
	
	public void updateGame(GameDTO game) {
		// update team panels
		myTeamPanel.updateGame(game, isObserver ? client.getCurrentGame().getTeamOne() : client.getMyTeam());
		otherTeamPanel.updateGame(game, isObserver ? client.getCurrentGame().getTeamTwo() : client.getOtherTeam());
		// refresh champ selection list
		champSelPanel.refresh();
		// update skin sel
		if (sellChampSkinPanel != null) {
			sellChampSkinPanel.update();			
		}
		mainPanel.revalidate();
	}
	
	public void close() {
		client.removeGameListener(this);
		countdown.stop();
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == lockChampion) {
			ResultMessage res = client.championSelectCompleted();
			if (res.equals(ResultMessage.OK)) {
				lockChampion.setEnabled(false);
			}
			System.out.println("lockChampion = " + res.getMessage());
		}
		else if (e.getSource() == leaveGame) {
			System.out.println("leave game = " + client.quitGame());
		}
	}

	@Override
	public void clientStateUpdated(ClientEvent e) {
		switch (e.getType()) {
		case POST_CHAMPION_SELECT:
		case CHAMP_SELECT_UPDATE:
			updateGame(e.getGame());
			break;
		case TRADE_CHAMP_REQUEST:
			System.out.println(e.getTradeMessage().getSenderInternalSummonerName() + " wants to trade your champ for " + e.getTradeMessage().getChampion());
			break;
		case TRADE_CHAMP_CANCEL:
			System.out.println(e.getTradeMessage().getSenderInternalSummonerName() + " cancelled the trade");
			break;
		case JOINING_POST_CHAMPION_SELECT:
			//updateGame(e.getGame()); ??
	        long countdownTime = 1000 * client.getGameTypeConfig(e.getGame().getGameTypeConfigId()).getPostPickTimerDuration();
			System.out.println("ChampSelectWin: updating counter !! val : " + countdownTime);
	        if (countdown != null) {
	        	countdown.stop();
	        }
			countdown = new Countdown(new Countdown.CountdownUpdater() {
				@Override
				public void countdownOver() {}
				@Override
				public void tick(long duration) {
					count.setText("" + (int)(duration/1000));
				}
			}, countdownTime);
	        countdown.start();
			break;
		case GAME_CLIENT_CONNECTED_TO_SERVER:
		case RETURNING_LOBBY:
		case RETURNING_TEAM_SELECT:
			close();
			break;
		}
		
	}

}
