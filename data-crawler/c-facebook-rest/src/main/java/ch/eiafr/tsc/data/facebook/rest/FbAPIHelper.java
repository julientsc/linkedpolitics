package ch.eiafr.tsc.data.facebook.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ch.eiafr.tsc.data.facebook.driver.DriverCache;
import ch.eiafr.tsc.data.facebook.driver.FbAPI;

public class FbAPIHelper {
	
	
	public static final String CONFIG_FILE = "config.properties";
	public static String access_token;
	public static final String BASE_URL = "https://graph.facebook.com/";
	public static DriverCache dc;
	
	static {
		dc = new DriverCache();
		
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {	 		
			input = FbAPIHelper.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			prop.load(input);
			access_token=prop.getProperty("ACCESS_TOKEN");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//
		}
	}
		
	private static FbAPI fbapi = new FbAPI(access_token, "2.0");
 
	public static FbAPI getFbAPI() {
		return fbapi;
	}

}
