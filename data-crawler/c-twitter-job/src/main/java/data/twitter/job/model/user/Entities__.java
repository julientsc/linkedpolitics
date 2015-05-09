
package data.twitter.job.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Entities__ implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3857797216902193154L;
	@Expose
    private List<Hashtag_> hashtags = new ArrayList<Hashtag_>();
    @Expose
    private List<Object> symbols = new ArrayList<Object>();
    @Expose
    private List<Url___> urls = new ArrayList<Url___>();
    @SerializedName("user_mentions")
    @Expose
    private List<UserMention_> userMentions = new ArrayList<UserMention_>();

    public List<Hashtag_> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag_> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Object> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Object> symbols) {
        this.symbols = symbols;
    }

    public List<Url___> getUrls() {
        return urls;
    }

    public void setUrls(List<Url___> urls) {
        this.urls = urls;
    }

    public List<UserMention_> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMention_> userMentions) {
        this.userMentions = userMentions;
    }

}
