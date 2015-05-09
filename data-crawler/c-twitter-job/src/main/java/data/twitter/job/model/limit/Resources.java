
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Resources  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4699242990192493281L;
	@Expose
    private Lists lists;
    @Expose
    private Application application;
    @Expose
    private Mutes mutes;
    @Expose
    private Friendships friendships;
    @Expose
    private Blocks blocks;
    @Expose
    private Geo geo;
    @Expose
    private Users users;
    @Expose
    private Followers followers;
    @Expose
    private Statuses statuses;
    @Expose
    private Help help;
    @Expose
    private Friends friends;
    @SerializedName("direct_messages")
    @Expose
    private DirectMessages directMessages;
    @Expose
    private Account account;
    @Expose
    private Favorites favorites;
    @Expose
    private Device device;
    @SerializedName("saved_searches")
    @Expose
    private SavedSearches savedSearches;
    @Expose
    private Search search;
    @Expose
    private Trends trends;

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Mutes getMutes() {
        return mutes;
    }

    public void setMutes(Mutes mutes) {
        this.mutes = mutes;
    }

    public Friendships getFriendships() {
        return friendships;
    }

    public void setFriendships(Friendships friendships) {
        this.friendships = friendships;
    }

    public Blocks getBlocks() {
        return blocks;
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    public Statuses getStatuses() {
        return statuses;
    }

    public void setStatuses(Statuses statuses) {
        this.statuses = statuses;
    }

    public Help getHelp() {
        return help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    public DirectMessages getDirectMessages() {
        return directMessages;
    }

    public void setDirectMessages(DirectMessages directMessages) {
        this.directMessages = directMessages;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public SavedSearches getSavedSearches() {
        return savedSearches;
    }

    public void setSavedSearches(SavedSearches savedSearches) {
        this.savedSearches = savedSearches;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Trends getTrends() {
        return trends;
    }

    public void setTrends(Trends trends) {
        this.trends = trends;
    }

}
