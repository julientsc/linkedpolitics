package ch.eiafr.tsc.data.facebook.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.From;
import ch.eiafr.tsc.data.facebook.model.PostToShow;
import ch.eiafr.tsc.data.facebook.model.PostsData;
import ch.eiafr.tsc.data.facebook.rest.GetData;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("page/{id}/posts/")
public class PostsResource {
 
	@Context
	UriInfo ui;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
    public String getPosts(@PathParam("id") String id) throws IOException {
		List<PostsData> posts = GetData.getPosts(id);
		return new Gson().toJson(posts);	
    }
	
	@Path("{postId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
    public String getPost(@PathParam("id") String id, @PathParam("postId") String postId) throws IOException {
		PostToShow post = GetData.getPost(id, postId);
		return new Gson().toJson(post);	
    }
	
	@Path("{postId}/likes")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
    public String getLikes(@PathParam("id") String id, @PathParam("postId") String postId) throws IOException {
		ArrayList<From> likes = GetData.getLikes(id, postId);
		return new Gson().toJson(likes);
    }
	
	@Path("{postId}/comments")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
    public String getComments(@PathParam("id") String id, @PathParam("postId") String postId) throws IOException {
		ArrayList<Comment> comments = GetData.getComments(id, postId);
		return new Gson().toJson(comments);	
    }
}