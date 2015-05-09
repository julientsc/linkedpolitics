
package data.twitter.job.model.user;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Entities implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4739553756976929398L;
	@Expose
    private Url url;
    @Expose
    private Description description;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

}
