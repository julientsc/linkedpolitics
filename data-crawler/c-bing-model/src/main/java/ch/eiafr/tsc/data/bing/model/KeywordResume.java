package ch.eiafr.tsc.data.bing.model;

import java.util.Date;

public class KeywordResume {
	private String keyword;
	private String link;
	private Date lastUpdate;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLink() {
		return link;
	}
	public void updateLink() {
		this.link = "/list/"+keyword+"/";
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}	
}