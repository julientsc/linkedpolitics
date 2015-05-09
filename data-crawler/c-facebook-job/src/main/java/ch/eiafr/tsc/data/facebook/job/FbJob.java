package ch.eiafr.tsc.data.facebook.job;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.eiafr.tsc.data.facebook.driver.FbAPI;
import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.From;
import ch.eiafr.tsc.data.facebook.model.Page;
import ch.eiafr.tsc.data.facebook.model.Post;
import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;

public class FbJob implements IJob{
	private static Logger logger = Logger.getLogger(JobExecutor.class);
	private FbAPI fbapi;
	public FbJob(FbAPI fbapi){
		this.fbapi = fbapi;
	};

	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//Get page
		Page p = fbapi.page(param);
		String json = gson.toJson(p);
		File pagesDir = new File(cacheDir+"pages/");
		if(!pagesDir.exists())
			pagesDir.mkdirs();
		JsonCache.Write(cacheDir+"pages/"+p.getId(), json);
		
		//Post  update
		File f = new File(cacheDir+"posts/"+p.getId());
		if(!f.exists())
			f.mkdirs();
		List<Post> postsToAdd = fbapi.posts(p.getId(),Arrays.asList(f.list()));
			
		for (Post post : postsToAdd) {
			post.setCreatedTime(post.getCreated_time().getTime());
			post.setUpdatedTime(post.getUpdated_time().getTime());
			String postsCache = f.getAbsolutePath()+"/"+post.getId();
			String jsonPost = gson.toJson(post);
			
	    	JsonCache.Write(postsCache, jsonPost);
	    	
	    	List<From> likes = fbapi.likes(post.getId());
	    	JsonCache.Write(postsCache+".like", gson.toJson(likes));
	    	
	    	List<Comment> comments = fbapi.comments(post.getId());
	    	JsonCache.Write(postsCache+".comment", gson.toJson(comments));
		}
		return true;
	}

	@Override
	public void doProcAndStop(String param, String cacheDir) throws Exception {
		
	}
	
}
