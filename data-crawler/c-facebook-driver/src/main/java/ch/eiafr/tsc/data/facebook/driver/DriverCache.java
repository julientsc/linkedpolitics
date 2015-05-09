package ch.eiafr.tsc.data.facebook.driver;

import java.io.InputStream;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.Page;
import ch.eiafr.tsc.data.facebook.model.Post;

public class DriverCache {

	private static final CacheManager cacheManager;
	private static final Ehcache postCache;
	private static final Ehcache commentCache;
	private static final Ehcache pageCache;

	static {

		ClassLoader contextClassLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream resourceAsStream = contextClassLoader
				.getResourceAsStream("ehcache.xml");
		cacheManager = CacheManager.create(resourceAsStream);
		System.setProperty("net.sf.ehcache.enableShutdownHook","true");
		postCache = cacheManager.getEhcache("post");
		commentCache = cacheManager.getEhcache("comment");
		pageCache = cacheManager.getEhcache("page");
	}
	
	public static void addPage(String id, Page post) {
		Element element = new Element(id, post);
		pageCache.put(element);
	}

	public static Page getPage(String id) {
		Element element = pageCache.get(id);
		if (element != null) {
			return (Page) element.getObjectValue();
		}
		return null;
	}

	public static void addComment(String id, Comment post) {
		Element element = new Element(id, post);
		commentCache.put(element);
	}

	public static Comment getComment(String id) {
		Element element = commentCache.get(id);
		if (element != null) {
			return (Comment) element.getObjectValue();
		}
		return null;
	}

	public static void addPost(String id, Post post) {
		Element element = new Element(id, post);
		postCache.put(element);
	}


	public static Post getPost(String id) {
		Element element = postCache.get(id);
		if (element != null) {
			return (Post) element.getObjectValue();
		}
		return null;
	}
	
	public static void shutdown() {
		cacheManager.shutdown();
	}
}
