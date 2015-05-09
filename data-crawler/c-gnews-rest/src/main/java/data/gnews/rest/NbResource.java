package data.gnews.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import data.gnews.model.GoogleNewsModel;

@Path("nb")
public class NbResource {

	@Context
	UriInfo ui;
	
	@Path("/{keyword}/")
	@GET
	@Produces(MediaType.TEXT_PLAIN+ ";charset=utf-8")
	public String news(@PathParam("keyword") String keyword) {
		GoogleNewsModel news = GetData.getNews(keyword);
		return ""+news.getNews().size();
	}
}
