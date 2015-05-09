package data.gnews.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import data.gnews.model.GoogleNewsModel;
import data.gnews.model.KeywordResume;

@Path("list")
public class ListResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String list() {
		List<KeywordResume> keywords = GetData.getList();
		return new Gson().toJson(keywords);
	}
	
	@Path("active")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String listActive() {
		List<KeywordResume> keywords = GetData.getActiveList();
		return new Gson().toJson(keywords);
	}
	
	@Path("/{keyword}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String news(@PathParam("keyword") String keyword) {
		GoogleNewsModel news = GetData.getNews(keyword);
		return new Gson().toJson(news);
	}
}
