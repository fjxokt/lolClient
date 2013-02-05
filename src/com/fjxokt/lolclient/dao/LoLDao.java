package com.fjxokt.lolclient.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fjxokt.lolclient.model.Champion;
import com.fjxokt.lolclient.model.ChampionAbility;
import com.fjxokt.lolclient.model.ChampionSkin;
import com.fjxokt.lolclient.model.Item;
import com.fjxokt.lolclient.model.ItemCategory;


public class LoLDao {
	
	// this requieres the queryManager to be already initialized with a db
	private static QueryManager queryManager = QueryManager.getInst();
	
	public static List<ChampionSkin> getChampionSkins(Integer championId) {
		ResultSet rs = queryManager.executeQuery("select * from championSkins where championId = " + championId);
		List<ChampionSkin> skins = new ArrayList<ChampionSkin>();
		try {
			while (rs.next()) {
				ChampionSkin skin = new ChampionSkin();
				skin.setId(rs.getInt("id"));
				skin.setIsBase(rs.getBoolean("isBase"));
				skin.setRank(rs.getInt("rank"));
				skin.setChampionId(championId);
				skin.setName(rs.getString("name"));
				skin.setDisplayName(rs.getString("displayName"));
				skin.setPortraitPath(rs.getString("portraitPath"));
				skin.setSplashPath(rs.getString("splashPath"));
				skins.add(skin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return skins;
	}
	
	public static List<ChampionAbility> getChampionAbilities(Integer championId) {
		ResultSet rs = queryManager.executeQuery("select * from championAbilities where championId = " + championId);
		List<ChampionAbility> abilities = new ArrayList<ChampionAbility>();
		try {
			while (rs.next()) {
				ChampionAbility ability = new ChampionAbility();
				ability.setId(rs.getInt("id"));
				ability.setRank(rs.getInt("rank"));
				ability.setChampionId(rs.getInt("championId"));
				ability.setName(rs.getString("name"));
				ability.setCost(rs.getString("cost"));
				ability.setCooldown(rs.getString("cooldown"));
				ability.setIconPath(rs.getString("iconPath"));
				ability.setRange(rs.getDouble("range"));
				ability.setEffect(rs.getString("effect"));
				ability.setDescription(rs.getString("description"));
				ability.setHotkey(rs.getString("hotkey"));
				abilities.add(ability);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return abilities;
	}
	
	private static Champion getChampion(ResultSet rs) {
		Champion champ = new Champion();
		try {
			champ.setId(rs.getInt("id"));
			champ.setName(rs.getString("name"));
			champ.setDisplayName(rs.getString("displayName"));
			champ.setTitle(rs.getString("title"));
			champ.setIconPath(rs.getString("iconPath"));
			champ.setPortraitPath(rs.getString("portraitPath"));
			champ.setSplashPath(rs.getString("splashPath"));
			champ.setSelectSoundPath(rs.getString("selectSoundPath"));
			champ.setTags(rs.getString("tags"));
			champ.setDescription(rs.getString("description"));
			champ.setRange(rs.getDouble("range"));
			champ.setMoveSpeed(rs.getDouble("moveSpeed"));
			champ.setArmorBase(rs.getDouble("armorBase"));
			champ.setArmorLevel(rs.getDouble("armorLevel"));
			champ.setManaBase(rs.getDouble("manaBase"));
			champ.setManaLevel(rs.getDouble("manaLevel"));
			champ.setManaBase(rs.getDouble("manaBase"));
			champ.setCriticalChanceBase(rs.getDouble("criticalChanceBase"));
			champ.setCriticalChanceLevel(rs.getDouble("criticalChanceLevel"));
			champ.setManaRegenBase(rs.getDouble("manaRegenBase"));
			champ.setManaRegenLevel(rs.getDouble("manaRegenLevel"));
			champ.setHealthRegenBase(rs.getDouble("healthRegenBase"));
			champ.setHealthLevel(rs.getDouble("healthRegenLevel"));
			champ.setMagicResistBase(rs.getDouble("magicResistBase"));
			champ.setMagicResistLevel(rs.getDouble("magicResistLevel"));
			champ.setHealthBase(rs.getDouble("healthBase"));
			champ.setHealthLevel(rs.getDouble("healthLevel"));
			champ.setAttackBase(rs.getDouble("attackBase"));
			champ.setAttackLevel(rs.getDouble("attackLevel"));
			champ.setRatingDefense(rs.getInt("ratingDefense"));
			champ.setRatingMagic(rs.getInt("ratingMagic"));
			champ.setRatingDifficulty(rs.getInt("ratingDifficulty"));
			champ.setRatingAttack(rs.getInt("ratingAttack"));
			
			List<String> tipsList = new ArrayList<String>();
			String tips = rs.getString("tips");
			String[] spl = tips.split("\\*");
			for (String s : spl) {
				if (!s.isEmpty()) {
					tipsList.add(s);
				}
			}
			champ.setTips(tipsList);
			
			List<String> opponentTipsList = new ArrayList<String>();
			tips = rs.getString("opponentTips");
			spl = tips.split("\\*");
			for (String s : spl) {
				if (!s.isEmpty()) {
					opponentTipsList.add(s);
				}
			}
			champ.setOpponentTips(opponentTipsList);
			
			champ.setSkins(getChampionSkins(champ.getId()));
			
			champ.setAbilities(getChampionAbilities(champ.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return champ;
	}
	
	public static Champion getChampion(Integer championId) {
		Champion champ = new Champion();
		ResultSet rs = queryManager.executeQuery("select * from champions where id = " + championId);
		try {
			if (rs.next()) {
				champ = getChampion(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return champ;
	}
	
	public static List<Champion> getChampions() {
		List<Champion> champs = new ArrayList<Champion>();
		ResultSet rs = queryManager.executeQuery("select * from champions");
		try {
			while (rs.next()) {
				Champion champ = getChampion(rs);
				champs.add(champ);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return champs;
	}
	
	private static Item getItem(ResultSet rs) {
		Item item = new Item();
		try {
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setDescription(rs.getString("description"));
			item.setIconPath(rs.getString("iconPath"));
			item.setPrice(rs.getInt("price"));
			item.setEpicness(rs.getInt("epicness"));
			item.setCategories(getItemCategories(item.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public static Item getItem(Integer itemId) {
		Item item = new Item();
		ResultSet rs = queryManager.executeQuery("select * from items where id = " + itemId);
		try {
			if (rs.next()) {
				item = getItem(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public static List<Item> getItems() {
		List<Item> items = new ArrayList<Item>();
		ResultSet rs = queryManager.executeQuery("select * from items");
		try {
			while (rs.next()) {
				Item it = getItem(rs);
				items.add(it);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public static List<ItemCategory> getItemCategories(Integer itemId) {
		List<ItemCategory> cats = new ArrayList<ItemCategory>();
		String sql = "select iic.*, ic.name from itemItemCategories iic, itemCategories ic" +
				" where ic.id = iic.itemCategoryId and iic.itemId = " + itemId;
		ResultSet rs = queryManager.executeQuery(sql);
		try {
			while (rs.next()) {
				ItemCategory cat = new ItemCategory();
				cat.setId(rs.getInt("itemCategoryId"));
				cat.setName(rs.getString("name"));
				cats.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cats;
	}

}
