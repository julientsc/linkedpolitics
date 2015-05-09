
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Friendships  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8778445812181226408L;
	@SerializedName("/friendships/incoming")
    @Expose
    private FriendshipsIncoming friendshipsIncoming;
    @SerializedName("/friendships/lookup")
    @Expose
    private FriendshipsLookup friendshipsLookup;
    @SerializedName("/friendships/outgoing")
    @Expose
    private FriendshipsOutgoing friendshipsOutgoing;
    @SerializedName("/friendships/no_retweets/ids")
    @Expose
    private FriendshipsNoRetweetsIds friendshipsNoRetweetsIds;
    @SerializedName("/friendships/show")
    @Expose
    private FriendshipsShow friendshipsShow;

    public FriendshipsIncoming getFriendshipsIncoming() {
        return friendshipsIncoming;
    }

    public void setFriendshipsIncoming(FriendshipsIncoming friendshipsIncoming) {
        this.friendshipsIncoming = friendshipsIncoming;
    }

    public FriendshipsLookup getFriendshipsLookup() {
        return friendshipsLookup;
    }

    public void setFriendshipsLookup(FriendshipsLookup friendshipsLookup) {
        this.friendshipsLookup = friendshipsLookup;
    }

    public FriendshipsOutgoing getFriendshipsOutgoing() {
        return friendshipsOutgoing;
    }

    public void setFriendshipsOutgoing(FriendshipsOutgoing friendshipsOutgoing) {
        this.friendshipsOutgoing = friendshipsOutgoing;
    }

    public FriendshipsNoRetweetsIds getFriendshipsNoRetweetsIds() {
        return friendshipsNoRetweetsIds;
    }

    public void setFriendshipsNoRetweetsIds(FriendshipsNoRetweetsIds friendshipsNoRetweetsIds) {
        this.friendshipsNoRetweetsIds = friendshipsNoRetweetsIds;
    }

    public FriendshipsShow getFriendshipsShow() {
        return friendshipsShow;
    }

    public void setFriendshipsShow(FriendshipsShow friendshipsShow) {
        this.friendshipsShow = friendshipsShow;
    }

}
