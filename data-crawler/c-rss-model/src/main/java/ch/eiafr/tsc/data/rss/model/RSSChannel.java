package ch.eiafr.tsc.data.rss.model;

import java.util.ArrayList;
import java.util.Date;

public class RSSChannel {
	private String title;
	private String source;
	private String description;
	private String language;
	private Date pubDate;
	private Date lastBuildDate;
	private ArrayList<RSSItem> items;
	private String copyright;
	private String link;

	public RSSChannel() {
		items = new ArrayList<RSSItem>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public ArrayList<RSSItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<RSSItem> items) {
		this.items = items;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		String s = "";
		if (title != null)
			s += "Title: " + title + "\n";
		if (link != null)
			s += "Link: " + link + "\n";
		if (description != null)
			s += "Description: " + description + "\n";
		if (language != null)
			s += "Language: " + language + "\n";
		if (copyright != null)
			s += "Copyright: " + copyright + "\n";
		if (pubDate != null)
			s += "PubDate: " + pubDate + "\n";
		if (lastBuildDate != null)
			s += "LastBuildDate: " + lastBuildDate + "\n";
		if (items != null)
			s += "Items: \n" + items.toString();
		return s;
	}

	public void updateBasicData(RSSChannel c2) {
		if (c2.copyright != null)
			copyright = c2.copyright;
		if (c2.description != null)
			description = c2.description;
		if (c2.language != null)
			language = c2.language;
		if (c2.lastBuildDate != null)
			lastBuildDate = c2.lastBuildDate;
		if (c2.pubDate != null)
			pubDate = c2.pubDate;
		if (c2.title != null)
			title = c2.title;
		if (c2.link != null)
			link = c2.link;
	}

}
