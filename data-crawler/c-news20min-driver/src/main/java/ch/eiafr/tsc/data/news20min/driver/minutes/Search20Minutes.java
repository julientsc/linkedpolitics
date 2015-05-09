package ch.eiafr.tsc.data.news20min.driver.minutes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Search20Minutes {
	
	private static String FR_URL = "http://www.20min.ch/ro/rechercher/";

	public static ArrayList<URL> search(String query, ArrayList<String> alreadyArticleDownload) throws IOException {
		URL u = new URL(FR_URL);
		return Search20.search(u, query, alreadyArticleDownload);
	}

}
