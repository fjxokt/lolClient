package com.fjxokt.lolclient.messaging;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import com.fjxokt.lolclient.lolrtmps.LoLClient;
import com.fjxokt.lolclient.utils.SimpleXML;

public class InvitationManager {
	
	private static InvitationManager instance;
	
	private InvitationListenerHandler invitHandler;
	
	public enum Status {
		GAME_INVITE("GAME_INVITE"), GAME_INVITE_ACCEPT("GAME_INVITE_ACCEPT"), GAME_INVITE_REJECT("GAME_INVITE_REJECT"),
		PRACTICE_GAME_INVITE("PRACTICE_GAME_INVITE"), PRACTICE_GAME_INVITE_ACCEPT("PRACTICE_GAME_INVITE_ACCEPT"), PRACTICE_GAME_INVITE_REJECT("PRACTICE_GAME_INVITE_REJECT"),
		GAME_INVITE_LIST_STATUS("GAME_INVITE_LIST_STATUS"),
		VERIFY_INVITEE("VERIFY_INVITEE");
		private String subject;
		Status(String subject) {
			this.subject = subject;
		}
		public String getType() {
			return subject;
		}
		public static Status getStatusFromString(String str) {
			Status[] status = Status.values();
			for (Status s : status) {
				if (s.getType().equals(str)) {
					return s;
				}
			}
			return null;
		}
		
	}
	
	private InvitationManager() {
		invitHandler = new InvitationListenerHandler();
	}
	
	public static InvitationManager getInst() {
		if (instance == null) {
			instance = new InvitationManager();
		}
		return instance;
	}
	
	public void addInvitationListener(InvitationListener invit) {
		invitHandler.addInvitationListener(invit);
	}
	
	public void removeInvitationListener(InvitationListener invit) {
		invitHandler.removeInvitationListener(invit);
	}
	
	// this will say if the message is related to game invitation or not
	public boolean isInvitationRelated(Message message) {
		if (Status.getStatusFromString(message.getSubject()) != null) {
			return true;
		}
		return false;
	}
	
	// check first if the message is an invitation
	public void processInvitation(Message message) {
		Status status = Status.getStatusFromString(message.getSubject());
		Invitation inv = null;
		switch (status) {
		// invitation to a game queue
		case GAME_INVITE:
			inv = new MatchmakingInvitation(message.getBody());
			invitHandler.notifyInvitationReceived(message.getFrom(), inv);
			// for test, let's accept the invit here
			acceptInvitation(inv);
			/*Message m1 = new Message(message.getFrom());
			m1.setSubject("GAME_INVITE_ACCEPT");
			m1.setType(Type.normal);
			m1.addBody(Message.getDefaultLanguage(), message.getBody());
			try {
				MessagingManager.getInst().sendMessage(m1, message.getFrom());
				System.out.println("sent message");
			} catch (XMPPException e) {
				e.printStackTrace();
			}*/
			break;
		case GAME_INVITE_ACCEPT:
			MatchmakingInvitation minv = new MatchmakingInvitation(message.getBody());
			invitHandler.notifyInvitationAccepted(message.getFrom(), minv);
			// send ACK
			Message m = new Message(message.getFrom());
			m.setSubject("GAME_INVITE_ACCEPT_ACK");
			m.setType(Type.normal);
			m.addBody(Message.getDefaultLanguage(), message.getBody());
			try {
				MessagingManager.getInst().sendMessage(m, message.getFrom());
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			//System.out.println("CALL CLIENT");
			// test, just accept and start queueing
			//LoLClient.getInst().invitationAccepted(""+inv.getInviteId(), inv.getUserName(), ""+minv.getQueueId());
			break;
		case GAME_INVITE_REJECT:
			inv = new MatchmakingInvitation(message.getBody());
			invitHandler.notifyInvitationRejected(message.getFrom(), inv);
			break;
		// invitation to a practice game
		case PRACTICE_GAME_INVITE:
			inv = new PracticeInvitation(message.getBody());
			invitHandler.notifyInvitationReceived(message.getFrom(), inv);
			// for test, let's accept the invit here
			acceptInvitation(inv);
			/*Message m2 = new Message(message.getFrom());
			m2.setSubject("PRACTICE_GAME_INVITE_ACCEPT");
			m2.setType(Type.normal);
			m2.addBody(Message.getDefaultLanguage(), message.getBody());
			try {
				MessagingManager.getInst().sendMessage(m2, message.getFrom());
			} catch (XMPPException e) {
				e.printStackTrace();
			}*/
			break;
		case PRACTICE_GAME_INVITE_ACCEPT:
			inv = new PracticeInvitation(message.getBody());
			invitHandler.notifyInvitationAccepted(message.getFrom(), inv);
			break;
		case PRACTICE_GAME_INVITE_REJECT:
			inv = new PracticeInvitation(message.getBody());
			invitHandler.notifyInvitationRejected(message.getFrom(), inv);
			break;
		case GAME_INVITE_LIST_STATUS:
			// <body><invitelist><invitee name=\"" + username + "status=\"ACCEPTED\" /></invitelist></body>"
			// TODO: invitHandler.notifyInvitationStatus(message.getBody());
			// build list of users with their status
			break;
		case VERIFY_INVITEE:
			// TODO: when to use this case ?
			String invitationId = SimpleXML.getTagValue(message.getBody(), "inviteId");
			LoLClient.getInst().acceptInviteForMatchmakingGame(invitationId);
			break;
		default:
			break;
		}
	}
	
	public void acceptInvitation(Invitation invit) {
		RosterEntry buddy = MessagingManager.getInst().getBuddyFromName(invit.getUserName());
		Message m1 = new Message(buddy.getUser());
		if (invit instanceof PracticeInvitation) {
			m1.setSubject(Status.PRACTICE_GAME_INVITE_ACCEPT.getType());
		}
		else {			
			m1.setSubject(Status.GAME_INVITE_ACCEPT.getType());
		}
		m1.setType(Type.normal);
		m1.addBody(Message.getDefaultLanguage(), invit.toXML());
		try {
			MessagingManager.getInst().sendMessage(m1, buddy.getUser());
			System.out.println("sent accept invitation");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	public void rejectInvitation(Invitation invit) {
		RosterEntry buddy = MessagingManager.getInst().getBuddyFromName(invit.getUserName());
		Message m1 = new Message(buddy.getUser());
		if (invit instanceof PracticeInvitation) {
			m1.setSubject(Status.PRACTICE_GAME_INVITE_REJECT.getType());
		}
		else {			
			m1.setSubject(Status.GAME_INVITE_REJECT.getType());
		}		m1.setType(Type.normal);
		m1.addBody(Message.getDefaultLanguage(), invit.toXML());
		try {
			MessagingManager.getInst().sendMessage(m1, buddy.getUser());
			System.out.println("sent reject invitation");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
