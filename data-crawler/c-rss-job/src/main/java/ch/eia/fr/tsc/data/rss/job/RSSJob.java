package ch.eia.fr.tsc.data.rss.job;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import ch.eiafr.tsc.data.rss.driver.RSSDriver;
import ch.eiafr.tsc.data.rss.model.RSSChannel;
import ch.eiafr.tsc.data.rss.model.json.Item;
import ch.eiafr.tsc.data.rss.model.json.JsonRSS;

public class RSSJob implements IJob {

	private static Logger logger = Logger.getLogger(RSSJob.class);

	public static String convertName(String param) {
		return "/"
				+ param.replaceAll("://", ".").replaceAll("/", ".").replaceAll(":", ".")
						.replaceAll("#", ".").replaceAll("\\?",".")
						.replaceAll("<", ".").replaceAll(">",".")
						.replaceAll("&", ".").replaceAll("\\*",".")
						.replaceAll("=", ".")+ ".json";
		
	}
	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		logger.debug("Start RSS job from " + param);

		File dir = new File(cacheDir);
		if (!dir.isDirectory())
			dir.mkdirs();

		String name = cacheDir + convertName(param);
		File file = new File(name);	
		logger.debug("Cache dir for RSS is " + file.getAbsolutePath());

		RSSChannel result = RSSDriver.getRss(param);

		if (!JsonCache.Exist(name)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonCache.Write(name, gson.toJson(result));

			logger.debug("New stream add (full add)");
		} else {
			logger.debug("Read json content");
			JsonRSS currentRSS = new Gson().fromJson(JsonCache.Read(name),
					JsonRSS.class);
			boolean newContent = false;
			for (Item newItem : currentRSS.getItems()) {
				boolean toAdd = true;
				for (Item existingItem : currentRSS.getItems()) {
					if (existingItem.getTitle().equals(newItem.getTitle())) {
						toAdd = false;
						break;
					}
				}
				if (toAdd) {
					logger.debug("New content add " + file.getAbsolutePath());
					newContent = true;
					currentRSS.getItems().add(newItem);
				}
			}

			if (newContent) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonCache.Write(name, gson.toJson(result));
				logger.debug("Write json content");
			} else {
				logger.debug("No new content");
			}
		}

		logger.debug("End RSS job for " + param);
		return true;
	}

	@Override
	public void doProcAndStop(String param, String cacheDir) throws Exception {

	}
}
