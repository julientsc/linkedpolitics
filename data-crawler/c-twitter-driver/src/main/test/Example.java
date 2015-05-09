import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import data.twitter.driver.TwitterDriver;


public class Example {

	public static void main(String [] args) throws Exception {
		
		// See more on https://dev.twitter.com/docs
		
		TwitterDriver twitterDriver = new TwitterDriver();
		URL twitterUrlAPI = new URL("https://api.twitter.com/1.1/friends/ids.json");
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		
		urlParams.add(new NameValuePair("screen_name", "julientsc"));
		urlParams.add(new NameValuePair("count", "100"));

		String response = twitterDriver.getTwitterUrlAPI(twitterUrlAPI, urlParams);
		System.out.println(response);
		
	}
	
}
