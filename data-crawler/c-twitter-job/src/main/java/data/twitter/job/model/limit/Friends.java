
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Friends  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6539972610661338953L;
	@SerializedName("/friends/ids")
    @Expose
    private FriendsIds friendsIds;
    @SerializedName("/friends/list")
    @Expose
    private FriendsList friendsList;
    @SerializedName("/friends/following/ids")
    @Expose
    private FriendsFollowingIds friendsFollowingIds;
    @SerializedName("/friends/following/list")
    @Expose
    private FriendsFollowingList friendsFollowingList;

    public FriendsIds getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(FriendsIds friendsIds) {
        this.friendsIds = friendsIds;
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public FriendsFollowingIds getFriendsFollowingIds() {
        return friendsFollowingIds;
    }

    public void setFriendsFollowingIds(FriendsFollowingIds friendsFollowingIds) {
        this.friendsFollowingIds = friendsFollowingIds;
    }

    public FriendsFollowingList getFriendsFollowingList() {
        return friendsFollowingList;
    }

    public void setFriendsFollowingList(FriendsFollowingList friendsFollowingList) {
        this.friendsFollowingList = friendsFollowingList;
    }

}
