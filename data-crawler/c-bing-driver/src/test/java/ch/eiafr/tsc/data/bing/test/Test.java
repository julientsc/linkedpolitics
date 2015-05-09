package ch.eiafr.tsc.data.bing.test;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.bing.driver.search.BingSearch;
import ch.eiafr.tsc.data.bing.model.BingResult;

public class Test {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		List<BingResult> results = BingSearch.search("julien Tscherrig");
		
		for (BingResult br : results) {
			System.out.println(br.getUrl());
		}
	}

}
