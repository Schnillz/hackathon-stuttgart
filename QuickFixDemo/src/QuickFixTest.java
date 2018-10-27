import java.io.FileInputStream;
import java.util.Random;

import org.apache.commons.logging.LogFactory;

import quickfix.Application;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.MessageStoreFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.field.ClOrdID;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.Product;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TransactTime;
import quickfix.fix50sp2.MessageFactory;
import quickfix.fix50sp2.NewOrderSingle;

public class QuickFixTest {
	
	

	public static void main(String[] args) {
		NewOrderSingle order = new NewOrderSingle();
		order.setField(new Symbol("EURUSD"));
		 
        order.setField(new ClOrdID(Generate_Rand()));
        order.setField(new OrderQty(10));
        order.setField(new OrdType('1'));
        order.setField(new Side('1'));
        order.setField(new TransactTime());
        order.set(new Product(4));
        try {
			Session.sendToTarget(order, "PQRS", "XY");
		} catch (SessionNotFound e) {
			e.printStackTrace();
		} 
	}

	private static String Generate_Rand() {
		return "ID" + new Random().nextInt(2000);
	}
	
}
