//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.concurrent.CountDownLatch;
//
//import quickfix.Acceptor;
//import quickfix.Application;
//import quickfix.ConfigError;
//import quickfix.DefaultMessageFactory;
//import quickfix.DoNotSend;
//import quickfix.FieldNotFound;
//import quickfix.FileStoreFactory;
//import quickfix.IncorrectDataFormat;
//import quickfix.IncorrectTagValue;
//import quickfix.LogFactory;
//import quickfix.Message;
//import quickfix.MessageFactory;
//import quickfix.MessageStoreFactory;
//import quickfix.RejectLogon;
//import quickfix.ScreenLogFactory;
//import quickfix.SessionID;
//import quickfix.SessionNotFound;
//import quickfix.SessionSettings;
//import quickfix.SocketAcceptor;
//import quickfix.UnsupportedMessageType;
//
//public class ServerApplication implements Application {
//
//	@Override
//	public void onCreate(SessionID sessionID) {
//	}
//
//	@Override
//	public void onLogon(SessionID sessionID) {
//	}
//
//	@Override
//	public void onLogout(SessionID sessionID) {
//	}
//
//	@Override
//	public void toAdmin(Message message, SessionID sessionID) {
//	}
//
//	@Override
//	public void fromAdmin(Message message, SessionID sessionID)
//			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//	}
//
//	@Override
//	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
//	}
//
//	@Override
//	public void fromApp(Message message, SessionID sessionID)
//			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
//		System.out.println("FromApp: " + message);
//	}
//
//	public static void main(String[] args)
//			throws ConfigError, FileNotFoundException, InterruptedException, SessionNotFound {
//				
//		String settingsFileName = "C:\\workspaces\\Hackathon2018\\QuickFixDemo\\src\\acceptor.config";
//		
//		File file = new File(settingsFileName);
//
//		Path path = file.toPath();
//		try {
//			byte[] fileContent = Files.readAllBytes(path);
//			System.out.println(new String(fileContent));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		SessionSettings settings = new SessionSettings(new FileInputStream(file));
//
//				
//		Application application = new ServerApplication();
//		MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
//		LogFactory logFactory = new ScreenLogFactory(true, true, true);
//		MessageFactory messageFactory = new DefaultMessageFactory();
//
//		Acceptor initiator = new SocketAcceptor(application, messageStoreFactory, settings, logFactory, messageFactory);
//		initiator.start();
//
//		CountDownLatch latch = new CountDownLatch(1);
//		latch.await();
//	}
//
//}