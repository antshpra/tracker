package tracker.module.transaction.client.views;

import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.data.TransactionData;

import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionInputView extends Composite {

	public abstract boolean validateInputs();
	
	public abstract CreateTransactionRequest getCreateTransactionRequest();
	
	public abstract void setTransactionData( TransactionData transactionData );
	
}
