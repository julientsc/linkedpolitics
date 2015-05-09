package ch.eiafr.tsc.data.rss.driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import ch.eiafr.tsc.data.rss.model.RSSChannel;
import ch.eiafr.tsc.data.rss.model.RSSItem;

public class RSSDriver {

	private static Logger logger = Logger.getLogger(RSSDriver.class);

	private static SimpleDateFormat SDF = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss z",Locale.US);
	public static RSSChannel getRss(String rssUrl){
		logger.debug("Get RSS from " + rssUrl);
		try{
			RSSChannel rssChannel = new RSSChannel();
			Document rss = Jsoup.connect(rssUrl).parser(Parser.xmlParser()).timeout(1500).get();
			Element channel = rss.getElementsByTag("channel").get(0);
			
			// items
			Elements items = channel.getElementsByTag("item");
			// channel without items
			channel.getElementsByTag("item").remove();
			// add channel info
			Elements channelElems = channel.children();
			for(Element channelElem : channelElems){
				switch(channelElem.tagName()){
				case "language":
					rssChannel.setLanguage(channelElem.text()); 
					break;
				case "title":
					rssChannel.setTitle(channelElem.text());
					break;
				case "description":
					rssChannel.setDescription(channelElem.text());
					break;
				case "link":
					rssChannel.setLink(channelElem.text());
					break;
				case "pubdate":
					rssChannel.setPubDate(SDF.parse(channelElem.text()));
					break;
				case "lastbuilddate":
					rssChannel.setLastBuildDate(SDF.parse(channelElem.text()));
					break;
				case "copyright":
					rssChannel.setCopyright(channelElem.text());
					break;
				}
			}
			
			// add items info
			ArrayList<RSSItem> itemList = new ArrayList<>();
			for(Element e : items){
				itemList.add(parseItem(e));
			}
			rssChannel.setItems(itemList);
			

			logger.info("Success getting RSS from " + rssUrl);
			
			return rssChannel;
			
		}catch(Exception e){
			logger.error("URL doesn't exist or is not a valid RSS feed");
			e.printStackTrace();
			return null;
		}
	}
	private static RSSItem parseItem(Element item){
		RSSItem crtItem = new RSSItem();
		Elements itemElems = item.children();
		for(Element itemElem : itemElems){
			switch(itemElem.tagName()){
			case "title":
				crtItem.setTitle(itemElem.text());
				break;
			case "description":
				crtItem.setDescription(itemElem.text());
				break;
			case "link":
				crtItem.setLink(itemElem.text());
				break;
			case "pubdate":
				try {
					crtItem.setPubDate(SDF.parse(itemElem.text()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return crtItem;
	}
}
