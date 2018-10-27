import java.io.FileNotFoundException;

import quickfix.ConfigError;
import quickfix.SessionNotFound;

public class QuickFixApplication {

	public static void main(String[] args) {
		
		try {
			QuoteRequestSender requestSender = new QuoteRequestSender();

			requestSender.connect();
			
			QuoteSender quoteSender = new QuoteSender();
			
			quoteSender.connect();
			
			requestSender.sendQuoteRequestWithGroup();			
			
		//	quoteSender.sendQuote();
			
		} catch (FileNotFoundException | ConfigError | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
