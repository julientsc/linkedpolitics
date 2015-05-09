
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Limits  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6809946755100284288L;
	@SerializedName("rate_limit_context")
    @Expose
    private RateLimitContext rateLimitContext;
    @Expose
    private Resources resources;

    public RateLimitContext getRateLimitContext() {
        return rateLimitContext;
    }

    public void setRateLimitContext(RateLimitContext rateLimitContext) {
        this.rateLimitContext = rateLimitContext;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

}
