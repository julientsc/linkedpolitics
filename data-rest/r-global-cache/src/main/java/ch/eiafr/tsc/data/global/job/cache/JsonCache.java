package ch.eiafr.tsc.data.global.job.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class JsonCache {
	
	static Logger log = Logger.getLogger(JsonCache.class);

	public static void Write(String pathName, String content) {
		pathName = pathName.toLowerCase();
		try {
			PrintWriter writer = new PrintWriter(pathName, "UTF-8");
			writer.println(content);
			writer.close();
			log.info("Write texte file ("+new File(pathName).getAbsolutePath()+") success");
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
	}
	
	public static String Read(String pathName) {
		pathName = pathName.toLowerCase();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathName), "UTF8"))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
			log.info("Read texte file ("+new File(pathName).getAbsolutePath()+") success");
			br.close();
	        return sb.toString();
	    } catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public static boolean Exist(String pathName) {
		log.debug("Check if file exist ("+new File(pathName).getAbsolutePath()+")");
		pathName = pathName.toLowerCase();
		File f = new  File(pathName);
		return f.isFile();
	}
}