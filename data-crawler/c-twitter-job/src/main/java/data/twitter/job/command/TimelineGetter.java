package data.twitter.job.command;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import data.twitter.driver.TwitterDriver;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import data.twitter.job.model.tweet.Tweet;
import data.twitter.job.model.user.User;

public class TimelineGetter{
	
	public static String BASE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";

	private static ArrayList<Tweet> getLastTimelineByUserIdFromTweet(User user,	Tweet lastTweet) throws Exception {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		TwitterDriver twitterDriver = new TwitterDriver();
		
		URL twitterUrlAPI = new URL(BASE_URL);

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new NameValuePair("count", "200"));
		urlParams.add(new NameValuePair("user_id", user.getIdStr()));
		
		urlParams.add(new NameValuePair("exclude_replies", "false"));
		urlParams.add(new NameValuePair("include_rts", "true"));
		urlParams.add(new NameValuePair("contributor_details", "true"));
		
		if(lastTweet!=null) {
			urlParams.add(new NameValuePair("max_id", lastTweet.getIdStr()));
			urlParams.add(new NameValuePair("count", "50"));
		} else {
			urlParams.add(new NameValuePair("count", "200"));
		}
			

		String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI,	urlParams);
		
		JsonParser parser = new JsonParser();
		JsonArray o = (JsonArray)parser.parse(response);
	    for(int i = 0 ; i < o.size() ; i++) {
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	Tweet tweet = gson.fromJson(o.get(i), Tweet.class);
	    		    	
	    	tweets.add(tweet);
	    }
	    
	    return tweets;
	}
	
	private static ArrayList<Tweet> getLastTimelineByUserId(User user) throws Exception {
		return getLastTimelineByUserIdFromTweet(user, null);
	}

	public static void UpdateLast200TweetsAndCheckCache(User user,String cacheDir) throws Exception {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		File directory = getTimeLineCacheDirectory(user,cacheDir);
		if(!directory.exists())
			directory.mkdirs();
		
		boolean newLastTweet = false;
		int count = 0;
		ArrayList<Tweet> tweetsToAdd = null;
		do {
			newLastTweet = false;
			
			if(count==0)
				tweetsToAdd = getLastTimelineByUserId(user);		
			else
				tweetsToAdd = getLastTimelineByUserIdFromTweet(user, tweetsToAdd.get(tweetsToAdd.size() - 1));	
			
			for (Tweet tweet : tweetsToAdd) {
			
				if(!existInCacheTweet(user, tweet,cacheDir)) {
					newLastTweet = true;
				} else {
					newLastTweet = false;
				}
			}
		
			count += tweetsToAdd.size();
			tweets.addAll(tweetsToAdd);
		}while(newLastTweet && tweetsToAdd.size() > 190);
			
		for (Tweet tweet : tweets) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			File filename = getTweetCacheFile(user, tweet,cacheDir);
	    	JsonCache.Write(filename.toString(), gson.toJson(tweet));
		}
		
	}
	
	public static boolean existInCacheTweet(User user, Tweet tweet,String cacheDir) {
    	return getTweetCacheFile(user, tweet,cacheDir).exists();
	}
	
	private static File getTweetCacheFile(User user, Tweet tweet, String cacheDir) {
		File f = new File(getTimeLineCacheDirectory(user,cacheDir).toString() + "/" + tweet.getIdStr());
		return f;
	}
	
	private static File getTimeLineCacheDirectory(User user, String cacheDir) {
		File f = new File(cacheDir+user.getIdStr());
		return f;
	}

}
