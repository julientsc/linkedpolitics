
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Mutes  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8378722650434689953L;
	@SerializedName("/mutes/users/list")
    @Expose
    private MutesUsersList mutesUsersList;
    @SerializedName("/mutes/users/ids")
    @Expose
    private MutesUsersIds mutesUsersIds;

    public MutesUsersList getMutesUsersList() {
        return mutesUsersList;
    }

    public void setMutesUsersList(MutesUsersList mutesUsersList) {
        this.mutesUsersList = mutesUsersList;
    }

    public MutesUsersIds getMutesUsersIds() {
        return mutesUsersIds;
    }

    public void setMutesUsersIds(MutesUsersIds mutesUsersIds) {
        this.mutesUsersIds = mutesUsersIds;
    }

}
