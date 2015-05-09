package data.twitter.job.model.tweet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Entities_ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5757502010345715842L;
	@Expose
	private List<Object> hashtags = new ArrayList<Object>();
	@Expose
	private List<Object> symbols = new ArrayList<Object>();
	@Expose
	private List<Url__> urls = new ArrayList<Url__>();
	@SerializedName("user_mentions")
	@Expose
	private List<Object> userMentions = new ArrayList<Object>();

	public List<Object> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Object> hashtags) {
		this.hashtags = hashtags;
	}

	public List<Object> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<Object> symbols) {
		this.symbols = symbols;
	}

	public List<Url__> getUrls() {
		return urls;
	}

	public void setUrls(List<Url__> urls) {
		this.urls = urls;
	}

	public List<Object> getUserMentions() {
		return userMentions;
	}

	public void setUserMentions(List<Object> userMentions) {
		this.userMentions = userMentions;
	}

}
