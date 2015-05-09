package ch.eiafr.tsc.data.site.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageModel {
	private static Logger logger = Logger.getLogger(PageModel.class);

	private String name;

	private URL url;

	private String content;

	private ArrayList<URL> links;

	public PageModel(URL url, String content) {

		this.url = url;
		this.content = content;
		this.links = new ArrayList<URL>();

		Document doc = Jsoup.parse(content, url.toString());
		ArrayList<String> suUrls = new ArrayList<String>();
		Elements links = doc.select("a[href]");
		for (Element link : links) {

			String sURL = link.attr("abs:href");
			if (sURL.equals(""))
				continue;

			if (!suUrls.contains(sURL)) {
				suUrls.add(sURL);
				try {

					URL aURL = new URL(sURL);
					this.links.add(aURL);
				} catch (MalformedURLException e) {
					logger.warn("Malformed Link Url for " + sURL + " on "
							+ url.toString());
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public String getContent() {
		return content;
	}

	public ArrayList<URL> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<URL> links) {
		this.links = links;
	}

}
