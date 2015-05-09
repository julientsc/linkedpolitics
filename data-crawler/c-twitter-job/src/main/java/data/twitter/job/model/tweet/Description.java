package data.twitter.job.model.tweet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Description implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 568015908835902853L;
	@Expose
	private List<Object> urls = new ArrayList<Object>();

	public List<Object> getUrls() {
		return urls;
	}

	public void setUrls(List<Object> urls) {
		this.urls = urls;
	}

}
