package com.fjxokt.lolclient.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.events.ClientEvent;
import com.fjxokt.lolclient.lolrtmps.events.ClientListener;
import com.fjxokt.lolclient.lolrtmps.model.GameQueueConfig;
import com.fjxokt.lolclient.lolrtmps.model.MatchMakerParams;
import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.lolrtmps.model.SearchingForMatchNotification;
import com.fjxokt.lolclient.lolrtmps.model.utils.ResultMessage;
import com.fjxokt.lolclient.lolrtmps.model.utils.SpectatorsAllowed;
import com.fjxokt.lolclient.ui.chat.ChatWinManager;
import com.fjxokt.lolclient.utils.Timer;

public class LobbyPanel extends JPanel implements ActionListener, ClientListener, Timer.TimerUpdater {

	private static final long serialVersionUID = 1L;
	
	private JPanel cards;
	
	private JLabel queueLabel;
	private JComboBox queues;
	private JButton joinQueue;
	private PracticeTable table;
	private JLabel filterLb;
	private JTextField filterField;
	private JButton refreshPractice;
	private JButton joinPractice;
	private JButton observePractice;
	private JButton createPractice;
	private JButton sendMessage;
	private JButton logout;
	
	private Timer timer = new Timer(this, 1000);
	private Integer queueId;
	private long waitingTime;
	
	private LoLClient client = LoLClient.getInst();
	
	public LobbyPanel(JPanel cards) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.cards = cards;
		
		queueLabel = new JLabel("Queues");
        JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pan.add(queueLabel);
        add(pan);
        
