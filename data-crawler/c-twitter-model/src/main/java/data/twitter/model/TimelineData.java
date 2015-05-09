package data.twitter.model;

public class TimelineData {
	private String id_str;
	private String text;
	private String link;
	public String getId_str() {
		return id_str;
	}
	public void setId_str(String id_str) {
		this.id_str = id_str;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void updateLink(String userId) {
		this.link = "/profils/"+userId+"/timeline/"+id_str+"/";
	}
	
}
