package data.twitter.model;

import java.util.Date;

public class UserListData {
	private String id_str;
	private String name;
	private String screen_name;
	private String link;
	private Date lastUpdate;
	
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
	public void updateLink() {
		link = "/profils/"+id_str+"/";
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
