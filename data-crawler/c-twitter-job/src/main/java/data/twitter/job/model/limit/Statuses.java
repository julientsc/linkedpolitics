
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Statuses  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2036554075375398112L;
	@SerializedName("/statuses/mentions_timeline")
    @Expose
    private StatusesMentionsTimeline statusesMentionsTimeline;
    @SerializedName("/statuses/lookup")
    @Expose
    private StatusesLookup statusesLookup;
    @SerializedName("/statuses/show/:id")
    @Expose
    private StatusesShowId statusesShowId;
    @SerializedName("/statuses/oembed")
    @Expose
    private StatusesOembed statusesOembed;
    @SerializedName("/statuses/retweeters/ids")
    @Expose
    private StatusesRetweetersIds statusesRetweetersIds;
    @SerializedName("/statuses/home_timeline")
    @Expose
    private StatusesHomeTimeline statusesHomeTimeline;
    @SerializedName("/statuses/user_timeline")
    @Expose
    private StatusesUserTimeline statusesUserTimeline;
    @SerializedName("/statuses/friends")
    @Expose
    private StatusesFriends statusesFriends;
    @SerializedName("/statuses/retweets/:id")
    @Expose
    private StatusesRetweetsId statusesRetweetsId;
    @SerializedName("/statuses/retweets_of_me")
    @Expose
    private StatusesRetweetsOfMe statusesRetweetsOfMe;

    public StatusesMentionsTimeline getStatusesMentionsTimeline() {
        return statusesMentionsTimeline;
    }

    public void setStatusesMentionsTimeline(StatusesMentionsTimeline statusesMentionsTimeline) {
        this.statusesMentionsTimeline = statusesMentionsTimeline;
    }

    public StatusesLookup getStatusesLookup() {
        return statusesLookup;
    }

    public void setStatusesLookup(StatusesLookup statusesLookup) {
        this.statusesLookup = statusesLookup;
    }

    public StatusesShowId getStatusesShowId() {
        return statusesShowId;
    }

    public void setStatusesShowId(StatusesShowId statusesShowId) {
        this.statusesShowId = statusesShowId;
    }

    public StatusesOembed getStatusesOembed() {
        return statusesOembed;
    }

    public void setStatusesOembed(StatusesOembed statusesOembed) {
        this.statusesOembed = statusesOembed;
    }

    public StatusesRetweetersIds getStatusesRetweetersIds() {
        return statusesRetweetersIds;
    }

    public void setStatusesRetweetersIds(StatusesRetweetersIds statusesRetweetersIds) {
        this.statusesRetweetersIds = statusesRetweetersIds;
    }

    public StatusesHomeTimeline getStatusesHomeTimeline() {
        return statusesHomeTimeline;
    }

    public void setStatusesHomeTimeline(StatusesHomeTimeline statusesHomeTimeline) {
        this.statusesHomeTimeline = statusesHomeTimeline;
    }

    public StatusesUserTimeline getStatusesUserTimeline() {
        return statusesUserTimeline;
    }

    public void setStatusesUserTimeline(StatusesUserTimeline statusesUserTimeline) {
        this.statusesUserTimeline = statusesUserTimeline;
    }

    public StatusesFriends getStatusesFriends() {
        return statusesFriends;
    }

    public void setStatusesFriends(StatusesFriends statusesFriends) {
        this.statusesFriends = statusesFriends;
    }

    public StatusesRetweetsId getStatusesRetweetsId() {
        return statusesRetweetsId;
    }

    public void setStatusesRetweetsId(StatusesRetweetsId statusesRetweetsId) {
        this.statusesRetweetsId = statusesRetweetsId;
    }

    public StatusesRetweetsOfMe getStatusesRetweetsOfMe() {
        return statusesRetweetsOfMe;
    }

    public void setStatusesRetweetsOfMe(StatusesRetweetsOfMe statusesRetweetsOfMe) {
        this.statusesRetweetsOfMe = statusesRetweetsOfMe;
    }

}
