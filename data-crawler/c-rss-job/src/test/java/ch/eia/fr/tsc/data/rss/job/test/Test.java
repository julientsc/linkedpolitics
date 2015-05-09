package ch.eia.fr.tsc.data.rss.job.test;

import org.apache.log4j.BasicConfigurator;

import ch.eia.fr.tsc.data.rss.job.RSSJob;

public class Test {

	public static void main(String [] args) {
		BasicConfigurator.configure();
		
		RSSJob rssJob = new RSSJob();
		
		rssJob.doProcAndContinue("http://rss.lemonde.fr/c/205/f/3060/index.rss", "./test");
	
		
	}
	
}
