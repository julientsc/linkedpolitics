package data.gnews.model;

import java.util.Date;

public class MonitorData {
	String keywork;
	Date lastUpdate;
	public String getKeywork() {
		return keywork;
	}
	public void setKeywork(String keywork) {
		this.keywork = keywork;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
