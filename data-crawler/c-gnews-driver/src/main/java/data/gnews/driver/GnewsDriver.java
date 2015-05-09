package data.gnews.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import data.gnews.model.GoogleNew;

public class GnewsDriver {

	private static String GOOGLE_NEWS_URI = "http://news.google.com/news?output=rss&num=100&q=";

	public static List<GoogleNew> search(String researchWord) {
		researchWord = researchWord.toLowerCase();
		ArrayList<GoogleNew> googleNews = new ArrayList<>();
		researchWord = researchWord.replaceAll(" ", "+");
		String qry = GOOGLE_NEWS_URI + researchWord;

		try {
			org.w3c.dom.Document doc = null;
			new Date();
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = domFactory.newDocumentBuilder();

			URL oracle = new URL(qry);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					oracle.openStream()));

			String inputLine, c = "";
			while ((inputLine = in.readLine()) != null)
				c += (inputLine);
			in.close();

			c = new String(c.getBytes(), "UTF-8");
			InputSource is = new InputSource(new StringReader(c));

			doc = docBuilder.parse(is);

			NodeList items = doc.getElementsByTagName("item");
			for (int i = 0; i < items.getLength(); i++) {
				Element element = (Element) (items.item(i));
				GoogleNew googleNew = createGoogleNew(element);
				googleNews.add(googleNew);
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return googleNews;
	}

	public static GoogleNew createGoogleNew(Element item) {
		GoogleNew gn = new GoogleNew();

		List<URL> links = new ArrayList<>();

		try {
			String url = ((Element) item.getElementsByTagName("link").item(0))
					.getTextContent();
			url = url.substring(url.indexOf("&url=") + "&url=".length());
			links.add(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String title = ((Element) item.getElementsByTagName("title").item(0))
				.getTextContent();
		gn.setTitle(title);

		try {
			String date = ((Element) item.getElementsByTagName("pubDate").item(
					0)).getTextContent();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
			Date pubDate = sdf.parse(date);
			gn.setPubDate(pubDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String description = ((Element) item
				.getElementsByTagName("description").item(0)).getTextContent();

		Document doc = Jsoup.parse(description);
		for (org.jsoup.nodes.Element link : doc.select("a")) {
			String absHref = link.attr("abs:href"); // "http://jsoup.org/"
			if (absHref.contains("&url=")) {
				absHref = absHref.substring(absHref.indexOf("&url=")
						+ "&url=".length());
				try {
					if (!links.contains(new URL(absHref)))
						links.add(new URL(absHref));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		gn.setLinks(links);
		return gn;
	}

}
