package ch.eiafr.tsc.data.news20min.tools;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.unbescape.html.HtmlEscape;

import ch.eiafr.tsc.data.news20min.model.Article;

public class ParseArticle {
	private static SimpleDateFormat storyDE = new SimpleDateFormat(
			"dd. MMMM yyyy HH:mm", Locale.GERMAN);
	private static SimpleDateFormat nostoryDE = new SimpleDateFormat(
			"EEEE, dd. MMMM yyyy HH:mm", Locale.GERMAN);
	private static SimpleDateFormat allFR = new SimpleDateFormat(
			"dd MMMM yyyy HH:mm", Locale.FRENCH);

	public static Article parseArticle(String content, URL url) {
		try {
			Article article = new Article();
			Document doc = Jsoup.parse(content);
			Element art = doc.getElementById("story_content");
			if (art == null) {
				return parseArticleNoStory(content, url);
			}

			article.setUrl(url);

			// set language
			article.setLang((url.toString().contains("/ro/") ? "FR" : "DE"));

			// get article header (date, title, subtitle, category) and body
			// (text)
			Element artHeader = art.getElementsByClass("story_titles").get(0);
			Element artBody = art.getElementsByClass("story_text").get(0);

			// parse and transform date to java Date object
			String sDate = artHeader.getElementsByClass("published").get(0)
					.getElementsByTag("p").get(0).html();
			sDate = HtmlEscape.unescapeHtml(sDate);
			if (sDate.contains(";"))
				sDate = sDate.substring(0, sDate.indexOf(";"));
			Date date = parseDate(sDate, true, url.toString().contains("/ro/"),
					true);
			article.setDate(date);
			// parse category
			if (artHeader.getElementsByTag("h4").size() > 0) {
				String category = artHeader.getElementsByTag("h4").get(0)
						.html();
				category = removeHtml(category);
				article.setCategory(category);
			}

			// parse title
			String title = artHeader.getElementsByTag("h1").get(0)
					.getElementsByTag("span").html();
			title = removeHtml(title);
			article.setTitle(title);

			// parse subtitle
			String subtitle = artHeader.getElementsByTag("h3").get(0).html();
			subtitle = removeHtml(subtitle);
			article.setSubtitle(subtitle);

			// parse article text
			article.setContent(getArtText(artBody));

			return article;
		} catch (Exception e) {
			return null;
		}
	}

	private static Article parseArticleNoStory(String content, URL url) {
		try {
			Article article = new Article();
			Document doc = Jsoup.parse(content);
			if(doc.getElementsByClass("highlight").size()==0)
				return null;
			Element art = doc.getElementsByClass("highlight").get(0);

			article.setUrl(url);

			// set language
			article.setLang((url.toString().contains("/ro/") ? "FR" : "DE"));

			// get article header (date, title, subtitle, category), lead
			// (subtitle) and body (text)
			Element artHeader = art.getElementsByClass("teaser_title").get(0);
			Element artLead = art.getElementsByClass("lead").get(0);
			Element artBody = art.getElementsByClass("story_text").get(0);

			String sDate = artHeader.getElementsByClass("date").html();
			sDate = HtmlEscape.unescapeHtml(sDate);
			Date date = parseDate(sDate, false,
					url.toString().contains("/ro/"), true);
			article.setDate(date);

			// parse category
			String category = artHeader.getElementsByTag("h3").get(0).html();
			category = removeHtml(category);
			article.setCategory(category);

			// parse title
			String title = artHeader.getElementsByTag("h2").get(0).html();
			title = removeHtml(title);
			article.setTitle(title);

			// parse subtitle
			if (artLead.getElementsByTag("p").size() > 0) {
				String subtitle = artLead.getElementsByTag("p").get(0).html();
				subtitle = removeHtml(subtitle);
				article.setSubtitle(subtitle);
			}

			// parse article text
			article.setContent(getArtText(artBody));

			return article;
		} catch (Exception e) {
			return null;
		}
	}

	private static Date parseDate(String date, boolean story, boolean french,
			boolean first) {
		try {
			if (french) {
				return allFR.parse(date);
			} else {
				if (story) {
					return storyDE.parse(date);
				} else {
					return nostoryDE.parse(date);
				}
			}
		} catch (ParseException e) {
			if (first)
				return parseDate(date, story, !french, false);
			else
				return null;
		}
	}

	private static String getArtText(Element storyText) {
		// remove javascript and popup about people from article text
		storyText.getElementsByTag("script").remove();
		storyText.getElementsByClass("cooltent").remove();

		// parse article text and author
		Elements texts = storyText.getElementsByTag("p");
		String textContent = "";
		for (Element e : texts) {
			if (e.hasClass("autor")) {
				break;
			}
			textContent += removeHtml(e.html()) + "\n";
		}
		return textContent;
	}

	private static String removeHtml(String s) {
		s = HtmlEscape.unescapeHtml(s);
		s = s.replaceAll("<[^>]*>", "");
		s = s.replaceAll("\n", "");
		return s;
	}
}
