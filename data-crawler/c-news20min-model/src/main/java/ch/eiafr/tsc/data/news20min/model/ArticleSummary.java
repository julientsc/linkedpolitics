package ch.eiafr.tsc.data.news20min.model;

import java.io.Serializable;
import java.util.Date;

public class ArticleSummary implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String linkRest;
	private String link20Min;
	private String title;
	private Date date;
	
	public String getLinkRest() {
		return linkRest;
	}
	public void setLinkRest(String linkRest) {
		this.linkRest = linkRest;
	}
	public String getLink20Min() {
		return link20Min;
	}
	public void setLink20Min(String link20Min) {
		this.link20Min = link20Min;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
