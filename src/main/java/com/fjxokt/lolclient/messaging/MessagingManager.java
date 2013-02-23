package com.fjxokt.lolclient.messaging;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Message.Body;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.lolrtmps.model.dto.GameDTO;
import com.fjxokt.lolclient.lolrtmps.model.utils.GameState;
import com.fjxokt.lolclient.lolrtmps.services.MatchmakerService;
import com.fjxokt.lolclient.ui.chat.ChatPresenceType;
import com.fjxokt.lolclient.utils.SimpleSHA1;
import com.fjxokt.lolclient.utils.SimpleXML;
import com.gvaneyck.rtmp.DummySSLSocketFactory;

public class MessagingManager implements MessageListener {

	private static MessagingManager instance;
	
	private XMPPConnection connection;
	private String serverIp;
	private int serverPort;
	private String username;
	private Map<String, Chat> mapChats;
	private boolean loggedIn;
	// gameID, chatRoom
	private Map<Double, PVPChatRoom> gameRooms;
	
	//////////////////////////////////
	// invit handler
	//////////////////////////////////
	private InvitationListenerHandler invitHandler;
	// add and remove methods
	public void addInvitationListener(InvitationListener invit) {
		invitHandler.addInvitationListener(invit);
	}
	public void removeInvitationListener(InvitationListener invit) {
		invitHandler.removeInvitationListener(invit);
	}
	
	//////////////////////////////////
	// messages listener
	//////////////////////////////////
	private List<ChatListener> msgListeners = new ArrayList<ChatListener>();
	// add a message listener
	public void addChatListener(ChatListener l) {
		msgListeners.add(l);
	}
	// notify methods
	protected void notifyGameMessageReceived(GameDTO game, String user, String message) {
		for (ChatListener l : msgListeners) {
			l.gameMessageReceived(game, user, message);
		}
	}
	protected void notifyBuddyMessageReceived(String userId, String message) {
		for (ChatListener l : msgListeners) {
			l.buddyMessageReceived(userId, message);
		}
	}
	protected void notifyBuddyPresenceChanged(String userId, Presence presence, ChatPresenceType type) {
		for (ChatListener l : msgListeners) {
			l.buddyPresenceChanged(userId, presence, type);
		}
	}
	
	private class PVPChatRoom {
		private MultiUserChat chatRoom;
		private ChatRoomType chatType;
		private PacketListener listener;
		public PVPChatRoom(MultiUserChat chatRoom, ChatRoomType chatType, PacketListener listener) {
			this.chatRoom = chatRoom;
			this.chatType = chatType;
			this.listener = listener;
		}
		public MultiUserChat getChatRoom() {
			return chatRoom;
		}
		public ChatRoomType getChatType() {
			return chatType;
		}
		public PacketListener getListener() {
			return listener;
		}
	}
	
	private class PVPChatRoomListener implements PacketListener {
		public void processPacket(Packet p) {
			//packet received: <message id="hm_97" to="sum39299508@pvp.net/xiff" from="ap~668f0a8595801c49e2ef016cd1962e72d05b43c3@sec.pvp.net/AngryCrakBaby" type="groupchat"><body>&lt;![CDATA[&lt;body&gt;&lt;invitelist&gt;&lt;invitee name=&quot;KrakenPiraten&quot; status=&quot;PENDING&quot; /&gt;&lt;invitee name=&quot;ToothlessHooker&quot; status=&quot;PENDING&quot; /&gt;&lt;/invitelist&gt;&lt;/body&gt;]]&gt;</body></message>
			//packet received: <message id="m_34" to="sum39299508@pvp.net/xiff" from="ap~668f0a8595801c49e2ef016cd1962e72d05b43c3@sec.pvp.net/Lordacdc" type="groupchat"><body>&lt;![CDATA[WHERE ARE MY BOOBIES?!]]&gt;</body></message>
			String str = replaceHTML(p.toXML());
			String msg = SimpleXML.getTagValue(str, "body");
			String user = p.getFrom();
			if (user.contains("/")) {
				user = user.substring(user.indexOf('/') + 1);
			}
			System.out.println("packet received: " + str);
			System.out.println("msg: " + msg);
			String body = unwrapMessage(msg);
			// TODO: check if we want to fire an event in this case
			if (body.startsWith("<body>") && body.endsWith("</body>")) {
				return;
			}
			notifyGameMessageReceived(null, user, body);
		}
	}
	
	private MessagingManager() {
		SmackConfiguration.setPacketReplyTimeout(5000);
		mapChats = new HashMap<String, Chat>();
		gameRooms = new HashMap<Double, MessagingManager.PVPChatRoom>();
		// init invit handler
		invitHandler = new InvitationListenerHandler();
	}
	
