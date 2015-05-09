
package data.twitter.job.model.user;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RetweetedStatus implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3762218631059168268L;
	@SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @Expose
    private String text;
    @Expose
    private String source;
    @Expose
    private Boolean truncated;
    @SerializedName("in_reply_to_status_id")
    @Expose
    private Object inReplyToStatusId;
    @SerializedName("in_reply_to_status_id_str")
    @Expose
    private Object inReplyToStatusIdStr;
    @SerializedName("in_reply_to_user_id")
    @Expose
    private Object inReplyToUserId;
    @SerializedName("in_reply_to_user_id_str")
    @Expose
    private Object inReplyToUserIdStr;
    @SerializedName("in_reply_to_screen_name")
    @Expose
    private Object inReplyToScreenName;
    @Expose
    private Object geo;
    @Expose
    private Object coordinates;
    @Expose
    private Object place;
    @Expose
    private Object contributors;
    @SerializedName("retweet_count")
    @Expose
    private Integer retweetCount;
    @SerializedName("favorite_count")
    @Expose
    private Integer favoriteCount;
    @Expose
    private Entities_ entities;
    @Expose
    private Boolean favorited;
    @Expose
    private Boolean retweeted;
    @SerializedName("possibly_sensitive")
    @Expose
    private Boolean possiblySensitive;
    @Expose
    private String lang;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public Object getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(Object inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Object getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    public void setInReplyToStatusIdStr(Object inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    public Object getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Object inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public Object getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    public void setInReplyToUserIdStr(Object inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    public Object getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(Object inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public Object getPlace() {
        return place;
    }

    public void setPlace(Object place) {
        this.place = place;
    }

    public Object getContributors() {
        return contributors;
    }

    public void setContributors(Object contributors) {
        this.contributors = contributors;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Entities_ getEntities() {
        return entities;
    }

    public void setEntities(Entities_ entities) {
        this.entities = entities;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
