package com.fjxokt.lolclient;

import com.fjxokt.lolclient.audio.Sounds;
import com.fjxokt.lolclient.ui.LoginWin;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.util.Properties;
import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		
        // for windows users
		try {   
                        // setup the look and feel properties
                        Properties props = HiFiLookAndFeel.getThemeProperties("Large-Font");                       
                        props.put("logoString", "NoAir Client");
                        
                        HiFiLookAndFeel.setCurrentTheme(props);
                        
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (Exception e) {}
		
		
		// run the main win
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// init sounds
				new Sounds();
				// start ui
				new LoginWin();
			}
		});
	}

}
