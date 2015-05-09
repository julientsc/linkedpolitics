
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Favorites  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2316688063923254664L;
	@SerializedName("/favorites/list")
    @Expose
    private FavoritesList favoritesList;

    public FavoritesList getFavoritesList() {
        return favoritesList;
    }

    public void setFavoritesList(FavoritesList favoritesList) {
        this.favoritesList = favoritesList;
    }

}
