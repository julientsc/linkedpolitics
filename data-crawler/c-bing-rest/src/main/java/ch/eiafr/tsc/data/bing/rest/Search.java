package ch.eiafr.tsc.data.bing.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.bing.driver.search.BingSearch;
import ch.eiafr.tsc.data.bing.model.BingResult;

@Path("search")
public class Search {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("q") String query) {

		List<BingResult> blu = BingSearch.search(query);
		return Response.created(ui.getRequestUri()).entity(blu).build();

	}
}
