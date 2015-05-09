package ch.eiafr.tsc.data.global.job.cache.test;

import java.io.File;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

public class Test {
	

	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		JsonCache.Write("test.txt", "abcd1234");
		String jsonContent = JsonCache.Read("test.txt");
		System.out.println(jsonContent);
		SerializeObject<Toto> serializeObject = new SerializeObject<Toto>();
		
		Toto obj1 = new Toto();
		obj1.content = "abcd1234";
		
		serializeObject.write(obj1, new File("test2.txt"));
		Toto obj2 = serializeObject.read(new File("test2.txt"));
		System.out.println(obj2.content);
		
	}

}
