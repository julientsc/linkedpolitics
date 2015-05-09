package ch.eiafr.tsc.data.news20min.driver.minutes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Search20Minuten {

	private static String DE_URL = "http://www.20min.ch/tools/suchen/";

	public static ArrayList<URL> search(String query, ArrayList<String> alreadyArticleDownload) throws IOException {
		URL url = new URL(DE_URL);
		return Search20.search(url, query, alreadyArticleDownload);
	}
}
