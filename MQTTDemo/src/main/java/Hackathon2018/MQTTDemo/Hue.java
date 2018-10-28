package Hackathon2018.MQTTDemo;



import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Hue {

	public static final int BLUE = 40000;
	public static final int VIOLET = 50000;
	public static final int GREEN = 20000;
	public static final int YELLOW = 10000;

private static final String URL = "http://172.16.12.253/api/ttvXHFpFCdbabaRrGurhTikoAIGSgwk1sZ2rkBTk/lights/2/state";
	
public static void setHue(int hue, int bri) {
	setHue(hue, bri, true);
}

public static void setHue(int hue, int bri, boolean state) {
	String body = "{\"on\":" + state + ", \"sat\":254, \"bri\":" + bri + ",\"hue\":"+ hue + "}";
	
	System.out.println("Set hue to hue = " + hue + " and bri = " + bri);
	
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

	public static void setHue(int hue) {
		setHue(hue, 100);
	}

	public static void main(String[] args) {
		setHue(1000);
		
	}
}