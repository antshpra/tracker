package tracker.module.transaction.client.views;

import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionView extends Composite implements MouseOverHandler, MouseOutHandler, MouseUpHandler {

	public abstract void setTransactionData( TransactionData transactionData );

}
