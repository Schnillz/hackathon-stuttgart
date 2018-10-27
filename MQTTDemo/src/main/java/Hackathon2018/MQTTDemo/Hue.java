package Hackathon2018.MQTTDemo;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Hue {


private static final String URL = "http://172.16.12.253/api/ttvXHFpFCdbabaRrGurhTikoAIGSgwk1sZ2rkBTk/lights/2/state";
	
	public static void setHue(int hue) {
String body = "{\"on\":true, \"sat\":254, \"bri\":100,\"hue\":"+ hue + "}";
		
		try {
			URL url = new URL(URL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(
			    httpCon.getOutputStream());
			out.write(body);
			out.close();
			httpCon.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		setHue(1000);
		
	}
}