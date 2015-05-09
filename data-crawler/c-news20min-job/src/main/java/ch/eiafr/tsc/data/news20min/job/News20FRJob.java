package ch.eiafr.tsc.data.news20min.job;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import ch.eiafr.humantech.tools.concurentdownloader.ConcurentDownloader;
import ch.eiafr.humantech.type.ConcurentArrayList;
import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.data.news20min.driver.minutes.Search20Minutes;
import ch.eiafr.tsc.data.news20min.model.Article;
import ch.eiafr.tsc.data.news20min.tools.ParseArticle;

public class News20FRJob implements IJob {

	private static Logger logger = Logger.getLogger(News20FRJob.class);

	public boolean doProcAndContinue(String param, String cacheDir) {
		logger.debug("Start job for " + param);
		SerializeObject<ArrayList<String>> serializer = new SerializeObject<ArrayList<String>>();

		File queries = new File(cacheDir + "/queries/");
		if (!queries.isDirectory())
			queries.mkdirs();
		String path = cacheDir + "/queries/" + param.replaceAll(" ", "_")
				+ ".FR.bin";
		File f = new File(path);
		ArrayList<String> alreadyCached = new ArrayList<String>();
		if (f.isFile()) {
			alreadyCached = serializer.read(new File(path));
		}

		logger.debug("Load previous articles for " + param);

		try {
			String sPageDir = cacheDir + "/articles/";
			File pageDir = new File(sPageDir);
			if (!pageDir.isDirectory())
				pageDir.mkdirs();

			String sPageRawDir = cacheDir + "/raws/";
			File PageRawDir = new File(sPageRawDir);
			if (!PageRawDir.isDirectory())
				PageRawDir.mkdirs();

			ArrayList<URL> newsLink = Search20Minutes.search(param,
					alreadyCached);

			ArrayList<URL> toRemove = new ArrayList<URL>();
			for (URL newLink : newsLink) {
				String pageName = newLink.toString().replaceAll("://", ".")
						.replaceAll("/", ".").replaceAll("#", ".");
				File f1 = new File(sPageDir + pageName);
				File f2 = new File(sPageRawDir + pageName);
				if (f1.isFile() && f2.isFile())
					toRemove.add(newLink);

				if (!alreadyCached.contains(newLink.toString()))
					alreadyCached.add(newLink.toString());
			}
			newsLink.removeAll(toRemove);

			ConcurentArrayList<URL> pagesUrl = new ConcurentArrayList<URL>(
					newsLink);
			ConcurentDownloader concurentDownloader = new ConcurentDownloader(
					pagesUrl, 10, true);
			HashMap<URL, String> finish = concurentDownloader.doProcess();

			SerializeObject<Article> serializeArticle = new SerializeObject<Article>();
			for (URL ff : finish.keySet()) {

				logger.debug("New articles " + ff.toString());

				String pageName = ff.toString().replaceAll("://", ".")
						.replaceAll("/", ".").replaceAll("#", ".");

				File ffa = new File(sPageRawDir + pageName);
				if (!ffa.exists()) {
					JsonCache.Write(sPageRawDir + pageName, finish.get(ff));
				}

				File ffb = new File(sPageDir + pageName);
				if (!ffb.exists()) {
					Article article = ParseArticle.parseArticle(
							JsonCache.Read(sPageRawDir + pageName), ff);
					serializeArticle.write(article, ffb);
				}

			}

			serializer.write(alreadyCached, new File(path));

		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void doProcAndStop(String param, String cacheDir) throws Exception {

	}

}
