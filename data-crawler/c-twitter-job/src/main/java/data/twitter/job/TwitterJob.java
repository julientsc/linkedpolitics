package data.twitter.job;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import data.twitter.job.command.FollowerGetter;
import data.twitter.job.command.FriendGetter;
import data.twitter.job.command.TimelineGetter;
import data.twitter.job.command.UserGetter;
import data.twitter.job.model.user.User;

public class TwitterJob implements IJob{

	private static Gson gson = new Gson();
	private static Logger logger = Logger.getLogger(JobExecutor.class);

	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		return true;
	}

	@Override
	public void doProcAndStop(String param, String cacheDir) throws Exception {
		// get user object
		User user = UserGetter.getUserFromParam(param);
		String jsonUser = gson.toJson(user);
		//save user object
		File f = new File(cacheDir+"lookup/users/id/");
		if(!f.exists())
			f.mkdirs();
		f = new File(cacheDir+"lookup/users/tmp/");
		if(!f.exists())
			f.mkdirs();
		JsonCache.Write(cacheDir+"lookup/users/id/"+user.getIdStr(), jsonUser);
		JsonCache.Write(cacheDir+"lookup/users/tmp/"+user.getScreenName(), jsonUser);
		
		// timeline
		TimelineGetter.UpdateLast200TweetsAndCheckCache(user, cacheDir+"timeline/");
		
		// friends
		ArrayList<String> friends = FriendGetter.updateFriends(user,cacheDir);
		ArrayList<User> userFriends = UserGetter.getUsersByUserId(friends, cacheDir);
		saveUser(userFriends,cacheDir+"lookup/users/id/");
		
		// follower
		ArrayList<String> followers = FollowerGetter.updateFollowers(user,cacheDir);
		ArrayList<User> userFollowers = UserGetter.getUsersByUserId(followers, cacheDir);
		saveUser(userFollowers,cacheDir+"lookup/users/id/");
	}
	
	public void saveUser(ArrayList<User> users, String dir){
		for(User user : users){
			String jsonUser = gson.toJson(user);
			JsonCache.Write(dir+user.getIdStr(), jsonUser);
		}
	}
	
	public static void main(String[] args) throws Exception{
		IJob job = new TwitterJob();
		job.doProcAndStop("indochinetwitt", "/var/test/");
	}
}
