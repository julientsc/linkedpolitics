
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Users  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4048528240527417774L;
	@SerializedName("/users/profile_banner")
    @Expose
    private UsersProfileBanner usersProfileBanner;
    @SerializedName("/users/suggestions/:slug/members")
    @Expose
    private UsersSuggestionsSlugMembers usersSuggestionsSlugMembers;
    @SerializedName("/users/show/:id")
    @Expose
    private UsersShowId usersShowId;
    @SerializedName("/users/suggestions")
    @Expose
    private UsersSuggestions usersSuggestions;
    @SerializedName("/users/lookup")
    @Expose
    private UsersLookup usersLookup;
    @SerializedName("/users/search")
    @Expose
    private UsersSearch usersSearch;
    @SerializedName("/users/contributors")
    @Expose
    private UsersContributors usersContributors;
    @SerializedName("/users/derived_info")
    @Expose
    private UsersDerivedInfo usersDerivedInfo;
    @SerializedName("/users/contributees")
    @Expose
    private UsersContributees usersContributees;
    @SerializedName("/users/report_spam")
    @Expose
    private UsersReportSpam usersReportSpam;
    @SerializedName("/users/suggestions/:slug")
    @Expose
    private UsersSuggestionsSlug usersSuggestionsSlug;

    public UsersProfileBanner getUsersProfileBanner() {
        return usersProfileBanner;
    }

    public void setUsersProfileBanner(UsersProfileBanner usersProfileBanner) {
        this.usersProfileBanner = usersProfileBanner;
    }

    public UsersSuggestionsSlugMembers getUsersSuggestionsSlugMembers() {
        return usersSuggestionsSlugMembers;
    }

    public void setUsersSuggestionsSlugMembers(UsersSuggestionsSlugMembers usersSuggestionsSlugMembers) {
        this.usersSuggestionsSlugMembers = usersSuggestionsSlugMembers;
    }

    public UsersShowId getUsersShowId() {
        return usersShowId;
    }

    public void setUsersShowId(UsersShowId usersShowId) {
        this.usersShowId = usersShowId;
    }

    public UsersSuggestions getUsersSuggestions() {
        return usersSuggestions;
    }

    public void setUsersSuggestions(UsersSuggestions usersSuggestions) {
        this.usersSuggestions = usersSuggestions;
    }

    public UsersLookup getUsersLookup() {
        return usersLookup;
    }

    public void setUsersLookup(UsersLookup usersLookup) {
        this.usersLookup = usersLookup;
    }

    public UsersSearch getUsersSearch() {
        return usersSearch;
    }

    public void setUsersSearch(UsersSearch usersSearch) {
        this.usersSearch = usersSearch;
    }

    public UsersContributors getUsersContributors() {
        return usersContributors;
    }

    public void setUsersContributors(UsersContributors usersContributors) {
        this.usersContributors = usersContributors;
    }

    public UsersDerivedInfo getUsersDerivedInfo() {
        return usersDerivedInfo;
    }

    public void setUsersDerivedInfo(UsersDerivedInfo usersDerivedInfo) {
        this.usersDerivedInfo = usersDerivedInfo;
    }

    public UsersContributees getUsersContributees() {
        return usersContributees;
    }

    public void setUsersContributees(UsersContributees usersContributees) {
        this.usersContributees = usersContributees;
    }

    public UsersReportSpam getUsersReportSpam() {
        return usersReportSpam;
    }

    public void setUsersReportSpam(UsersReportSpam usersReportSpam) {
        this.usersReportSpam = usersReportSpam;
    }

    public UsersSuggestionsSlug getUsersSuggestionsSlug() {
        return usersSuggestionsSlug;
    }

    public void setUsersSuggestionsSlug(UsersSuggestionsSlug usersSuggestionsSlug) {
        this.usersSuggestionsSlug = usersSuggestionsSlug;
    }

}
