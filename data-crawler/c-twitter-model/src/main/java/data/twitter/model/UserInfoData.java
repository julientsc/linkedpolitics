package data.twitter.model;

import java.util.Date;

public class UserInfoData {
	private String id_str;
	private String name;
	private String screen_name;
	private String location;
	private String description;
	private long followers_count;
	private String followers_link;
	private long friends_count;
	private String friends_link;
	private long statuses_count;
	private String statuses_link;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(long followers_count) {
		this.followers_count = followers_count;
	}
	public String getFollowers_link() {
		return followers_link;
	}
	public void setFollowers_link(String followers_link) {
		this.followers_link = followers_link;
	}
	public long getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(long friends_count) {
		this.friends_count = friends_count;
	}
	public String getFriends_link() {
		return friends_link;
	}
	public void setFriends_link(String friends_link) {
		this.friends_link = friends_link;
	}
	public long getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(long statuses_count) {
		this.statuses_count = statuses_count;
	}
	public String getStatuses_link() {
		return statuses_link;
	}
	public void setStatuses_link(String statuses_link) {
		this.statuses_link = statuses_link;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public void updateLink(boolean friends, boolean followers, boolean statuses){
		if(friends)
			friends_link = "/profils/"+id_str+"/friends/";
		if(followers)
			followers_link = "/profils/"+id_str+"/followers/";
		if(statuses)
			statuses_link = "/profils/"+id_str+"/timeline/";
	}
}
