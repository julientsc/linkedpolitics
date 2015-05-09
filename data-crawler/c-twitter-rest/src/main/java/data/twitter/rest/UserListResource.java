package data.twitter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.twitter.model.UserListData;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/profils/")
//@Api(value = "/twitter", description = "Get data from Twitter")
public class UserListResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@ApiOperation(value = "Get list of users", notes = "", response = UserListData.class, responseContainer = "List")
    public String getUserList() {
		return new Gson().toJson(GetData.getUserList()); 
    }
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@ApiOperation(value = "Get list of users", notes = "", response = UserListData.class, responseContainer = "List")
    public String getActiveUserList() {
		return new Gson().toJson(GetData.getActiveUserList()); 
    }

	
	@Path("/{userid}/")
	public UserInfoResource getUserInfo(@PathParam("userid") String userid){
		return new UserInfoResource(userid);
	}
}
