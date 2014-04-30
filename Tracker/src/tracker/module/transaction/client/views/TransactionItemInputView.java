package tracker.module.transaction.client.views;

import java.util.List;

import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.TransactionItemData;
import tracker.service.transaction.shared.TransactionItemTypeData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionItemInputView extends Composite implements ClickHandler {

	public abstract boolean validateInputs();
	
	public abstract CreateTransactionItemRequest getCreateTransactionItemRequest();
	
	public abstract void setTransactionItemData( TransactionItemData transactionItemData );

	public abstract void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList );

	@Override
	public void onClick(ClickEvent event) { }

}
