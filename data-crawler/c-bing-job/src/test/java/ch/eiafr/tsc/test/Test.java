package ch.eiafr.tsc.test;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.bing.job.BingJob;

public class Test {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		ArrayList<String> keywordList = new ArrayList<String>();
		keywordList.add("julien tscherrig");
		keywordList.add("omar abou khaled");

		try {
			BingJob bingJob = new BingJob();
			bingJob.doProcAndContinue(keywordList.get(0), "/var/cache/semantic/data/bing/json/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
