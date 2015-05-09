package ch.eiafr.tsc.data.bing.model;

import java.util.ArrayList;

public class BingResultModel {
	private String keyword;
	
	private ArrayList<String> ids;
	
	public BingResultModel(){
		ids = new ArrayList<>();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<String> getIDs() {
		return ids;
	}

	public void setIDs(ArrayList<String> ids) {
		this.ids = ids;
	}

}