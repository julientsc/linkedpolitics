package ch.eiafr.tsc.data.facebook.rest.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.eiafr.tsc.data.facebook.rest.FbAPIHelper;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("proxy/{request: .*}")
public class ProxyResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPage(@PathParam("request") String request)
			throws IOException {
		URL url = new URL(FbAPIHelper.BASE_URL + request + "?access_token="
				+ FbAPIHelper.access_token);
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
		}

		in.close();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ESCAPE_NON_ASCII, true);

		Object json = mapper.readValue(sb.toString(), Object.class);
		String result = mapper.writeValueAsString(json);

		return result;
	}

}