	public static MessagingManager getInst() {
		if (instance == null) {
			instance = new MessagingManager();
		}
		return instance;
	}
	
	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getUsername() {
		return username;
	}

	public void init(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}
		
	public boolean login(String username, String password) {
		//System.setProperty("smack.debugEnabled", "true");
		//XMPPConnection.DEBUG_ENABLED = true;
		this.username = username;

	    ConnectionConfiguration xmppConfig = new ConnectionConfiguration(serverIp, serverPort, "pvp.net");
        xmppConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
        xmppConfig.setSocketFactory(new DummySSLSocketFactory());

        connection = new XMPPConnection(xmppConfig);
        try {
			connection.connect();
	        //SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.login(username, "AIR_" + password, "xiff");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	    
	    loggedIn = connection.isAuthenticated();
	    if (!loggedIn) {
	    	return false;
	    }
	    
	    // create listener for people that would start to talk to me
	    ChatManager chatManager = connection.getChatManager();
	    chatManager.addChatListener(new ChatManagerListener() {
			public void chatCreated(Chat chat, boolean createdLocally) {
				if (!mapChats.containsKey(chat.getParticipant()) && !createdLocally) {
                    chat.addMessageListener(MessagingManager.this);
                    mapChats.put(chat.getParticipant(), chat);
				}
			}
		});
	    
	    // entries listener
    	Roster roster = connection.getRoster();
    	roster.addRosterListener(new RosterListener() {
			public void presenceChanged(Presence presence) {
				System.out.println(presence.getFrom() + " changed presence to " + presence.getStatus() + " : " + 
						presence.getType() + " : " + presence.getMode() + " : " + presence.getTo() + " : " + presence.getXmlns() +
						presence.getPropertyNames());
				String userId = presence.getFrom().split("/")[0];
				ChatPresenceType pres = getBuddyPresenceType(getBuddyFromId(userId));
				BuddyStatus status = new BuddyStatus(presence.getStatus());
				System.out.println(status);
				notifyBuddyPresenceChanged(userId, presence, pres);
			}
			public void entriesUpdated(Collection<String> entries) {
				System.out.println(entries.toString() + " have been updated");
			}
			public void entriesDeleted(Collection<String> entries) {
				System.out.println(entries.toString() + " have been deleted");
			}
			public void entriesAdded(Collection<String> entries) {
				System.out.println(entries.toString() + " have been added");
			}
		});
    	
	    
	    displayBuddyList();
	    
	    return loggedIn;
    }
	
	/*
	 * buddy presence:
	 *  <body>
	 *  	<profileIcon>0</profileIcon>
	 *  	<level>5</level>
	 *  	<wins>1</wins>
	 *  	<leaves>1</leaves>
	 *  	<odinWins>1</odinWins>
	 *  	<odinLeaves>0</odinLeaves>
	 *  	<queueType />
	 *  	<rankedWins>0</rankedWins>
	 *  	<rankedLosses>0</rankedLosses>
	 *  	<rankedRating>0</rankedRating>
	 *  	<tier>UNRANKED</tier><statusMsg />
	 *  	<skinname>Ryze</skinname>
	 *  	<gameQueueType>NORMAL_3x3</gameQueueType>
	 *  	<gameStatus>inGame</gameStatus>
	 *  	<timeStamp>1358179214080</timeStamp>
	 *  </body>
	 */
	
	/*
	 * invite to matchmaking:
	 * <body>
	 * 		<inviteId>1847728772</inviteId>
	 * 		<userName>NinjaSUPER</userName>
	 * 		<profileIconId>536</profileIconId
	 * 		><gameType>NORMAL_GAME</gameType>
	 * 		<mapId>1</mapId>
	 * 		<queueId>2</queueId>
	 * 		<gameMode>classic_pvp</gameMode>
	 * 		<gameDifficulty></gameDifficulty>
	 * </body>
	 */
	
	/*
	 * invite to practice game
	 * <body>
	 * 		<inviteId>883308981</inviteId>
	 * 		<userName>fjxokt</userName>
	 * 		<profileIconId>28</profileIconId>
	 * 		<gameType>PRACTICE_GAME</gameType>
	 * 		<gameTypeIndex>practiceGame_gameMode_GAME_CFG_PICK_BLIND</gameTypeIndex>
	 * 		<gameId>671954227</gameId>
	 * 		<mapId>1</mapId>
	 * 		<gamePassword></gamePassword>
	 * </body>
	 */
	
	/* 
	 * game invite rejected: subject = GAME_INVITE_REJECT
	 * other: GAME_MSG_OUT_OF_SYNC
	 * other: GAME_INVITE_OWNER_CANCEL
	 */
	
	public void disconnect() {
		connection.disconnect();
		loggedIn = false;
    }
	
	public boolean reconnect() {
		connection.disconnect();
		try {
			connection.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
			System.out.println("could not reconnect to chat!");
		}
		loggedIn = connection.isAuthenticated();
		return loggedIn;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public Message createInvite(String to) {
		Message m = new Message("sum22371167@pvp.net");
		m.setSubject("GAME_INVITE");
		m.setType(Type.normal);
		//m.setTo("sum22371167@pvp.net");
		m.addBody(Message.getDefaultLanguage(), "<body><inviteId>1234567890</inviteId><userName>fjxokttest</userName>" +
				"<profileIconId>28</profileIconId><gameType>NORMAL_GAME</gameType>" +
				"<mapId>1</mapId><queueId>2</queueId><gameMode>classic_pvp</gameMode>" +
				"<gameDifficulty></gameDifficulty></body>");
		try {
			sendMessage(m, "sum22371167@pvp.net");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	public void processMessage(Chat chat, Message message) {
		// ignore null messages
		System.out.println("MSG: " + message.getSubject());
		System.out.println("MSG: " + message.getBody());
		System.out.println("MSG: " + message.getLanguage());
		System.out.println("MSG: " + message.getType());
		System.out.println("MSG: " + message.getFrom());
		System.out.println("MSG: " + message.getTo());
		System.out.println("MSG: " + message.getXmlns());
		System.out.println("MSG: " + message.getPacketID());
		System.out.println("MSG: " + message.getThread());
		System.out.println("MSG: " + message.getSubject(Message.getDefaultLanguage()));
		System.out.println("MSG: " + Packet.getDefaultLanguage());
		String msg = null;
		for (Body bod : message.getBodies()) {
			System.out.println(bod.getLanguage() + " : " + bod.getMessage());
			msg = bod.getMessage();
		}
		for (String prop : message.getPropertyNames()) {
			System.out.println("prop: " + prop + " : " + message.getProperty(prop));
		}
		
		// Check if the message is related to invitation
		if (InvitationManager.getInst().isInvitationRelated(message)) {
			InvitationManager.getInst().processInvitation(message);
		}
				
		if (message.getSubject() != null) {
			if (message.getSubject().equals("PRACTICE_GAME_INVITE")) {
				PracticeInvitation invit = new PracticeInvitation(msg);
				System.out.println(invit);
				invitHandler.notifyInvitationReceived(message.getFrom(), invit);
			}
			else if (message.getSubject().equals("GAME_INVITE")) {
				MatchmakingInvitation invit = new MatchmakingInvitation(msg);
				System.out.println(invit);
				invitHandler.notifyInvitationReceived(message.getFrom(), invit);
			}
		}
		
		if (message.getSubject() != null && false) {
			if (message.getSubject().equals("GAME_INVITE")) {
				
				// answer to the invit
				Message m = new Message("sum22371167@pvp.net");
				m.setSubject("GAME_INVITE_ACCEPT");
				m.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				m.addBody(Message.getDefaultLanguage(), msg);
				try {
					sendMessage(m, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (message.getSubject().equals("GAME_INVITE_ACCEPT")) {
				//String invitationId = getTagVal(msg, "inviteId");
				String username = SimpleXML.getTagValue(msg, "userName");
				//String queueId = getTagVal(msg, "queueId");
				
				// answer to the invit
				Message m = new Message("sum22371167@pvp.net");
				m.setSubject("GAME_INVITE_ACCEPT_ACK");
				m.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				m.addBody(Message.getDefaultLanguage(), msg);
				try {
					sendMessage(m, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// list invited ppl
				Message p = new Message("sum22371167@pvp.net");
				p.setSubject("GAME_INVITE_LIST_STATUS");
				p.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				p.addBody(Message.getDefaultLanguage(), "<body><invitelist><invitee name=\"" + username +"\" " +
						"status=\"ACCEPTED\" /></invitelist></body>");
				try {
					sendMessage(p, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				/*System.out.println(invitationId);
				System.out.println(username);
				System.out.println(queueId);
				String userId = chat.getParticipant().split("/")[0];
				LoLClient.getInst().invitationAccepted(invitationId, getBuddyFromId(userId).getName(), queueId);*/
			}
			else if (message.getSubject().equals("GAME_INVITE_LIST_STATUS")) {
				Message m = new Message("sum22371167@pvp.net");
				m.setSubject("GAME_INVITE_LIST_STATUS_ACK");
				m.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				m.addBody(Message.getDefaultLanguage(), msg);
				try {
					sendMessage(m, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// i have been accepted (received ater I send my GAME_INVITE_ACCEPT)
			else if (message.getSubject().equals("GAME_INVITE_ACCEPT_ACK")) {
				String invitationId = SimpleXML.getTagValue(msg, "inviteId");
				MatchmakerService.acceptInviteForMatchmakingGame(LoLClient.getInst().getRTMPSClient(), invitationId);
				
				// TEST
				Message m = new Message("sum22371167@pvp.net");
				m.setSubject("VERIFY_INVITEE");
				m.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				m.addBody(Message.getDefaultLanguage(), msg);
				try {
					sendMessage(m, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			else if (message.getSubject().equals("VERIFY_INVITEE")) {
				// accept the invite
				//String invitationId = getTagVal(msg, "inviteId");
				//LoLClient.getInst().acceptInviteForMatchmakingGame(invitationId);
				
				// answer to the invit message
				Message m = new Message("sum22371167@pvp.net");
				m.setSubject("VERIFY_INVITEE_ACK");
				m.setType(Type.normal);
				//m.setTo("sum22371167@pvp.net");
				m.addBody(Message.getDefaultLanguage(), msg);
				try {
					sendMessage(m, "sum22371167@pvp.net");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		if (message.getBody() == null) {
			return;
		}
		
		if (message.getType() == Message.Type.chat) {
			System.out.println(chat.getParticipant() + " says: " + message.getBody());
			String userId = chat.getParticipant().split("/")[0];
			notifyBuddyMessageReceived(userId, message.getBody());
		}
		else if (message.getType() == Message.Type.groupchat) {
			System.out.println("group: " + chat.getParticipant() + " says: " + message.getBody());
		}
		else if (message.getType() == Message.Type.groupchat) {
			System.out.println("normal: " + chat.getParticipant() + " says: " + message.getBody());
		}
    }
	
	private String replaceHTML(String src) {
		return src.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
	}
	
	private String wrapMessage(String msg) {
		return "<![CDATA[" + msg + "]]>";
	}
	
	private String unwrapMessage(String msg) {
		return msg.substring(9, msg.length() - 3);
	}
	
	public String getMyName() {
		return username;
	}
	
	public void sendMessage(String message, String to) throws XMPPException {
		Chat chat = mapChats.get(to);
		if (chat == null) {
			chat = connection.getChatManager().createChat(to, this);
			connection.getChatManager().addChatListener(new ChatManagerListener() {
				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(MessagingManager.this);
				}
			});
			mapChats.put(to, chat);
		}
		chat.sendMessage(message);
    }
	
	public void sendMessage(Message message, String to) throws XMPPException {
		Chat chat = mapChats.get(to);
		if (chat == null) {
			chat = connection.getChatManager().createChat(to, this);
			connection.getChatManager().addChatListener(new ChatManagerListener() {
				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(MessagingManager.this);
				}
			});
			mapChats.put(to, chat);
		}
		System.out.println("MESSAGE TYPE: " + message.getType());
		//ChatManager man = connection.getChatManager();
		connection.sendPacket(message);
		//man.
		//chat.sendMessage(message);
    }
	
	public void sendMessage(GameDTO game, String message) {
		PVPChatRoom pvpChatRoom = gameRooms.get(game.getId());
		try {
			pvpChatRoom.getChatRoom().sendMessage(wrapMessage(message));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
 
	public void joinRoom(GameDTO game) {
		
		// check the room doesnt already exists
		PVPChatRoom pvproom = gameRooms.get(game.getId());
		
		// if room already exists
		if (pvproom != null) {
			// it means we are changing room for this game (from team sel to champ sel, to champ sel to end of game...)
			// we just leave the other room
			leaveRoom(game);
		}
		
		String roomName = game.getName();
		String roomPassword = game.getRoomPassword();
		
		// TODO: check this
		// this one depends on the gameType and gameState of game we're in
		ChatRoomType type = null;
		if (game.getGameState().equals(GameState.CHAMP_SELECT.getName()) || game.getGameState().equals(GameState.POST_CHAMP_SELECT.getName())) {
			type = ChatRoomType.ARRANGING_PRACTICE;
		}
		else {
			type = ChatRoomType.ARRANGING_PRACTICE;
		}
		
		String sha1 = null;
		try {
			sha1 = SimpleSHA1.SHA1(roomName.toLowerCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String room = type.getType() + ChatRoomType.PREFIX_DELIMITER + sha1 + "@sec.pvp.net";
		System.out.println("room: " + room + " : pwd: " + roomPassword);
		
		try {
//			try {
//			RoomInfo info = MultiUserChat.getRoomInfo(connection, room);
//			System.out.println("Number of occupants:" + info.getOccupantsCount());
//			System.out.println("Room Subject:" + info.getSubject());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			// create the chat object
			MultiUserChat chatRoom = new MultiUserChat(connection, room);
			// join the room
			chatRoom.join(getUsername(), roomPassword);
			// create and add the packet listener for this room
			PVPChatRoomListener listener = new PVPChatRoomListener();
			chatRoom.addMessageListener(listener);
			// create our pvpchat object
			pvproom = new PVPChatRoom(chatRoom, type, listener);
			gameRooms.put(game.getId(), pvproom);
			
			System.out.println(chatRoom.getSubject() + " : " + chatRoom.getOccupantsCount());
			
			// some debug for fun
			Iterator<String> occupants = chatRoom.getOccupants();
//			chatRoom.addMessageListener(new PacketListener() {
//				public void processPacket(Packet p) {
//					System.out.println("new packet received: " + p.toXML());
//					for (String s : p.getPropertyNames()) {
//						System.out.println("prop " + s + " : " + p.getProperty(s));
//					}
//				}
//			});
			while (occupants.hasNext()) {
				System.out.println("Player " + occupants.next() + " joined the team.");
			}
//			chatRoom.leave();
		} catch (XMPPException e1) {
			e1.printStackTrace();
		}
	}
	
	public void leaveRoom(GameDTO game) {
		PVPChatRoom room = gameRooms.get(game.getId());
		if (room != null) {
			room.getChatRoom().removeMessageListener(room.getListener());
			gameRooms.remove(game.getId());
		}
	}
	
	public void displayRooms() {
		try {
			Collection<HostedRoom> hostedRooms = MultiUserChat.getHostedRooms(connection, "lvl.pvp.net");
			for (HostedRoom r : hostedRooms) {
				System.out.println(r.getName());
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
    public void displayBuddyList() {
	    Collection<RosterEntry> entries = getBuddyList();
	    System.out.println("" + entries.size() + " buddy(ies):");
	    for(RosterEntry r : entries) {
	    	System.out.println(r.getUser() + " : " + r.getStatus() + " : " + r.getName() + " : " + r.getType() + " : " + isBuddyAvailable(r));
	    	for (RosterGroup group : r.getGroups()) {
	    		System.out.println("group: " + group.getName());
	    	}
	    }
    }
    
    public void addBuddy() {
    	//Roster roster = connection.getRoster();
    }
    
    public RosterEntry getBuddyFromName(String name) {
    	for (RosterEntry e : getBuddyList()) {
    		if (e.getName().equals(name)) {
    			return e;
    		}
    	}
    	return null;
    }
    
    public RosterEntry getBuddyFromId(String id) {
    	for (RosterEntry e : getBuddyList()) {
    		if (e.getUser().equals(id)) {
    			return e;
    		}
    	}
    	return null;
    }
    
    public Collection<RosterEntry> getBuddyList() {
    	Roster roster = connection.getRoster();
	    Collection<RosterEntry> entries = roster.getEntries();
	    return entries;
    }
    
    public ChatPresenceType getBuddyPresenceType(RosterEntry buddy) {
    	if (isBuddyAvailable(buddy)) {
    		return ChatPresenceType.AVAILABLE;
    	}
    	if (isBuddyAway(buddy)) {
    		return ChatPresenceType.AWAY;
    	}
    	if (isBuddyBusy(buddy)) {
    		return ChatPresenceType.BUSY;
    	}
    	return ChatPresenceType.OFFLINE;
    }
    
    public boolean isBuddyAvailable(RosterEntry buddy) {
	    Roster roster = connection.getRoster();
	    Presence p = roster.getPresence(buddy.getUser());
	    if (p.getMode() != null && (p.getMode().equals(Mode.away) || p.getMode().equals(Mode.dnd))) {
	    	return false;
	    }
	    return p.isAvailable();
    }
    
    public boolean isBuddyAway(RosterEntry buddy) {
	    Roster roster = connection.getRoster();
	    Presence p = roster.getPresence(buddy.getUser());
	    return p.getMode() != null && p.getMode().equals(Mode.away);
    }
    
    public boolean isBuddyBusy(RosterEntry buddy) {
	    Roster roster = connection.getRoster();
	    Presence p = roster.getPresence(buddy.getUser());
	    return p.getMode() != null && p.getMode().equals(Mode.dnd);
    }
	
}