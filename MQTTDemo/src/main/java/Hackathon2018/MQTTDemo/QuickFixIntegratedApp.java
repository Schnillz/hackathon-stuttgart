package Hackathon2018.MQTTDemo;

public class QuickFixIntegratedApp {

public static void main(String[] args) {
		
		try {
			
			Hue.setHue(50000);

			
			QuoteRequestSender requestSender = new QuoteRequestSender();

			requestSender.connect();
			
			QuoteSender quoteSender = new QuoteSender();
			
			quoteSender.connect();
			
			requestSender.sendQuoteRequestWithGroup();			
			
		//	quoteSender.sendQuoteWithGroup();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
}
	
}
