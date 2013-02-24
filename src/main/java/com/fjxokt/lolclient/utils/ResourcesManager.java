package com.fjxokt.lolclient.utils;

import com.fjxokt.lolclient.dao.LoLDao;
import com.fjxokt.lolclient.model.Champion;
import com.fjxokt.lolclient.model.Item;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ResourcesManager {

    private static ResourcesManager instance;
    private HashMap<String,ImageIcon> map;
    private HashMap<Integer, Champion> champions;
    private HashMap<Integer, Item> items;
    
    private ResourcesManager() {
    	map = new HashMap<String,ImageIcon>();
    	
    	// get all champions
    	champions = new HashMap<Integer, Champion>();
    	List<Champion> champs = LoLDao.getChampions();
    	for (Champion champ : champs) {
    		champions.put(champ.getId(), champ);
    	}
    	
    	// get all items
    	items = new HashMap<Integer, Item>();
    	List<Item> its = LoLDao.getItems();
    	for (Item it : its) {
    		items.put(it.getId(), it);
    	}
    }
    
    public static ResourcesManager getInst() {
        if (instance == null) {
            instance = new ResourcesManager();
        }
        return instance;
    }
    
    public ImageIcon getIcon(String res) {
    	return getIcon(res, false);
    }
    
    public ImageIcon getIcon(String res, boolean isResource) {
    	ImageIcon icon = map.get(res);
    	if (icon == null) {
    		icon = (isResource) ? new ImageIcon(this.getClass().getResource(res)) : new ImageIcon(res);
			map.put(res, icon);
    	}
    	return icon;
    }
    
    public ImageIcon getIcon(String res, boolean isResource, int width, int height) {
    	ImageIcon icon = map.get(res + width + height);
    	if (icon == null) {
    		Image img = null;
    		try {
	    		if (isResource) {
	    			img = ImageIO.read(this.getClass().getResource(res));
	    		}
	    		else {
	    			img = ImageIO.read(new File(res));
	    		}
    		} catch (IOException e) {
    			System.out.println("Can't read file: " + res);
    			//e.printStackTrace();
    		}
    		if (img == null) {
    			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    			icon = new ImageIcon(img);
    		}
    		else {
    			Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
				icon = new ImageIcon(resizedImage);
    		}
			
			map.put(res + width + height, icon);
    	}
    	return icon;
    }
    
    public Champion getChampion(Integer championId) {
    	Champion champ = champions.get(championId);
    	// should not happen, as I get the whole list when creating the ResourcesManager
    	if (champ == null) {
    		champ = LoLDao.getChampion(championId);
    		champions.put(championId, champ);
    	}
    	return champ;
    }
    
    public Item getItem(Integer itemId) {
    	Item item = items.get(itemId);
    	// should not happen, as I get the whole list when creating the ResourcesManager
    	if (item == null) {
    		item = LoLDao.getItem(itemId);
    		items.put(itemId, item);
    	}
    	return item;
    } 
    
}