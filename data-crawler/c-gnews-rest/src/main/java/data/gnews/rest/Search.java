package data.gnews.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import data.gnews.driver.GnewsDriver;
import data.gnews.model.GoogleNew;

@Path("search")
public class Search {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("q") String query) {

		List<GoogleNew> results = GnewsDriver.search(query);
		return Response.created(ui.getRequestUri()).entity(results).build();

	}
}
