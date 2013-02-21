package com.fjxokt.lolclient;

import java.io.File;

public class ResourceConstants {
	
	public final static String sep = File.separator;
	public final static String language = "en_US";
	
	public final static String assetsBasePackage = "." + sep + "assets" + sep;
	public final static String imageAssetsBasePackage = assetsBasePackage + "images" + sep;      
	public final static String soundAssetsBasePackage = assetsBasePackage + "sounds" + sep;
	
	public final static String sqlFile = assetsBasePackage + sep + "data" + sep + "gameStats" + sep + "gameStats_" + language + ".sqlite";
	
	public final static String abilitiesIconPath = imageAssetsBasePackage + "champion_abilities" + sep;
	public final static String itemsIconPath = imageAssetsBasePackage + "item_icons" + sep;
	public final static String champIconPath = imageAssetsBasePackage + "champion_icons" + sep;
	public final static String portraitPath = imageAssetsBasePackage + "champion_banners" + sep;
	public final static String splashPath = imageAssetsBasePackage + "champion_splashes" + sep;
	public final static String genericClientImagesPath = imageAssetsBasePackage + "generic_client_images" + sep;
	public final static String spellIconPath = imageAssetsBasePackage + "spell_icons" + sep;    
	
	public final static String soundsChampsPath = soundAssetsBasePackage + language + sep + "champions" + sep;
	
}
