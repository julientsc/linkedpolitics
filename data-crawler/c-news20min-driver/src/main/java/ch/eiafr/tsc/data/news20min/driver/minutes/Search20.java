package ch.eiafr.tsc.data.news20min.driver.minutes;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ch.eiafr.tsc.data.news20min.driver.tools.PostParameter;
import ch.eiafr.tsc.data.news20min.driver.tools.Webpage;

public class Search20 {

	private static Logger logger = Logger.getLogger(Search20Minuten.class);

	public static ArrayList<URL> search(URL url, String query,
			ArrayList<String> alreadyArticleDownload) throws IOException  {
		logger.debug("Start search for <<" + query
				+ ">> (already downloaded : " + alreadyArticleDownload.size()
				+ ")");


		int pageCount = 0;
		int counter = 0;
		boolean cont = true;

		query = query.toLowerCase();

		ArrayList<URL> urls = new ArrayList<URL>();

		do {
			logger.debug("Browse page " + pageCount + " for << " + query
					+ " >>");

			ArrayList<PostParameter> parameters = new ArrayList<>();
			parameters.add(new PostParameter("search_pagenum", String
					.valueOf(pageCount)));
			parameters.add(new PostParameter("search_notnew", "0"));
			parameters.add(new PostParameter("search_advanced", "1"));
			parameters.add(new PostParameter("search_efrom", "01/01/2000"));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			String endDate = sdf.format(new Date());
			parameters.add(new PostParameter("search_eto", endDate));
			parameters.add(new PostParameter("q", query));

			Webpage page = Webpage.postWebpage(url, parameters);
			Document doc = Jsoup.parse(page.getContent(), url.toString());
			Elements stories = doc.select("div.resulttext");

			counter = 0;
			for (Element story : stories) {
				String articleUrl = story.select("a[href]").first()
						.attr("abs:href");
				try {
					if (articleUrl.startsWith("http://www.20min.ch")) {
						logger.debug("Stories found : " + articleUrl);
						
						if(alreadyArticleDownload.contains(articleUrl)) {
							logger.info("Search end (previous article found)");
							return urls;
						}
						
						urls.add(new URL(articleUrl));
						counter++;
					}
				} catch (Exception e) {
					System.err.println("  ! - Article URL is incorrect : "
							+ articleUrl);
				}
			}
			pageCount++;
		} while (counter != 0 && cont);

		logger.info("Search end");

		return urls;
	}
}
