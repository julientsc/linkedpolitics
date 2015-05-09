package data.twitter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.twitter.model.TimelineData;

/**
 * Root resource (exposed at "myresource" path)
 */

//@Api(value = "/twitter", description = "Get data from Twitter")
public class TimelineResource {
	String userId;
	public TimelineResource(String userId) {
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
//	@ApiOperation(value = "Get status list for a user", notes = "", response = TimelineData.class, responseContainer="List")
    public String getTimeline() {
		return new Gson().toJson(GetData.getTimeline(userId)); 
    }
	
	
	@Path("/{statusId}/")
	public StatusResource getStatus(@PathParam("statusId") String statusId){
		return new StatusResource(userId,statusId);
	}
}
