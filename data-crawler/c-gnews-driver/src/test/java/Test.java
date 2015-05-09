import java.net.URL;
import java.util.List;

import data.gnews.driver.GnewsDriver;
import data.gnews.model.GoogleNew;


public class Test {
	
	public static void main(String[] args) {
		List<GoogleNew> results = GnewsDriver.search("Toto");
		for (GoogleNew result : results) {
			System.out.println(result.getPubDate().toString());
			for (URL url : result.getLinks())
				System.out.println(" - " + url);
		}

		System.out.println("Total : " + results.size());
	}

}
