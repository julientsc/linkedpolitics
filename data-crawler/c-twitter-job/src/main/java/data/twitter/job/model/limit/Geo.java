
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Geo  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4456276031670496568L;
	@SerializedName("/geo/similar_places")
    @Expose
    private GeoSimilarPlaces geoSimilarPlaces;
    @SerializedName("/geo/search")
    @Expose
    private GeoSearch geoSearch;
    @SerializedName("/geo/reverse_geocode")
    @Expose
    private GeoReverseGeocode geoReverseGeocode;
    @SerializedName("/geo/id/:place_id")
    @Expose
    private GeoIdPlaceId geoIdPlaceId;

    public GeoSimilarPlaces getGeoSimilarPlaces() {
        return geoSimilarPlaces;
    }

    public void setGeoSimilarPlaces(GeoSimilarPlaces geoSimilarPlaces) {
        this.geoSimilarPlaces = geoSimilarPlaces;
    }

    public GeoSearch getGeoSearch() {
        return geoSearch;
    }

    public void setGeoSearch(GeoSearch geoSearch) {
        this.geoSearch = geoSearch;
    }

    public GeoReverseGeocode getGeoReverseGeocode() {
        return geoReverseGeocode;
    }

    public void setGeoReverseGeocode(GeoReverseGeocode geoReverseGeocode) {
        this.geoReverseGeocode = geoReverseGeocode;
    }

    public GeoIdPlaceId getGeoIdPlaceId() {
        return geoIdPlaceId;
    }

    public void setGeoIdPlaceId(GeoIdPlaceId geoIdPlaceId) {
        this.geoIdPlaceId = geoIdPlaceId;
    }

}
