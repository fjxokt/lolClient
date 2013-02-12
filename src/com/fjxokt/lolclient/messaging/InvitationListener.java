package com.fjxokt.lolclient.messaging;

import java.util.Map;

public interface InvitationListener {
	
	void invitationReceived(String userId, Invitation invitation);
	void invitationAccepted(String userId, Invitation invitation);
	void invitationRejected(String userId, Invitation invitation);
	void invitationStatus(Map<String,String> usersStatus);
}
