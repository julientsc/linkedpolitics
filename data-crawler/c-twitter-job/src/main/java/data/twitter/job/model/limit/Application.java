
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Application  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5964011306457812882L;
	@SerializedName("/application/rate_limit_status")
    @Expose
    private ApplicationRateLimitStatus applicationRateLimitStatus;

    public ApplicationRateLimitStatus getApplicationRateLimitStatus() {
        return applicationRateLimitStatus;
    }

    public void setApplicationRateLimitStatus(ApplicationRateLimitStatus applicationRateLimitStatus) {
        this.applicationRateLimitStatus = applicationRateLimitStatus;
    }

}
