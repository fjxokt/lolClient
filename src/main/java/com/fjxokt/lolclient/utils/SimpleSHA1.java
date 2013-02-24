package com.fjxokt.lolclient.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SimpleSHA1 { 
 
    private static String convertToHex(byte[] data) { 
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) { 
                    buf.append((char) ('0' + halfbyte));
                }
                else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    }
    
    public static String SHA1(byte[] data)  { 
    	MessageDigest md = null;
    	try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			Log.getInst().severe("Could not get SHA1 hash!");
			return null;
		}
    	byte[] sha1hash = new byte[40];
	    md.update(data);
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
    }
 
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
    } 

    private SimpleSHA1() {
    }
} 