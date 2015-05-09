package ch.eiafr.tsc.data.site.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.site.model.KeywordResume;
import ch.eiafr.tsc.data.site.model.SiteModel;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("list/")
public class PageResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getList() throws IOException {
		List<KeywordResume> list = GetData.getList();
		return Response.ok(ui.getRequestUri()).entity(list).build();
	}
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getActiveList() throws IOException {
		List<KeywordResume> activeList = GetData.getActiveList();
		return Response.ok(ui.getRequestUri()).entity(activeList).build();
	}
	
	
	@Path("{keyword}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getPage(@PathParam("keyword") String keyword) throws IOException {
		SiteModel search = GetData.getNews(keyword);
		return Response.ok(ui.getRequestUri()).entity(search).build();
	}
}
