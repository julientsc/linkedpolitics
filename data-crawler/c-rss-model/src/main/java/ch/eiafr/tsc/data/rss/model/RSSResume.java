package ch.eiafr.tsc.data.rss.model;

import java.util.Date;

public class RSSResume {
	private String title;
	private String description;
	private String website;
	private String link;
	private Date lastRetrival;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getLastRetrival() {
		return lastRetrival;
	}
	public void setLastRetrival(Date lastRetrival) {
		this.lastRetrival = lastRetrival;
	}
	
	
}
