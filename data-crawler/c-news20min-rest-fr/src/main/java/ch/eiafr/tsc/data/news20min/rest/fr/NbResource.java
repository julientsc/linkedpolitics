package ch.eiafr.tsc.data.news20min.rest.fr;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.news20min.model.ArticleSummary;

@Path("nb")
public class NbResource {

	@Context
	UriInfo ui;

	@Path("/{keyword}/")
	@GET
	@Produces(MediaType.TEXT_PLAIN+ ";charset=utf-8")
	public String articlesList(@PathParam("keyword") String keyword) {
		List<ArticleSummary> articlesList = GetData.getArticlesList(keyword);
		return ""+articlesList.size();
	}
}
