
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class GeoSimilarPlaces implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4512686350473055054L;
	@Expose
    private Integer limit;
    @Expose
    private Integer remaining;
    @Expose
    private Integer reset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }

}
