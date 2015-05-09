package ch.eiafr.tsc.globalnoarg.data.rest;

import java.util.Date;

import ch.eiafr.tsc.data.globalnoarg.job.JobExecutor;

public class MonitorTask implements Runnable {
	private boolean forceStart = false;
	private boolean running = false;
	private boolean enabled = false;
	private boolean stop = false;
	private int timer;
	private JobExecutor jobExecutor;
	private Date lastExec = new Date(0);

	public MonitorTask(MonitorList monitorList, JobExecutor jobExecutor) {
		this.timer = monitorList.getTimer();
		this.jobExecutor = jobExecutor;
	}



	public boolean isRunning() {
		return running;
	}

	public void setTimer(int sec) {
		timer = sec;
	}

	public int getTimer() {
		return timer;
	}

	public void stop() {
		stop = true;
		enabled = false;
	}

	public void start() {
		stop = false;
		enabled = true;
		new Thread(this).start();
	}

	public void forceStart() {
		if (!isRunning())
			forceStart = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				running = true;
				Date dateEnd = new Date();
				lastExec = dateEnd;
				running = false;
				for (int i = 0; i < 10 * timer; i++) {
					Thread.sleep(100);
					if (forceStart || stop) {
						forceStart = false;
						break;
					}
				}
				jobExecutor.doProc();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Date getLastExec() {
		return lastExec;
	}

}
