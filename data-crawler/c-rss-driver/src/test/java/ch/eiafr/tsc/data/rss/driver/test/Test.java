package ch.eiafr.tsc.data.rss.driver.test;

import java.net.MalformedURLException;
import ch.eiafr.tsc.data.rss.driver.RSSDriver;


public class Test {
	public static void main(String[] args) throws MalformedURLException{
		System.out.println(RSSDriver.getRss("http://rss.lemonde.fr/c/205/f/3060/index.rss"));
		System.out.println(RSSDriver.getRss("http://www.20min.ch/rss/rss.tmpl?type=channel&get=17&lang=ro"));
	}
}
