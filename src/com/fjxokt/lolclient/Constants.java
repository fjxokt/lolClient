package com.fjxokt.lolclient;

import java.io.File;

public class Constants {
	
	public final static String sep = File.separator;
	public final static String language = "en_US";
	public final static String sqlFile = "assets" + sep + "data" + sep + "gameStats" + sep + "gameStats_" + language + ".sqlite";
	public final static String abilitiesIconPath = "assets" + sep + "images" + sep + "championScreens" + sep + "abilities" + sep;
	public final static String itemsIconPath = "assets" + sep + "images" + sep + "items" + sep;
	public final static String champIconPath = "assets" + sep + "images" + sep + "champions" + sep;
	public final static String portraitPath = champIconPath;
	public final static String splashPath = champIconPath;
	public final static String soundsPath = "assets" + sep + "sounds" + sep;
	public final static String soundsChampsPath = soundsPath + language + sep + "champions" + sep;
	
}
