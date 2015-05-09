package ch.eiafr.tsc.data.bing.driver.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.bing.model.BingResult;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class BingSearch {

	private static Logger logger = Logger.getLogger(BingSearch.class);

	private static String ACCOUNT_KEY = "M0QuHIn968QULiM1vaxN3HAZlubmp22DQYV982qgDQc=";

	private static int MAX_RESULTS = 50;

	private static String getJSON(URL url) {
		logger.debug("Get Bing URL : " + url.toString());

		byte[] accountKeyBytes = Base64
				.encodeBase64((ACCOUNT_KEY + ":" + ACCOUNT_KEY).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

		try {

			// if (!BingCache.ressourceExist(url)) {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}

			conn.disconnect();

			return sb.toString();

		} catch (IOException e) {
			logger.warn("Cannot get Bing URL : " + url.toString());
			e.printStackTrace();
		}
		return null;
	}

	public static List<BingResult> search(String query) {
		logger.info("Execute query : <" + query + ">");
		String searchText = query.replaceAll(" ", "%20").replaceAll("\\+",
				"%2B");
//		byte[] accountKeyBytes = Base64
//				.encodeBase64((ACCOUNT_KEY + ":" + ACCOUNT_KEY).getBytes());
//		String accountKeyEnc = new String(accountKeyBytes);

		List<BingResult> results = new ArrayList<>();

//		String qry = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Composite?"
//				+ "Sources=%27web%27"
//				+ "&Query=%27"
//				+ searchText
//				+ "%27"
//				+ "&$top=" + MAX_RESULTS + "&$format=json";
//		URL url = new URL(qry);
//
//
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//				(conn.getInputStream())));
//
//		StringBuilder sb = new StringBuilder();
//		String output;
//		while ((output = br.readLine()) != null) {
//			sb.append(output);
//		}
//
//		conn.disconnect();
	

		boolean c = true;
		int i = 0;
		do {

			String page = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Composite?"
					+ "Sources=%27web%27"
					+ "&Query=%27"
					+ searchText
					+ "%27"
					+ "&$top=" + MAX_RESULTS + "&$skip=" + i + "&$format=json";
			try {
				URL subUrl = new URL(page);

				String txtSon = BingSearch.getJSON(subUrl);
				JsonArray contentURLS = new JsonParser().parse(txtSon)
						.getAsJsonObject().get("d").getAsJsonObject()
						.get("results").getAsJsonArray().get(0)
						.getAsJsonObject().get("Web").getAsJsonArray();

				int total = new JsonParser().parse(txtSon).getAsJsonObject()
						.get("d").getAsJsonObject().get("results")
						.getAsJsonArray().get(0).getAsJsonObject()
						.get("WebTotal").getAsInt();
				if (total < i + MAX_RESULTS)
					c = false;
				for (int ii = 0; ii < contentURLS.size(); ii++) {
					String url = contentURLS.get(ii).getAsJsonObject().get("Url")
							.getAsString();
					String title = contentURLS.get(ii).getAsJsonObject().get("Title")
							.getAsString();
					String description = contentURLS.get(ii).getAsJsonObject().get("Description")
							.getAsString();
					String id = contentURLS.get(ii).getAsJsonObject().get("ID")
							.getAsString();

					BingResult br = new BingResult();
					br.setDescription(description);
					br.setId(id);
					br.setTitle(title);
					br.setUrl(url);
					results.add(br);
				}

				i += MAX_RESULTS;
			} catch (MalformedURLException e) {
				logger.fatal("Bad Bing search URL " + page);
				return null;
			}

		} while (c);

		return results;
	}

}
