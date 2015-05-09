package ch.eiafr.tsc.data.facebook.driver;

import java.util.ArrayList;
import java.util.List;

import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.From;
import ch.eiafr.tsc.data.facebook.model.Page;
import ch.eiafr.tsc.data.facebook.model.Post;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.batchfb.Batcher;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import com.googlecode.batchfb.PagedLater;
import com.googlecode.batchfb.Param;

public class FbAPI {

	private Batcher batcher;

	public FbAPI(String token, String version) {
		batcher = new FacebookBatcher(token, version);
	}
	

	public Page page(String object) {
		return page(object, false);
	}
	
	public Page page(String object, boolean refresh) {
		Page page = DriverCache.getPage(object);	
		if (refresh||page==null) {
			page = batcher.graph(object, Page.class).get();
			DriverCache.addPage(object, page);
		}		
		return page;
	}
	
	public List<Post> posts(String page) {
		return posts(page, new ArrayList<String>());
	}

	public List<Post> posts(String page, List<String> oldPosts) {
		List<Post> posts = new ArrayList<>();
		PagedLater<Post> feed = batcher.paged(page + "/posts", Post.class);
		while (feed != null) {
			Post lastPost = null;
			for (Post post : feed.get()) {
				posts.add(post);
				lastPost = post;
			}
			if(lastPost==null||oldPosts.contains(lastPost.getId())){
				break;
			}
			feed = feed.next();
		}
		return posts;
	}

	public List<Comment> comments(String postId) {
		List<Comment> comments = new ArrayList<>();
		PagedLater<Comment> feed = batcher.paged(postId + "/comments",
				Comment.class);
		while (feed != null) {
			for (Comment post : feed.get()) {
				comments.add(post);
			}
			feed = feed.next();
		}
		return comments;
	}

	public long comments_count(String postId) {
		Later<JsonNode> blu = batcher.graph(postId + "/comments", new Param(
				"summary", true), new Param("limit", 0));
		return blu.get().get("summary").get("total_count").asLong();
	}

	public long likes_count(String postId) {
		Later<JsonNode> blu = batcher.graph(postId + "/likes", new Param(
				"summary", true), new Param("limit", 0));
		return blu.get().get("summary").get("total_count").asLong();
	}

	public List<From> likes(String postId) {
		List<From> comments = new ArrayList<>();
		PagedLater<From> feed = batcher.paged(postId + "/likes", From.class);
		while (feed != null) {
			for (From post : feed.get()) {
				comments.add(post);
			}
			feed = feed.next();
		}
		return comments;
	}

}
