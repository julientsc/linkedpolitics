package ch.eiafr.tsc.data.bing.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import ch.eiafr.tsc.data.bing.model.BingResult;
import ch.eiafr.tsc.data.bing.model.BingResultModel;
import ch.eiafr.tsc.data.bing.model.KeywordResume;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("list/")
public class PageResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getList() throws IOException {
		List<KeywordResume> list = GetData.getList();
		return new Gson().toJson(list);
	}
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getActiveList() throws IOException {
		List<KeywordResume> activeList = GetData.getActiveList();
		return new Gson().toJson(activeList);
	}
	
	
	@Path("{keyword}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getPage(@PathParam("keyword") String keyword) throws IOException {
		BingResultModel search = GetData.getNews(keyword);
		return new Gson().toJson(search);
	}
	
	@Path("{keyword}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public String getPage(@PathParam("keyword") String keyword, @PathParam("id") String id) throws IOException {
		BingResult search = GetData.getResultInfo(keyword, id);
		return new Gson().toJson(search);
	}
}
