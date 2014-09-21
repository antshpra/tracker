package tracker.commons.client;

import java.util.List;

import tracker.service.transaction.shared.data.TransactionItemData;
import tracker.service.transaction.shared.data.TransactionItemTypeData;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionItemDataInputView extends Composite {

	public abstract HandlerRegistration addDeleteButtonClickHandler(
			ClickHandler clickHandler );
			
	public abstract boolean validateInputs();
	
	public abstract TransactionItemData getTransactionItemData();

	public abstract void setTransactionItemData(
			TransactionItemData transactionItemData );

	public abstract void setTransactionItemTypeDataList(
			List<TransactionItemTypeData> transactionItemTypeDataList );

}
