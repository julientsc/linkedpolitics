package ch.eiafr.tsc.data.facebook.rest.resources;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import ch.eiafr.tsc.data.facebook.model.PageListData;
import ch.eiafr.tsc.data.facebook.model.PageToShow;
import ch.eiafr.tsc.data.facebook.rest.GetData;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("page/")
public class PageResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getPages() throws IOException {
		ArrayList<PageListData> page = GetData.getPageList();
		return new Gson().toJson(page);
	}
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getActivePages() throws IOException {
		ArrayList<PageListData> page = GetData.getActivePageList();
		return new Gson().toJson(page);
	}
	
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getPage(@PathParam("id") String id) throws IOException {
		PageToShow page = GetData.getPageFromId(id);
		return new Gson().toJson(page);
	}
}
