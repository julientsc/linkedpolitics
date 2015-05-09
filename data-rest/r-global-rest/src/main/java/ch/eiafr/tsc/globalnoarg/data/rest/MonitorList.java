package ch.eiafr.tsc.globalnoarg.data.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class MonitorList {

	private int timer;
	private File timmerFile;

	public MonitorList(String configDir) {
		timmerFile = new File(configDir + "timer");
		timer = 1800;
		loadTimer();
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
		saveTimer();
	}
	
	private void loadTimer() {
		try {
			// read link list file
			if (timmerFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(
						timmerFile));
				String line;
				if ((line = br.readLine()) != null) {
					timer = Integer.valueOf(line);
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTimer() {
		try {
			PrintWriter writer = new PrintWriter(timmerFile, "UTF-8");
			writer.println(timer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
