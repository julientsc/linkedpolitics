package ch.eiafr.tsc.data.news20min.driver.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Webpage {

	private URL url;
	private String content;

	public URL getURL() {
		return this.url;
	}

	public String getContent() {
		return content;
	}

	private Webpage(URL url, StringBuffer content) {
		this.url = url;
		this.content = content.toString();
	}

	public static Webpage getWebpage(URL url) throws IOException {
//		System.out.println(" - GET page content");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		StringBuffer content = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		return new Webpage(url, content);
	}

	public static Webpage postWebpage(URL url,
			ArrayList<PostParameter> parameters) throws IOException {
//		System.out.print(" - POST page content : ");

		String urlParameters = "";
		for (PostParameter parameter : parameters) {
			urlParameters += parameter.name + "=" + parameter.value + "&";
		}

		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length",
				"" + Integer.toString(urlParameters.getBytes().length));
		connection.setUseCaches(false);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		connection.disconnect();
		

//		System.out.println(url.toString() + " / " + urlParameters);

		Reader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		StringBuffer content = new StringBuffer();
		for (int c; (c = in.read()) >= 0; content.append(((char) c)))
			;
		return new Webpage(url, content);
	}

	public static Webpage getWebpage(URL actionUrl,
			ArrayList<PostParameter> parameters) throws IOException {
	
//		System.out.print(" - GET page content (with parameters) : ");

		String urlParameters = "";
		for (PostParameter parameter : parameters) {
			urlParameters += parameter.name + "=" + parameter.value + "&";
		}
		
		URL url = new URL(actionUrl.toString() + "?" + urlParameters);
		
//		System.out.println(url.toString());
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		StringBuffer content = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		return new Webpage(url, content);
	}

}
