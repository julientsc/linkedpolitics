package ch.eiafr.tsc.data.site.job;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.site.driver.SiteDriver;
import ch.eiafr.tsc.data.site.model.SiteModel;

public class SiteJob implements IJob {

	private static Logger logger = Logger.getLogger(JobExecutor.class);

	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		try {
			SiteDriver siteDriver = new SiteDriver(2);
			SiteModel site = siteDriver.get(new URL(param));

			String sDir = cacheDir
					+ "/"
					+ param.replaceAll("/", "")
							.replaceAll("#", ".").replaceAll(":", ".")
					+ "/";
			File dir = new File(sDir);
			if (!dir.isDirectory())
				dir.mkdirs();

			site.saveAs(sDir + new Date().getTime());
			logger.info("Success saving job result");

		} catch (MalformedURLException e) {
			logger.warn("Ending job with error : param = " + param);
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void doProcAndStop(String param, String cacheDir) throws Exception {

	}
}
