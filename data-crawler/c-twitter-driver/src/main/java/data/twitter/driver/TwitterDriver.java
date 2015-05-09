package data.twitter.driver;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import data.twitter.driver.constant.Global;
import data.twitter.driver.tools.NvpComparator;

public class TwitterDriver {

	public String getTwitterUrlAPI(URL twitterUrlAPI,
			List<NameValuePair> urlParams) throws Exception {

		String method = "GET";

		String url = twitterUrlAPI.toString();

		String oAuthNonce = String.valueOf(System.currentTimeMillis());
		String oAuthSignatureMethod = "HMAC-SHA1";
		String oAuthTimestamp = getTimestamp();
		String oAuthVersion = "1.0";

		String signatureBaseString1 = method;
		String signatureBaseString2 = url;

		List<NameValuePair> allParams = new ArrayList<NameValuePair>();
		allParams.add(new NameValuePair("oauth_consumer_key",
				Global.oAuthConsumerKey));
		allParams.add(new NameValuePair("oauth_nonce", oAuthNonce));
		allParams.add(new NameValuePair("oauth_signature_method",
				oAuthSignatureMethod));
		allParams.add(new NameValuePair("oauth_timestamp", oAuthTimestamp));
		allParams
				.add(new NameValuePair("oauth_token", Global.oAuthAccessToken));
		allParams.add(new NameValuePair("oauth_version", oAuthVersion));
		allParams.addAll(urlParams);

		Collections.sort(allParams, new NvpComparator());

		StringBuffer signatureBaseString3 = new StringBuffer();
		for (int i = 0; i < allParams.size(); i++) {
			NameValuePair nvp = allParams.get(i);
			if (i > 0) {
				signatureBaseString3.append("&");
			}
			signatureBaseString3.append(nvp.getName() + "=" + nvp.getValue());
		}

		String signatureBaseStringTemplate = "%s&%s&%s";
		String signatureBaseString = String.format(signatureBaseStringTemplate,
				URLEncoder.encode(signatureBaseString1, "UTF-8"),
				URLEncoder.encode(signatureBaseString2, "UTF-8"),
				URLEncoder.encode(signatureBaseString3.toString(), "UTF-8"));

		signatureBaseString = signatureBaseString.replaceAll("%2C", "%252C");
		
//		 System.out.println("signatureBaseString: " + signatureBaseString);

		String compositeKey = URLEncoder.encode(Global.oAuthConsumerSecret,
				"UTF-8")
				+ "&"
				+ URLEncoder.encode(Global.oAuthAccessTokenSecret, "UTF-8");

		String oAuthSignature = computeSignature(signatureBaseString,
				compositeKey);
		
//		 System.out.println("oAuthSignature       : " + oAuthSignature);

		String oAuthSignatureEncoded = URLEncoder.encode(oAuthSignature,
				"UTF-8");
		
//		 System.out.println("oAuthSignatureEncoded: " +
//		 oAuthSignatureEncoded);

		String authorizationHeaderValueTempl = "OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"%s\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"%s\"";

		String authorizationHeaderValue = String.format(
				authorizationHeaderValueTempl, Global.oAuthConsumerKey,
				oAuthNonce, oAuthSignatureEncoded, oAuthSignatureMethod,
				oAuthTimestamp, Global.oAuthAccessToken, oAuthVersion);
		
//		 System.out.println("authorizationHeaderValue: "
//		 + authorizationHeaderValue);

		StringBuffer urlWithParams = new StringBuffer(url);
		for (int i = 0; i < urlParams.size(); i++) {
			if (i == 0) {
				urlWithParams.append("?");
			} else {
				urlWithParams.append("&");
			}
			NameValuePair urlParam = urlParams.get(i);
			urlWithParams
					.append(urlParam.getName() + "=" + urlParam.getValue());
		}

//		 System.out.println("urlWithParams: " + urlWithParams.toString());
//		 System.out.println("authorizationHeaderValue:"
//		 + authorizationHeaderValue);

		GetMethod getMethod = new GetMethod(urlWithParams.toString());
		getMethod.addRequestHeader("Authorization", authorizationHeaderValue);

		HttpClient cli = new HttpClient();
		cli.executeMethod(getMethod);

//		 long responseContentLength = getMethod.getResponseContentLength();
//		 System.out.println("responseContentLength:" + responseContentLength);

		return getMethod.getResponseBodyAsString();
	}

	private static String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException,
			Exception {
		SecretKey secretKey = null;

		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");

		mac.init(secretKey);

		byte[] text = baseString.getBytes();

		return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}

	private static String getTimestamp() {
		long millis = System.currentTimeMillis();
		long secs = millis / 1000;
		return String.valueOf(secs);
	}

}
