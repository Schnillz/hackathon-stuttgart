import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.Group;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.InvalidMessage;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.Message.Header;
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
import quickfix.field.BeginString;
import quickfix.field.BidPx;
import quickfix.field.ClOrdID;
import quickfix.field.ContractMultiplier;
import quickfix.field.DeliverToCompID;
import quickfix.field.HandlInst;
import quickfix.field.IDSource;
import quickfix.field.ListID;
import quickfix.field.ListSeqNo;
import quickfix.field.MaturityDay;
import quickfix.field.MaturityMonthYear;
import quickfix.field.MsgType;
import quickfix.field.NoOrders;
import quickfix.field.NoRelatedSym;
import quickfix.field.OfferPx;
import quickfix.field.OfferSize;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.QuoteReqID;
import quickfix.field.SecurityDesc;
import quickfix.field.SecurityID;
import quickfix.field.SecurityType;
import quickfix.field.SenderCompID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.TotNoOrders;
import quickfix.field.TransactTime;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.Quote;
import quickfix.fix42.QuoteRequest;

public class QuoteRequestSender implements Application {

	private static volatile SessionID sessionID;

	@Override
	public void onCreate(SessionID sessionID) {
		System.out.println(this.getClass().getName() + " OnCreate");
	}

	@Override
	public void onLogon(SessionID sessionID) {
		System.out.println(this.getClass().getName() + " OnLogon");
		QuoteRequestSender.sessionID = sessionID;
	}

	@Override
	public void onLogout(SessionID sessionID) {
		System.out.println(this.getClass().getName() + " OnLogout");
		QuoteRequestSender.sessionID = null;
	}

	@Override
	public void toAdmin(Message message, SessionID sessionID) {
		System.out.println(this.getClass().getName() + " ToAdmin");
	}

	@Override
	public void fromAdmin(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		System.out.println(this.getClass().getName() + " FromAdmin");
	}

	@Override
	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
		System.out.println(this.getClass().getName() + " ToApp: " + message);
	}

	@Override
	public void fromApp(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		System.out.println(this.getClass().getName() + " FromApp");
	}

	public void connect() throws ConfigError, FileNotFoundException, InterruptedException {
		String settingsFileName = "C:\\repositories\\Hackathon2018\\hackathon-stuttgart\\QuickFixDemo\\src\\initiator.config";

		File file = new File(settingsFileName);

		SessionSettings settings = new SessionSettings(new FileInputStream(file));

		Application application = new QuoteRequestSender();
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

	@Deprecated
	public void sendQuoteRequest() throws SessionNotFound {
		System.out.println("Build quote request object...");

		QuoteRequest request = new QuoteRequest();
		QuoteReqID quoteReqID = new QuoteReqID("HackathonStuttgart-1234");

		// request.setField(new DeliverToCompID("CITIDE"));

		request.set(quoteReqID);
		request.set(new NoRelatedSym(1));

		request.setField(new Symbol("BAADERBK"));
		request.setField(new SecurityID("DE0005140008"));
		request.setField(new IDSource("4"));
		request.setField(new OrderQty(123));

		System.out.println("Send quote request");

		Session.sendToTarget(request, sessionID);

		System.out.println("Sent quote request");
	}

	public void sendQuoteRequestWithGroup() {
		
		System.out.println("Build quote request message");
		
		Hue.setHue(10000);
		
		Message message = new Message();
		Header header = message.getHeader();
		header.setField(new BeginString("FIX.4.2"));
		header.setField(new MsgType("R"));
		header.setField(new SenderCompID("BAADER"));
		header.setField(new TargetCompID("CATSOS"));
		header.setField(new DeliverToCompID("CITIDE"));

		// QuoteReqID
		String quoteRequestID = "HackStuttgart" + System.currentTimeMillis();
		message.setString(131 , quoteRequestID);
		// NoRelatedSym
		message.setString(146 , "1");

		
		Group group = new Group(146, 55);
		group.setField(new Symbol("BAADERBK"));
		group.setField(new SecurityID("DE0005140008"));
		group.setField(new IDSource("4"));
		group.setField(new SecurityType("OPT"));
		group.setField(new OrderQty(123));
		message.addGroup(group);

		try {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Session.sendToTarget(message, sessionID);
			
			System.out.println("Sent quote request message");

			
		} catch (SessionNotFound e) {
			e.printStackTrace();
		}

	}

	@Deprecated
	public void sendQuoteRequestString() {

		char soh = '\001';

		try {
			Message msg = new Message("8=FIX.4.2" + soh + "9=130" + soh + "35=R" + soh + "34=78" + soh + "49=BAADER"
					+ soh + "52=20181027-13:11:12.394" + soh + "56=CATSOS" + soh + "22=4" + soh + "38=123" + soh
					+ "48=DE0005140008" + soh + "55=BAADERBK" + soh + "131=HackathonStuttgart-1234" + soh + "146=1"
					+ soh + "10=013" + soh);
			Session.sendToTarget(msg, sessionID);
		} catch (InvalidMessage e) {
			e.printStackTrace();
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args)
			throws ConfigError, FileNotFoundException, InterruptedException, SessionNotFound {

		QuoteRequestSender app = new QuoteRequestSender();

		app.connect();

		// final String orderId = "342";
		// NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId),
		// new HandlInst('1'), new Symbol("6758.T"),
		// new Side(Side.BUY), new TransactTime(), new OrdType(OrdType.MARKET));
		//
		//
		// Session.sendToTarget(newOrder, sessionID);

		// app.sendQuoteRequest();

		app.sendQuoteRequestWithGroup();

		Thread.sleep(5000);
	}

}