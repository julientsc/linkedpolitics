package ch.eiafr.tsc.data.facebook.model;

import java.util.List;
import java.util.ArrayList;

public class Likes {
	private List<From> data = new ArrayList<From>();
	private Paging paging;
	public List<From> getData() {
		return data;
	}
	public void setData(List<From> data) {
		this.data = data;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
	
}
