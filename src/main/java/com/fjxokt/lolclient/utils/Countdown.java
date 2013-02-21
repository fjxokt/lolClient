package com.fjxokt.lolclient.utils;

public class Countdown {
	
	public interface CountdownUpdater {
		void countdownOver();
		void tick(long duration);
	}
	
	private Thread curThread;
	private boolean stop;
	private long duration;
	private int tick = 50;
	private CountdownUpdater updater;
	
	protected Countdown() {}
	
	public Countdown(CountdownUpdater updater, long duration) {
		this.updater = updater;
		this.duration = duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	private Thread createThread() {
		Thread curThread = new Thread() {
            public void run() {
            	tick(this);
            }
        };
        curThread.setName("(Countdown)");
        curThread.setDaemon(true);
        return curThread;
	}
	
	public void stop() {
		stop = true;
	}
	
	public void start() {
		curThread = createThread();
		stop = false;
        curThread.start();
	}
	
	public boolean isStarted() {
		if (curThread == null) {
			return false;
		}
		return curThread.isAlive();
	}
	
	public void tick(Thread thread) {
		long startTime = duration;
		while (curThread == thread && !stop && startTime > 0) {
			try {
				Thread.sleep(tick);
				startTime -= tick;
				updater.tick(startTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (startTime <= 0) {
			updater.countdownOver();
		}
	}
}
