package data.gnews.model;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class GoogleNew {

	String title;
	List<URL> links;
	Date pubDate;

	public String getTitle() {
		return title;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public List<URL> getLinks() {
		return links;
	}

	public void setLinks(List<URL> links) {
		this.links = links;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		String t = "";
		t += "Title : " + getTitle() + "\n";
		// t += "Link : " + getLink() + "\n";
		t += "Date : " + getPubDate().toString() + "\n";
		return t;
	}
	
	@Override
	public boolean equals(Object other){
		if(other.getClass()!=this.getClass())
			return false;
		GoogleNew otherNew = (GoogleNew)other;
		if(!this.getTitle().equals(otherNew.getTitle()))
			return false;
		return true;
	}
}
