
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SavedSearches  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5219271036312736891L;
	@SerializedName("/saved_searches/destroy/:id")
    @Expose
    private SavedSearchesDestroyId savedSearchesDestroyId;
    @SerializedName("/saved_searches/list")
    @Expose
    private SavedSearchesList savedSearchesList;
    @SerializedName("/saved_searches/show/:id")
    @Expose
    private SavedSearchesShowId savedSearchesShowId;

    public SavedSearchesDestroyId getSavedSearchesDestroyId() {
        return savedSearchesDestroyId;
    }

    public void setSavedSearchesDestroyId(SavedSearchesDestroyId savedSearchesDestroyId) {
        this.savedSearchesDestroyId = savedSearchesDestroyId;
    }

    public SavedSearchesList getSavedSearchesList() {
        return savedSearchesList;
    }

    public void setSavedSearchesList(SavedSearchesList savedSearchesList) {
        this.savedSearchesList = savedSearchesList;
    }

    public SavedSearchesShowId getSavedSearchesShowId() {
        return savedSearchesShowId;
    }

    public void setSavedSearchesShowId(SavedSearchesShowId savedSearchesShowId) {
        this.savedSearchesShowId = savedSearchesShowId;
    }

}
