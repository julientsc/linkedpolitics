package ch.eiafr.tsc.data.rss.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.rss.model.RSSChannel;
import ch.eiafr.tsc.data.rss.model.RSSResume;

import com.google.gson.Gson;

@Path("list")
public class ListResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String list() {
		List<RSSResume> rssResume = GetData.getList();
		return new Gson().toJson(rssResume);
	}
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String listActive() {
		List<RSSResume> rssResume = GetData.getActiveList();
		return new Gson().toJson(rssResume);
	}
	
	@Path("/{rssFile}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String rssContent(@PathParam("rssFile") String rssFile){
		RSSChannel rss = GetData.getRss(rssFile);
		return new Gson().toJson(rss);
	}
}
