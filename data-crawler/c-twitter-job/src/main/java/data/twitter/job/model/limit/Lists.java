
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Lists  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -342311170603155108L;
	@SerializedName("/lists/subscribers")
    @Expose
    private ListsSubscribers listsSubscribers;
    @SerializedName("/lists/memberships")
    @Expose
    private ListsMemberships listsMemberships;
    @SerializedName("/lists/list")
    @Expose
    private ListsList listsList;
    @SerializedName("/lists/ownerships")
    @Expose
    private ListsOwnerships listsOwnerships;
    @SerializedName("/lists/subscriptions")
    @Expose
    private ListsSubscriptions listsSubscriptions;
    @SerializedName("/lists/members")
    @Expose
    private ListsMembers listsMembers;
    @SerializedName("/lists/subscribers/show")
    @Expose
    private ListsSubscribersShow listsSubscribersShow;
    @SerializedName("/lists/statuses")
    @Expose
    private ListsStatuses listsStatuses;
    @SerializedName("/lists/members/show")
    @Expose
    private ListsMembersShow listsMembersShow;
    @SerializedName("/lists/show")
    @Expose
    private ListsShow listsShow;

    public ListsSubscribers getListsSubscribers() {
        return listsSubscribers;
    }

    public void setListsSubscribers(ListsSubscribers listsSubscribers) {
        this.listsSubscribers = listsSubscribers;
    }

    public ListsMemberships getListsMemberships() {
        return listsMemberships;
    }

    public void setListsMemberships(ListsMemberships listsMemberships) {
        this.listsMemberships = listsMemberships;
    }

    public ListsList getListsList() {
        return listsList;
    }

    public void setListsList(ListsList listsList) {
        this.listsList = listsList;
    }

    public ListsOwnerships getListsOwnerships() {
        return listsOwnerships;
    }

    public void setListsOwnerships(ListsOwnerships listsOwnerships) {
        this.listsOwnerships = listsOwnerships;
    }

    public ListsSubscriptions getListsSubscriptions() {
        return listsSubscriptions;
    }

    public void setListsSubscriptions(ListsSubscriptions listsSubscriptions) {
        this.listsSubscriptions = listsSubscriptions;
    }

    public ListsMembers getListsMembers() {
        return listsMembers;
    }

    public void setListsMembers(ListsMembers listsMembers) {
        this.listsMembers = listsMembers;
    }

    public ListsSubscribersShow getListsSubscribersShow() {
        return listsSubscribersShow;
    }

    public void setListsSubscribersShow(ListsSubscribersShow listsSubscribersShow) {
        this.listsSubscribersShow = listsSubscribersShow;
    }

    public ListsStatuses getListsStatuses() {
        return listsStatuses;
    }

    public void setListsStatuses(ListsStatuses listsStatuses) {
        this.listsStatuses = listsStatuses;
    }

    public ListsMembersShow getListsMembersShow() {
        return listsMembersShow;
    }

    public void setListsMembersShow(ListsMembersShow listsMembersShow) {
        this.listsMembersShow = listsMembersShow;
    }

    public ListsShow getListsShow() {
        return listsShow;
    }

    public void setListsShow(ListsShow listsShow) {
        this.listsShow = listsShow;
    }

}
