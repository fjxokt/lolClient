package com.fjxokt.lolclient.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvitationListenerHandler {
	
	private List<InvitationListener> invitListeners = new ArrayList<InvitationListener>();
	
	// add a message listener
	public void addInvitationListener(InvitationListener l) {
		invitListeners.add(l);
	}
	
	// remove a message listener
	public void removeInvitationListener(InvitationListener l) {
		invitListeners.remove(l);
	}
	
	// notify methods
	protected void notifyInvitationReceived(String userId, Invitation invitation) {
		for (InvitationListener l : invitListeners) {
			l.invitationReceived(userId, invitation);
		}
	}
	
	protected void notifyInvitationAccepted(String userId, Invitation invitation) {
		for (InvitationListener l : invitListeners) {
			l.invitationAccepted(userId, invitation);
		}	
	}
	
	protected void notifyInvitationRejected(String userId, Invitation invitation) {
		for (InvitationListener l : invitListeners) {
			l.invitationRejected(userId, invitation);
		}
	}
	
	protected void notifyInvitationStatus(Map<String,String> usersStatus) {
		for (InvitationListener l : invitListeners) {
			l.invitationStatus(usersStatus);
		}
	}

}
