package data.twitter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.twitter.model.UserInfoData;

/**
 * Root resource (exposed at "myresource" path)
 */

//@Api(value = "/twitter", description = "Get data from Twitter")
public class UserInfoResource {
	String userId;
	public UserInfoResource(String userId) {
		this.userId = userId;
	}
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@ApiOperation(value = "Get info for a user", notes = "", response = UserInfoData.class)
    public String getUserUnfo() {
		return new Gson().toJson(GetData.getUserInfoFromId(userId)); 
    }
	
	
	@Path("/timeline/")
	public TimelineResource getTimeline(){
		return new TimelineResource(userId);
	}
	@Path("/followers/")
	public FollowersResource getFollowers(){
		return new FollowersResource(userId);
	}
	@Path("/friends/")
	public FriendsResource getFriends(){
		return new FriendsResource(userId);
	}
	
}
