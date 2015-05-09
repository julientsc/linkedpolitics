package ch.eiafr.tsc.data.site.model;

import java.net.URL;
import java.util.ArrayList;

import ch.eiafr.tsc.data.global.job.cache.JsonCache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SiteModel {
	
	private URL baseUrl;

	private ArrayList<PageModel> pages;

	public SiteModel(URL url) {
		this.baseUrl = url;
	}

	public URL getBaseUrl() {
		return baseUrl;
	}

	public ArrayList<PageModel> getPages() {
		return pages;
	}

	public void setPages(ArrayList<PageModel> pages) {
		this.pages = pages;
	}

	public void saveAs(String path) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		JsonCache.Write(path, gson.toJson(this));
	}
	
}
