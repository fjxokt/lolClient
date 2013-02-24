package com.fjxokt.lolclient.audio;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * A generic sound that can be created and then played multiple times.
 * @author Emily Mabrey emilymabrey93@gmail.com
 */
public class JSound extends PlaybackListener implements Runnable
{
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;    

    public  JSound(String filePath)
    {
        this.filePath = filePath;
    }

    public void play()
    {
        try
        {
            String urlAsString = 
                "file:///" 
                + new java.io.File(".").getCanonicalPath() 
                + "/" 
                + this.filePath;

            this.player = new AdvancedPlayer
            (
                new java.net.URL(urlAsString).openStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
        }
    }
    
    public void stop() {
    	if (player != null) {
    		player.stop();
    	}
    }

    // PlaybackListener members

    @Override
    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackStarted()");
    }

    @Override
    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackEnded()");
    }    

    // Runnable members

    public void run()
    {
        try
        {
            this.player.play();
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
        }

    }
}
