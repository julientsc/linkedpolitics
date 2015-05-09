package data.twitter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.twitter.model.PageData;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/all/")
//@Api(value = "/twitter", description = "Get data from Twitter")
public class AllUserListResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@ApiOperation(value = "Get list of all users", notes = "", response = PageData.class, responseContainer = "List")
	public String getAllUser(){
		return new Gson().toJson(GetData.getAllUserList());
	}
	
	@Path("/{pageNum}/")
	public AllUserPageResource getAllUserPage(@PathParam("pageNum") int pageNum){
		return new AllUserPageResource(pageNum);
	}

}
