import java.util.ArrayList;

import data.twitter.job.command.UserGetter;
import data.twitter.job.model.user.User;


public class TestUser {
	
	public static void main(String[] args) {

		try {
			
			ArrayList<String> screenNameList = new ArrayList<String>();
			screenNameList.add("twitterapi");
			screenNameList.add("julientsc");
//			ArrayList<User> users = UserGetter.getUsers(screenNameList);
//			for (User user : users) {
//				System.out.println(user.getId());
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
