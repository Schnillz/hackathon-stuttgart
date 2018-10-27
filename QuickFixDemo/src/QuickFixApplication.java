import java.io.FileNotFoundException;

import quickfix.ConfigError;
import quickfix.SessionNotFound;

public class QuickFixApplication {

	public static void main(String[] args) {
		
		try {
			Hue.setHue(50000);

			
			QuoteRequestSender requestSender = new QuoteRequestSender();

			requestSender.connect();
			
			QuoteSender quoteSender = new QuoteSender();
			
			quoteSender.connect();
			
			requestSender.sendQuoteRequestWithGroup();			
			
		//	quoteSender.sendQuoteWithGroup();
			
		} catch (FileNotFoundException | ConfigError | InterruptedException e) {
			e.printStackTrace();
		} 
		
		
		
	}
	
}
