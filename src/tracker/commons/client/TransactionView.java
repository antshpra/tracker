package tracker.commons.client;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionView extends Composite {

	public abstract void setTransactionData( TransactionData transactionData );

}
