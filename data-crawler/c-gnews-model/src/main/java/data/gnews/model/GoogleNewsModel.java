package data.gnews.model;

import java.util.ArrayList;

public class GoogleNewsModel {
	private String keyword;
	private ArrayList<GoogleNew> news;
	
	public GoogleNewsModel(){
		news = new ArrayList<>();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<GoogleNew> getNews() {
		return news;
	}

	public void setNews(ArrayList<GoogleNew> news) {
		this.news = news;
	}
}
