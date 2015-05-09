package ch.eiafr.tsc.global.data.rest;

import java.util.ArrayList;
import java.util.Date;

import ch.eiafr.tsc.data.global.job.JobExecutor;

public class MonitorTask implements Runnable {
	private ArrayList<String> params;
	private boolean forceStart = false;
	private boolean running = false;
	private boolean enabled = false;
	private boolean stop = false;
	private int timer;
	private JobExecutor jobExecutor;
	private Date lastExec = new Date(0);
	private static final Object verrouList = new Object();

	@SuppressWarnings("unchecked")
	public MonitorTask(MonitorList monitorList, JobExecutor jobExecutor) {
		this.params = ((ArrayList<String>) monitorList.getParams().clone());
		this.timer = monitorList.getTimer();
		this.jobExecutor = jobExecutor;
	}

	@SuppressWarnings("unchecked")
	public void setList(ArrayList<String> list) {
		synchronized (verrouList) {
			this.params = ((ArrayList<String>) list.clone());
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (!stop) {
			try {
				running = true;
				ArrayList<String> localParams;
				synchronized (verrouList) {
					localParams = ((ArrayList<String>) params.clone());
				}
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
				jobExecutor.doProc(localParams);

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
