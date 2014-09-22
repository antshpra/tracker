package tracker.commons.client;

import tracker.service.shared.data.TransactionItemData;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionItemDataInputView extends Composite {

	public abstract HandlerRegistration addDeleteButtonClickHandler(
			ClickHandler clickHandler );
			
	public abstract void setEnabled( boolean enabled );
	
	public abstract boolean validateInputs();
	
	public abstract TransactionItemData getTransactionItemData();

	public abstract void setTransactionItemData(
			TransactionItemData transactionItemData );

}
