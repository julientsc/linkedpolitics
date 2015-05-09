package data.twitter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.twitter.model.TimelineData;

/**
 * Root resource (exposed at "myresource" path)
 */

//@Api(value = "/twitter", description = "Get data from Twitter")
public class StatusResource {
	String userId;
	String statusId;
	public StatusResource(String userId, String statusId) {
		this.userId = userId;
		this.statusId = statusId;
	}
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@ApiOperation(value = "Get details of a status", notes = "", response = TimelineData.class, responseContainer="List")
    public String getStatus() {
		return new Gson().toJson(GetData.getStatus(userId,statusId)); 
    }
}