        JPanel queuePan = new JPanel();
    	// queues combobox
        ComboBoxModel model = new DefaultComboBoxModel(client.getAvailableQueues().toArray());
        queues = new JComboBox(model);
        queues.setRenderer(new DefaultListCellRenderer() {
        	private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list,Object value,
                      int index,boolean isSelected,boolean cellHasFocus)
			{
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				GameQueueConfig queue = (GameQueueConfig)value;
				if (queue != null) {						
					label.setText(queue.getType() + " (" + queue.getGameMode() + 
						" - " + queue.getPointsConfigKey() + ")");
				}
				return label;
			}
        });
        queuePan.add(queues);
    	
        joinQueue = new JButton("Join queue");
        joinQueue.addActionListener(this);
        // enable queue if the matchmaking is available
        joinQueue.setEnabled(client.getLoginDataPacketForUser().getMatchMakingEnabled());
        queuePan.add(joinQueue);
        
        add(queuePan);
       
        JLabel practLabel = new JLabel("Practice games");
        pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pan.add(practLabel);
        add(pan);
        
        table = new PracticeTable();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getClickCount() == 2) {
            		int row = table.rowAtPoint(e.getPoint());
            		joinGame((PracticeGameSearchResult)table.getObjectAtRow(row));
            	}
            }
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(540, 400));
        add(scroll);
        
        // filter label and textfield
        pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	filterLb = new JLabel("Filter: ");
    	pan.add(filterLb);
    	
    	filterField = new JTextField(25);
    	filterField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				JTextField field = (JTextField)e.getSource();
				((PracticeTableModel)table.getModel()).filter(field.getText());
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}
		});
    	pan.add(filterField);
    	
    	// show private games checkbox
    	JCheckBox check = new JCheckBox("Show private games");
    	check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox check = (JCheckBox)e.getSource();
				((PracticeTableModel)table.getModel()).filter(check.isSelected());
			}
		});
    	pan.add(check);
    	
    	add(pan);

        pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        refreshPractice = new JButton("Refresh list");
        refreshPractice.addActionListener(this);
        pan.add(refreshPractice);
        
        joinPractice = new JButton("Join game");
        joinPractice.addActionListener(this);
        pan.add(joinPractice);
        
        observePractice = new JButton("Observe game");
        observePractice.addActionListener(this);
        pan.add(observePractice);
      
        createPractice = new JButton("Create game");
        createPractice.addActionListener(this);
        pan.add(createPractice);
        
        add(pan);
        
        pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        sendMessage = new JButton("Chat");
        sendMessage.addActionListener(this);
        pan.add(sendMessage);
        
        JButton store = new JButton("Store");
        store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(LoLClient.getInst().getStoreUrl()));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
        pan.add(store);
        
        JButton masteries = new JButton("Masteries");
        masteries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new MasteriesWin();
					}
				});
			}
		});
        pan.add(masteries);
        
        logout = new JButton("Logout");
        logout.addActionListener(this);
        pan.add(logout);
        
        add(pan);
        
        client.addGameListener(this);
	}
	
	@Override
	public void clientStateUpdated(final ClientEvent e) {
		switch (e.getType()) {
		case SEARCHING_FOR_MATCH:
			// triggered when attached to a queue 
			// (when clicking the join queue button or when having a game with friends)
			if (e.getSearchNotification().getJoinedQueues().size() > 0) {
				queueId = e.getSearchNotification().getJoinedQueues().get(0).getQueueId().intValue();
				waitingTime = e.getSearchNotification().getJoinedQueues().get(0).getWaitTime().longValue();
				timer.start();
				// change button title
				joinQueue.setText("Leave queue");
			}
			break;
		case JOINING_MATCHMAKING:
			// stop queue update
			stopQueueUpdate();
			// and launch the popup
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new JoinMatchPopup();
				}
			});
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinQueue) {
			GameQueueConfig queue = (GameQueueConfig)queues.getSelectedItem();
			if (queue != null) {
				if (timer.isStarted()) {
					ResultMessage cancel = client.cancelFromQueueIfPossible(queueId.doubleValue());
					System.out.println("cancel queue: " + cancel);
					// cancel worked
					if (cancel.equals(ResultMessage.OK)) {
						// stop queue update
						stopQueueUpdate();
					}
				}
				// try to join a queue
				else {
					SearchingForMatchNotification infos = client.attachToQueue(new MatchMakerParams(new Integer[]{queue.getId()}));
					System.out.println(infos);
					
					if (infos.getPlayerJoinFailures().size() > 0) {
						JOptionPane.showMessageDialog(this, "Can't join queue for reasons: " + infos.getPlayerJoinFailures().toString(),
									"Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}
		}
		else if (e.getSource() == refreshPractice) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        	public void run() {
	    			refreshPracticeGames();
	        	}
	        });
		}
		else if (e.getSource() == joinPractice) {
			joinGame((PracticeGameSearchResult)table.getSelectedObject());
		}
		else if (e.getSource() == observePractice) {
			PracticeGameSearchResult selRes = (PracticeGameSearchResult)table.getSelectedObject();
			SpectatorsAllowed spec = SpectatorsAllowed.getStateFromString(selRes.getAllowSpectators());
			if (!spec.equals(SpectatorsAllowed.NONE)) {				
				client.observeGame(selRes.getId());
			}
			else {
				JOptionPane.showMessageDialog(this, "spectators not allowed for game '" + selRes.getName() + "'",
						"Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if (e.getSource() == createPractice) {
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "CREATE_GAME_PANEL");
		}
		else if (e.getSource() == sendMessage) {
			ChatWinManager.getInst().getMainWin().setVisible(true);
		}		
		else if (e.getSource() == logout) {
			client.logout();
		}
	}
	
	private void joinGame(PracticeGameSearchResult game) {
		if (game == null) return;
		
		String pwd = null;
		if (game.isPrivateGame()) {
			 pwd = JOptionPane.showInputDialog(null, "Enter password : ", "", JOptionPane.INFORMATION_MESSAGE);
			 if (pwd == null || pwd.isEmpty()) {
				 return;
			 }
		}
		
		System.out.println("Trying to join game '" + game.getName() + "' (" + game.getId() + ")...");
		ResultMessage joinOk = client.joinGame(game.getId(), pwd);
		if (joinOk.equals(ResultMessage.OK)) {
			System.out.println("join ok");
		}
		else {
			JOptionPane.showMessageDialog(this, "Error joining game '" + game.getName() + "' : " + joinOk.getMessage(),
					"Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void refreshPracticeGames() {
		List<PracticeGameSearchResult> games = client.listAllPracticeGames();
		System.out.println("" + games.size() + " practice games availables");
		((PracticeTableModel)table.getModel()).updateList(games);
	}
	
	public void stopQueueUpdate() {
		// stop timer
		timer.stop();
		// rename button
		joinQueue.setText("Join queue");
		// queue label
		queueLabel.setText("Queues");
	}
	
	@Override
	public void updatedTick(long value) {
		final int h = 1000*60*60, m = 1000*60;
		String waitS = String.format("%dm %ds", (waitingTime%h)/m, ((waitingTime%h)%m)/1000);
		String timeS = String.format("%dm %ds", (value%h)/m, ((value%h)%m)/1000);
		queueLabel.setText("In queue for " + timeS + ". Estimated time: " + waitS);
	}

}
