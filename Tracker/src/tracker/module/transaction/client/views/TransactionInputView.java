package tracker.module.transaction.client.views;

import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionInputView extends Composite implements ClickHandler {

	public abstract boolean validateInputs();
	
	public abstract CreateTransactionRequest getCreateTransactionRequest();
	
	public abstract void setTransactionData( TransactionData transactionData );
	
	@Override
	public void onClick( ClickEvent event ) { }
	
}
