package ch.eiafr.tsc.data.rss.model;

import java.util.Date;


public class RSSItem {
	private String title;
	private String description;
	private String link;
	private Date pubDate;

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	@Override
	public String toString(){
		String s = "";
		if (title != null)
			s += "Title: " + title + "\n";
		if (link != null)
			s += "Link: " + link + "\n";
		if (description != null)
			s += "Description: " + description + "\n";
		if (pubDate != null)
			s += "PubDate: " + pubDate + "\n";
		return s;
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass()!=this.getClass())
			return false;
		RSSItem i2 = (RSSItem)o;
		if(i2.title.equals(title))
			return true;
		return false;
	}

}
