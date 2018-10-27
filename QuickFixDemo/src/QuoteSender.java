import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RejectLogon;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;
import quickfix.field.BidPx;
import quickfix.field.ClOrdID;
import quickfix.field.ContractMultiplier;
import quickfix.field.HandlInst;
import quickfix.field.IDSource;
import quickfix.field.MaturityDay;
import quickfix.field.MaturityMonthYear;
import quickfix.field.NoRelatedSym;
import quickfix.field.OfferPx;
import quickfix.field.OfferSize;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.QuoteReqID;
import quickfix.field.SecurityDesc;
import quickfix.field.SecurityID;
import quickfix.field.SecurityType;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TransactTime;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.Quote;
import quickfix.fix42.QuoteRequest;

public class QuoteSender implements Application {

	private static volatile SessionID sessionID;

	@Override
	public void onCreate(SessionID sessionID) {
		System.out.println("OnCreate");
	}

	@Override
	public void onLogon(SessionID sessionID) {
		System.out.println("OnLogon");
		QuoteSender.sessionID = sessionID;
	}

	@Override
	public void onLogout(SessionID sessionID) {
		System.out.println("OnLogout");
		QuoteSender.sessionID = null;
	}

	@Override
	public void toAdmin(Message message, SessionID sessionID) {
		System.out.println("ToAdmin");
	}

	@Override
	public void fromAdmin(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		System.out.println("FromAdmin");
	}

	@Override
	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
		System.out.println("ToApp: " + message);
	}

	@Override
	public void fromApp(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		System.out.println("FromApp");
	}
	
	public void connect() throws InterruptedException, FileNotFoundException, ConfigError {
		String settingsFileName = "C:\\workspaces\\Hackathon2018\\QuickFixDemo\\src\\initiator2.config";
		
		File file = new File(settingsFileName);

		
		SessionSettings settings = new SessionSettings(new FileInputStream(file));

		Application application = new QuoteSender();
		MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
		LogFactory logFactory = new ScreenLogFactory(true, true, true);
		MessageFactory messageFactory = new DefaultMessageFactory();

		Initiator initiator = new SocketInitiator(application, messageStoreFactory, settings, logFactory,
				messageFactory);
		initiator.start();

		while (sessionID == null) {
			Thread.sleep(1000);
		}
		
		System.out.println("SessionID is now " + sessionID);
	}
	
	public void sendQuote() throws SessionNotFound {
		QuoteReqID quoteReqID = new QuoteReqID("HackathonStuttgart-1234");
		
		Quote quote = new Quote();
		quote.set(quoteReqID);
		QuoteReqID quoteReqID2 = new QuoteReqID("Hackathon-Quote-1234");
		quote.set(quoteReqID2);
		quote.set(new Symbol("MM1"));
		quote.set(new SecurityID("DE0005140008"));
		quote.set(new IDSource("4"));
		quote.set(new SecurityType("CS"));
		quote.set(new MaturityMonthYear("205012"));
		quote.set(new MaturityDay("31"));
		quote.set(new ContractMultiplier(1));
		quote.set(new SecurityDesc("DE0005140008"));
		quote.set(new BidPx(9.769));
		quote.set(new OfferPx(9.771));
		quote.set(new OfferSize(1024));
		
		System.out.println("Send quote...");
		
		Session.sendToTarget(quote, sessionID);
		System.out.println("Sent quote...");
	}

	public static void main(String[] args)
			throws ConfigError, FileNotFoundException, InterruptedException, SessionNotFound {
		
		QuoteSender app2 = new QuoteSender();
		
		app2.connect();
		

//		final String orderId = "342";
//		 NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId), new HandlInst('1'), new Symbol("6758.T"),
//		            new Side(Side.BUY), new TransactTime(), new OrdType(OrdType.MARKET));
//
//		 
//		Session.sendToTarget(newOrder, sessionID);
		


		
		Thread.sleep(5000);

		app2.sendQuote();

		
		Thread.sleep(5000);
	}

}