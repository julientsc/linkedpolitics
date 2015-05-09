import java.util.ArrayList;

import data.twitter.job.command.TweetGetter;

public class TestTweet {

	public static void main(String[] args) {

		try {
			
			ArrayList<String> tweetsId = new ArrayList<String>();
			tweetsId.add("20");
			tweetsId.add("432656548536401920");
			
			TweetGetter.getTweets(tweetsId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
