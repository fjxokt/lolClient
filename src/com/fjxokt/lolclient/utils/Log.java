package com.fjxokt.lolclient.utils;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Log {
	
	private static Logger log;
	private static final String file = "lolclient.log";
    
    public static Logger getInst() {
        if (log == null) {
            FileHandler hand;
    		try {
    			log = Logger.getLogger(Log.class.getName());
    			log.setLevel(Level.INFO);
    			File f = new File(file);
    			boolean tooBig = (f.length() > 1000000L);
    			// append to the file if size < 1MB
    			hand = new FileHandler(file, !tooBig);
    			hand.setFormatter(new SimpleFormatter() {
    				public String format(LogRecord record) {
    		            return new java.util.Date() + " " + record.getLevel() + " " + record.getMessage() + "\r\n";
    				}
    			});
    	    	log.addHandler(hand);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        return log;
    }
    
    private Log(String file) {}
}
