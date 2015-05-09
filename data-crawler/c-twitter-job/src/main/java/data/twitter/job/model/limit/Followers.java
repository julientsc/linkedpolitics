
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Followers implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7095756526890590430L;
	@SerializedName("/followers/list")
    @Expose
    private FollowersList followersList;
    @SerializedName("/followers/ids")
    @Expose
    private FollowersIds followersIds;

    public FollowersList getFollowersList() {
        return followersList;
    }

    public void setFollowersList(FollowersList followersList) {
        this.followersList = followersList;
    }

    public FollowersIds getFollowersIds() {
        return followersIds;
    }

    public void setFollowersIds(FollowersIds followersIds) {
        this.followersIds = followersIds;
    }

}
