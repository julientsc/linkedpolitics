
package data.twitter.job.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Hashtag_ implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5568527980291820541L;
	@Expose
    private String text;
    @Expose
    private List<Integer> indices = new ArrayList<Integer>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

}
