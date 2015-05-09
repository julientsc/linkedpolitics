
package data.twitter.job.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Url implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4750998995900029368L;
	@Expose
    private List<Url_> urls = new ArrayList<Url_>();

    public List<Url_> getUrls() {
        return urls;
    }

    public void setUrls(List<Url_> urls) {
        this.urls = urls;
    }

}
