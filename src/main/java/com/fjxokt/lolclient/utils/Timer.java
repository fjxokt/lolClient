package com.fjxokt.lolclient.utils;

public class Timer {
	
	public interface TimerUpdater {
		void updatedTick(long value);
	}
	
	private Thread curThread;
	private boolean stop;
	private long duration = 0;
	private int tick;
	private TimerUpdater updater;
	
	protected Timer() {}
	
	public Timer(TimerUpdater updater, int tick) {
		this.updater = updater;
		this.tick = tick;
	}
	
	private Thread createThread() {
		Thread curThread = new Thread() {
                    @Override
            public void run() {
            	tick(this);
            }
        };
        curThread.setName("(Timer)");
        curThread.setDaemon(true);
        return curThread;
	}
	
	public void stop() {
		stop = true;
		duration = 0;
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
		while (curThread == thread && !stop) {
			try {
				updater.updatedTick(duration);
				Thread.sleep(tick);
				duration += tick;
			} catch (InterruptedException e) {
			}
		}
	}
}
