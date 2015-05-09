package ch.eiafr.tsc.data.lib.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

public class FileManager<T> {
	private String url;
	private Class<T> c;
	
	public FileManager(Class<T> c, String url) {
		this.c = c;
		this.url = url;
	}
	
	public T getFromWebService(String uri) {
		Gson gson = new Gson();
		URL api;
		
		try {
			api = new URL(url + uri);
		
			InputStreamReader isr = new InputStreamReader(api.openStream(), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
		
			try {
				T data = (T) gson.fromJson(in, c);
				isr.close();
				in.close();
				return data;
			} catch(Exception e) {
				isr.close();
				in.close();
				e.printStackTrace();
				return null;
			}
	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}