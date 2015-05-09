
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Search  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4606677218673474854L;
	@SerializedName("/search/tweets")
    @Expose
    private SearchTweets searchTweets;

    public SearchTweets getSearchTweets() {
        return searchTweets;
    }

    public void setSearchTweets(SearchTweets searchTweets) {
        this.searchTweets = searchTweets;
    }

}
