package ch.eiafr.tsc.data.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.site.driver.SiteDriver;

public class Test {

	public static void main(String[] args) throws MalformedURLException {

		BasicConfigurator.configure();
		
		SiteDriver siteDriver = new SiteDriver(2);
		
		siteDriver.get(new URL("http://www.nestle.com"));
	
	}

}
