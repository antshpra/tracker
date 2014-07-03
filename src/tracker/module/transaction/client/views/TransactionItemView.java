package tracker.module.transaction.client.views;

import tracker.service.transaction.shared.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionItemView extends Composite {

	public abstract void setTransactionItemData( TransactionItemData transactionItemData );

}
