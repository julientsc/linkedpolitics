
package data.twitter.job.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Entities_ implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5934455039359941121L;
	@Expose
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();
    @Expose
    private List<Object> symbols = new ArrayList<Object>();
    @Expose
    private List<Url__> urls = new ArrayList<Url__>();
    @SerializedName("user_mentions")
    @Expose
    private List<UserMention> userMentions = new ArrayList<UserMention>();

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
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

    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

}
