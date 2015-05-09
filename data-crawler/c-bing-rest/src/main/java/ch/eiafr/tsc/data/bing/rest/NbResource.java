package ch.eiafr.tsc.data.bing.rest;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.bing.model.BingResultModel;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("nb/")
public class NbResource {

	@Context
	UriInfo ui;

	
	@Path("{keyword}")
	@GET
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf8")
	public String getPage(@PathParam("keyword") String keyword) throws IOException {
		BingResultModel search = GetData.getNews(keyword);
		return ""+search.getIDs().size();
	}
}
