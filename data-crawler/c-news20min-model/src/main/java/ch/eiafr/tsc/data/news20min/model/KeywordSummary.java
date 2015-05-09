package ch.eiafr.tsc.data.news20min.model;

import java.util.Date;

public class KeywordSummary {
	private String param;
	private String link;
	private Date lastUpdate;
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
