
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Trends implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5833875029743207282L;
	@SerializedName("/trends/available")
    @Expose
    private TrendsAvailable trendsAvailable;
    @SerializedName("/trends/place")
    @Expose
    private TrendsPlace trendsPlace;
    @SerializedName("/trends/closest")
    @Expose
    private TrendsClosest trendsClosest;

    public TrendsAvailable getTrendsAvailable() {
        return trendsAvailable;
    }

    public void setTrendsAvailable(TrendsAvailable trendsAvailable) {
        this.trendsAvailable = trendsAvailable;
    }

    public TrendsPlace getTrendsPlace() {
        return trendsPlace;
    }

    public void setTrendsPlace(TrendsPlace trendsPlace) {
        this.trendsPlace = trendsPlace;
    }

    public TrendsClosest getTrendsClosest() {
        return trendsClosest;
    }

    public void setTrendsClosest(TrendsClosest trendsClosest) {
        this.trendsClosest = trendsClosest;
    }

}
