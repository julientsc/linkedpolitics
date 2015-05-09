package data.twitter.model;

public class StatusData {
	private String id_str;
	private String text;
	private String created_at;
	private long retweet_count;
	private long favorite_count;
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
	public String getDate() {
		return created_at;
	}
	public void setDate(String date) {
		this.created_at = date;
	}
	public long getRetweet_count() {
		return retweet_count;
	}
	public void setRetweet_count(long retweet_count) {
		this.retweet_count = retweet_count;
	}
	public long getFavorite_count() {
		return favorite_count;
	}
	public void setFavorite_count(long favorite_count) {
		this.favorite_count = favorite_count;
	}
	
	
	
}
