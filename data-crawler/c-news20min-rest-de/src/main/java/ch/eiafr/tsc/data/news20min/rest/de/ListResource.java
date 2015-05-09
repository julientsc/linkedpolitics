package ch.eiafr.tsc.data.news20min.rest.de;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ch.eiafr.tsc.data.news20min.model.Article;
import ch.eiafr.tsc.data.news20min.model.ArticleSummary;
import ch.eiafr.tsc.data.news20min.model.KeywordSummary;

import com.google.gson.Gson;

@Path("list")
public class ListResource {

	@Context
	UriInfo ui;

	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String list() {
		List<KeywordSummary> keywords = GetData.getList();
		return new Gson().toJson(keywords);
	}
	
	@Path("/active")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String listActive() {
		List<KeywordSummary> keywords = GetData.getActiveList();
		return new Gson().toJson(keywords);
	}
	
	@Path("/{keyword}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String articlesList(@PathParam("keyword") String keyword) {
		List<ArticleSummary> articlesList = GetData.getArticlesList(keyword);
		return new Gson().toJson(articlesList);
	}
	
	@Path("/{keyword}/{articleLink}")
	@GET
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public String article(@PathParam("keyword") String keyword,@PathParam("articleLink") String articleLink) {
		Article article = GetData.getArticle(keyword,articleLink);
		return new Gson().toJson(article);
	}
}
