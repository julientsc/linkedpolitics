package ch.eiafr.tsc.data.site.test;

import java.io.File;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.site.job.SiteJob;

public class Test {

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();

		SiteJob siteJob = new SiteJob();
		
		File cache = new File("./cache/");
		if(!cache.isDirectory())
			cache.mkdirs();
		
		siteJob.doProcAndContinue("http://www.tscherrig.com", "./cache/");
				
	}

}
