
package ch.eiafr.tsc.data.facebook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Page implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7263306549380262511L;
	private String id;
    private String about;
    private boolean canPost;
    private String category;
    private List<CategoryList> categoryList = new ArrayList<CategoryList>();
    private long checkins;
    private String companyOverview;
    private Cover cover;
    private String founded;
    private boolean hasAddedApp;
    private boolean isCommunityPage;
    private boolean isPublished;
    private long likes;
    private String link;
    private Location location;
    private String mission;
    private String name;
    private Parking parking;
    private String products;
    private long talkingAboutCount;
    private String username;
    private String website;
    private long wereHereCount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public long getCheckins() {
        return checkins;
    }

    public void setCheckins(long checkins) {
        this.checkins = checkins;
    }

    public String getCompanyOverview() {
        return companyOverview;
    }

    public void setCompanyOverview(String companyOverview) {
        this.companyOverview = companyOverview;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public boolean isHasAddedApp() {
        return hasAddedApp;
    }

    public void setHasAddedApp(boolean hasAddedApp) {
        this.hasAddedApp = hasAddedApp;
    }

    public boolean isIsCommunityPage() {
        return isCommunityPage;
    }

    public void setIsCommunityPage(boolean isCommunityPage) {
        this.isCommunityPage = isCommunityPage;
    }

    public boolean isIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public long getTalkingAboutCount() {
        return talkingAboutCount;
    }

    public void setTalkingAboutCount(long talkingAboutCount) {
        this.talkingAboutCount = talkingAboutCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getWereHereCount() {
        return wereHereCount;
    }

    public void setWereHereCount(long wereHereCount) {
        this.wereHereCount = wereHereCount;
    }
    
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
