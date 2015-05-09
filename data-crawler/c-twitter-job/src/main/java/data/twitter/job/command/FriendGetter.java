package data.twitter.job.command;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import data.twitter.driver.TwitterDriver;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import data.twitter.job.model.user.User;

public class FriendGetter  {
	
	public static String BASE_URL = "https://api.twitter.com/1.1/friends/ids.json";
		
	private static void updateFriendsIds(User user, ArrayList<String> friends) throws Exception {
		updateFriendsIds(user, null, 10, friends);
	}
	
	private static void updateFriendsIds(User user, String nextCursor, int depth, ArrayList<String> friends) throws Exception {

		TwitterDriver twitterDriver = new TwitterDriver();

		URL twitterUrlAPI = new URL(BASE_URL);

		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new NameValuePair("count", "5000"));
		urlParams.add(new NameValuePair("user_id", user.getIdStr()));
		
		if(nextCursor!=null)
			urlParams.add(new NameValuePair("cursor", nextCursor));

		String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI,	urlParams);
		
		JsonParser parser = new JsonParser();
		JsonElement o = parser.parse(response);
		JsonArray a = o.getAsJsonObject().get("ids").getAsJsonArray();
		
		boolean end = false;
		for(int i = 0 ; i < a.size() ; i++) {
			String id = String.valueOf(a.get(i));
			if(friends.contains(id)) {
				end = true;
			} else {
				friends.add(id);
			}
		}
		if(end)
			return;
    	
		depth--;
    	String cursor = o.getAsJsonObject().get("next_cursor_str").getAsString();
    	
		if(depth == 0 || cursor.equals("0")) {
			return;
		} else {
			updateFriendsIds(user, cursor, depth, friends);
		}
	}


	public static ArrayList<String> updateFriends(User userToUpdate, String cacheDir) throws Exception {
		
		File f = new File(cacheDir+"friend/");
		if(!f.isDirectory())
			f.mkdirs();
		
		File ff = new File(f.toString() + "/" +  userToUpdate.getIdStr());
		
		SerializeObject<ArrayList<String>> listingSerializer = new SerializeObject<ArrayList<String>>();
		
		ArrayList<String> friends = listingSerializer.read(ff);
		if(friends == null)
			friends = new ArrayList<String>();
		
		updateFriendsIds(userToUpdate, friends);
		
		listingSerializer.write(friends, ff);
		return friends;
	}	
	
}
