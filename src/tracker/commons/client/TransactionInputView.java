package tracker.commons.client;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionInputView extends Composite {

	public abstract void setEnabled( boolean enabled );
	
	public abstract boolean validateInputs();
	
	public abstract HandlerRegistration addSaveButtonClickHandler(
			ClickHandler clickHandler );

	public abstract TransactionData getTransactionData();
	
	public abstract void setTransactionData( TransactionData transactionData );
	
}
