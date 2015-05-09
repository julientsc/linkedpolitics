package ch.eiafr.tsc.data.facebook.model;

import java.util.ArrayList;
import java.util.List;

public class Comments {
	private List<Comment> data = new ArrayList<Comment>();
	private Paging paging;
	public List<Comment> getData() {
		return data;
	}
	public void setData(List<Comment> data) {
		this.data = data;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
	
}
