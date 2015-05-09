package data.twitter.model;

public class RelatedUserData {
	String id_str;
	String name;
	String screen_name;
	String link;
	public String getId() {
		return id_str;
	}
	public void setId(String id_str) {
		this.id_str = id_str;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
