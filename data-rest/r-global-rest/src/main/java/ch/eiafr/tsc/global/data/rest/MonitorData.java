package ch.eiafr.tsc.global.data.rest;

import java.util.Date;

public class MonitorData  {
	private String param;
	private Date lastUpdate;
	private Boolean isActive;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean active() {
		return this.isActive;
	}
}