package ch.eiafr.tsc.data.facebook.model;

public class PostsData {
	String id;
	String message;
	String link;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public void updateLink(String pageId){
		link = "/page/"+pageId+"/posts/"+id+"/";
	}
}
