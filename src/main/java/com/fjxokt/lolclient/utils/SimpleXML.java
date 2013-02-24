package com.fjxokt.lolclient.utils;

public class SimpleXML {
	
	// Simply return what is between first occurence of <tag> and last occurence of </tag>
	public static String getTagValue(String xml, String tag) {
		int k = xml.indexOf("<" + tag + ">");
		if (k > -1) {
			String sub = xml.substring(k + 1 + tag.length());
			int l = sub.lastIndexOf("</" + tag + ">");
			if (l > -1) {
				return xml.substring(k + 2 + tag.length(), k + 1 + l + tag.length());
			}
		}
		return null;
	}
	
	public static Integer getIntTagValue(String xml, String tag) {
		String s = getTagValue(xml, tag);
		if (s != null) {
			return Integer.parseInt(s);
		}
		return null;
	}
	
	public static Double getDoubleTagValue(String xml, String tag) {
		String s = getTagValue(xml, tag);
		if (s != null) {
			return Double.parseDouble(s);
		}
		return null;
	}

    private SimpleXML() {
    }

}
