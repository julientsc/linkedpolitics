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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import data.twitter.job.model.user.User;

public class UserGetter {
	
	public static String BASE_URL = "https://api.twitter.com/1.1/users/lookup.json";
	
	public static User getUserFromParam(String param) throws Exception{
		boolean paramIsId = true;
		if(param==null || param.equals(""))
			return null;
		for(char c : param.toCharArray()){
			if(!Character.isDigit(c)){
				paramIsId=false;
				break;
			}
		}
		User u;
		if(paramIsId){
			u = getUserFromId(param);
		}else{
			u = getUserFromScreenname(param);
		}
		return u;
	}
	
	private static User getUserFromScreenname(String screenname) throws Exception{
		return getUser(screenname, "screen_name");
	}
	
	public static User getUserFromId(String id) throws Exception{
		return getUser(id, "user_id");
	}
	
	private static User getUser(String param, String varName) throws Exception{
		URL twitterUrlAPI = new URL(BASE_URL);
		TwitterDriver twitterDriver = new TwitterDriver();
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new NameValuePair(varName, param)); 
		
		String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI,	urlParams);
		System.out.println(response);
		JsonParser parser = new JsonParser();
		JsonArray o = (JsonArray)parser.parse(response);
		return  new Gson().fromJson(o.get(0), User.class);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<User> getUsersByUserId(ArrayList<String> userIdList, String cacheDir) throws Exception {

		ArrayList<User> users = new ArrayList<User>();
		String lookupDir = cacheDir+"lookup/users/id/";
		File f = new File(lookupDir);
		ArrayList<String> toGet = (ArrayList<String>) userIdList.clone();
		String[] lookupInCache = f.list();
		for (String l : lookupInCache) {
			l=l.toLowerCase();
			if(toGet.contains(l)) {
				toGet.remove(l);
			}
		}
		
		while(toGet.size()>0) {
		
			TwitterDriver twitterDriver = new TwitterDriver();
	
			URL twitterUrlAPI = new URL(BASE_URL);
			
			StringBuffer userIds = new StringBuffer();
			boolean isFirst = true;
			

			ArrayList<String> toRemove = new ArrayList<String>();
			int count = 0 ;
			for (String tweetId : toGet) {
				if(!isFirst) {
					userIds.append(",");
				}
				userIds.append(tweetId);
				toRemove.add(tweetId);
				isFirst = false;
				count++;
				if(count==100)
					break;
			}
			
			for (String r : toRemove) {
				toGet.remove(r);
			}
	
			List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
			urlParams.add(new NameValuePair("user_id", userIds.toString())); 
			String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI,	urlParams);
	
//			System.out.println(response);
			
			JsonParser parser = new JsonParser();
			try{
				JsonArray o = (JsonArray)parser.parse(response);
			    for(int i = 0 ; i < o.size() ; i++) {
			    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
			    	User user = gson.fromJson(o.get(i), User.class);
			    	String fileName = lookupDir + user.getIdStr();
			    	if(!JsonCache.Exist(fileName)) {
			    		JsonCache.Write(fileName, gson.toJson(o.get(i)));
			    	}
			    	users.add(user);
			    }
			}
			// in case of this is not a array of user but just one user, add it
			catch(Exception e){
				JsonElement o = parser.parse(response);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    	User user = gson.fromJson(o, User.class);
		    	String fileName = lookupDir + user.getIdStr();
		    	if(!JsonCache.Exist(fileName)) {
		    		JsonCache.Write(fileName, gson.toJson(o));
		    	}
		    	users.add(user);
			}
		}
	    return users;
	}
}
