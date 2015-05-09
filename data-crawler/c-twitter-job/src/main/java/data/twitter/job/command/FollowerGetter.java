package data.twitter.job.command;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import data.twitter.driver.TwitterDriver;
import data.twitter.job.model.user.User;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FollowerGetter {

	public static String BASE_URL = "https://api.twitter.com/1.1/followers/ids.json";

	private static void updateFollowersIds(User user, ArrayList<String> followers) throws Exception {
		updateFollowersIds(user, null, 10, followers);
	}
	
	private static void updateFollowersIds(User user, String nextCursor, int depth, ArrayList<String> followers) throws Exception {
		TwitterDriver twitterDriver = new TwitterDriver();

		URL twitterUrlAPI = new URL(BASE_URL);

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new NameValuePair("count", "5000"));
		urlParams.add(new NameValuePair("user_id", user.getIdStr()));

		if(nextCursor!=null)
			urlParams.add(new NameValuePair("cursor", nextCursor));
		
		String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI, urlParams);
		
		JsonParser parser = new JsonParser();
		JsonElement o = parser.parse(response);
		JsonArray a = o.getAsJsonObject().get("ids").getAsJsonArray();
		
		boolean end = false;
		for(int i = 0 ; i < a.size() ; i++) {
			String id = String.valueOf(a.get(i));
			if(followers.contains(id)) {
				end = true;
			} else {
				followers.add(id);
			}
		}
		if(end)
			return;
    	
		String cursor = o.getAsJsonObject().get("next_cursor_str").getAsString();
		
		depth--;
		if(depth == 0 || cursor.equals("0")) {
			return;
		} else {
	    	updateFollowersIds(user, cursor, depth, followers);
		}
	}


	public static ArrayList<String> updateFollowers(User userToUpdate, String cacheDir) throws Exception {
		File f = new File(cacheDir+"follower/");
		if(!f.isDirectory())
			f.mkdirs();
		
		File ff = new File(f.toString() +"/" +  userToUpdate.getIdStr());
		
		SerializeObject<ArrayList<String>> listingSerializer = new SerializeObject<ArrayList<String>>();
		
		ArrayList<String> followers = listingSerializer.read(ff);
		if(followers == null)
			followers = new ArrayList<String>();
		
		updateFollowersIds(userToUpdate, followers);
		listingSerializer.write(followers, ff);
		return followers;
	}
	
	

}
