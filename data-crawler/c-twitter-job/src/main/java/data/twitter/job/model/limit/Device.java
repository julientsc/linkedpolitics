
package data.twitter.job.model.limit;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Device  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5493426452986264887L;
	@SerializedName("/device/token")
    @Expose
    private DeviceToken deviceToken;

    public DeviceToken getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(DeviceToken deviceToken) {
        this.deviceToken = deviceToken;
    }

}
