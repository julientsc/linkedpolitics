package ch.eiafr.tsc.data.site.driver;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import ch.eiafr.humantech.tools.concurentdownloader.ConcurentDownloader;
import ch.eiafr.humantech.type.ConcurentArrayList;
import ch.eiafr.tsc.data.site.model.PageModel;
import ch.eiafr.tsc.data.site.model.SiteModel;

public class SiteDriver {

	private static Logger logger = Logger.getLogger(SiteDriver.class);
	
	private int depth;

	public SiteDriver(int depth) {
		this.depth = depth;
	}
	
	public SiteModel get(URL url) {
		SiteModel site = new SiteModel(url);
		
		HashMap<String, PageModel> siteContent = new HashMap<String, PageModel>();
		
		crawl(siteContent, url, this.depth);
		
		ArrayList<PageModel> pages = new ArrayList<PageModel>();
		for (String aURL : siteContent.keySet()) {
			pages.add(siteContent.get(aURL));
		}
		
		site.setPages(pages);
		
		return site;
	}
	
	private void crawl(HashMap<String, PageModel> siteContent, URL url, int depth) {
		ArrayList<URL> level = new ArrayList<URL>();
		level.add(url);
		
		crawl(siteContent, level, url, depth);
	}
		
		
	private void crawl(HashMap<String, PageModel> siteContent, ArrayList<URL> level, URL baseUrl, int depth) {
		logger.debug(baseUrl.toString() + " : remain stage " + (depth -1));
		ArrayList<URL> newLevel = new ArrayList<URL>();

		ConcurentArrayList<URL> pagesUrl = new ConcurentArrayList<URL>();
		for (URL url : level) {
			pagesUrl.add(url);
		}
		
		
		ConcurentDownloader concurentDownloader = new ConcurentDownloader(pagesUrl, 10);
		HashMap<URL, String> finish = concurentDownloader.doProcess();
		
		for (URL url : finish.keySet()) {
//			System.out.println(url.toString());
			
			PageModel page = new PageModel(url, finish.get(url));
			siteContent.put(page.getUrl().toString(), page);
			
			for(URL link : page.getLinks()) {
				if(link.toString().startsWith(baseUrl.toString())) {
					if(!siteContent.containsKey(link.toString())) {
						newLevel.add(link);
//						System.out.println(" +" + link);
					}
				}
			}
		}
		
		if(--depth != 0) {
			crawl(siteContent, newLevel, baseUrl, depth);
		}
	}
	
}
