package data.twitter.job.model.tweet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Url_ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3833861584059415347L;
	@Expose
	private String url;
	@SerializedName("expanded_url")
	@Expose
	private String expandedUrl;
	@SerializedName("display_url")
	@Expose
	private String displayUrl;
	@Expose
	private List<Integer> indices = new ArrayList<Integer>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExpandedUrl() {
		return expandedUrl;
	}

	public void setExpandedUrl(String expandedUrl) {
		this.expandedUrl = expandedUrl;
	}

	public String getDisplayUrl() {
		return displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

}
