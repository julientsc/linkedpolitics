
package data.twitter.job.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class UserMention implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7607709660590051273L;
	@SerializedName("screen_name")
    @Expose
    private String screenName;
    @Expose
    private String name;
 
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @Expose
    private List<Integer> indices = new ArrayList<Integer>();

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

}
