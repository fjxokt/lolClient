package com.fjxokt.lolclient.messaging;

public class ChatConfig {

	// !isPublic && hasPwd
	final static String xmpp_muc_secure_conference_name = "sec";
	// isPublic && !hasPwd
	final static String xmpp_muc_global_name = "lvl";
	// !isPublic && !hasPwd
	final static String xmpp_muc_conference_name = "conference";

    private ChatConfig() {
    }
	
}
