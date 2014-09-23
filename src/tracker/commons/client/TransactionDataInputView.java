package tracker.commons.client;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionDataInputView extends Composite {

	public abstract boolean validateInputs();
	
	public abstract void setEnabled( boolean enabled );

	public abstract TransactionData getTransactionData();
	
	public abstract void setTransactionData( TransactionData transactionData );
	
	public abstract void reset();

}
