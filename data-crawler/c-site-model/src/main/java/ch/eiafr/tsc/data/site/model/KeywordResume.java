package ch.eiafr.tsc.data.site.model;

import java.util.Date;

public class KeywordResume {

	private String param;
	private String link;
	private Date lastUpdate;

	public String getParam() {
		return param;
	}

	public void setParam(String keyword) {
		this.param = keyword;
	}

	public String getLink() {
		return link;
	}

	public void updateLink() {
		this.link = "/list/" + param + "/";
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}