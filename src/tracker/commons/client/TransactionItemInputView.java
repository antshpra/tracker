package tracker.commons.client;

import java.util.List;

import tracker.service.transaction.shared.data.TransactionItemData;
import tracker.service.transaction.shared.data.TransactionItemTypeData;

import com.google.gwt.user.client.ui.Composite;

public abstract class TransactionItemInputView extends Composite {

	public abstract boolean validateInputs();
	
	public abstract TransactionItemData getTransactionItemData();

	public abstract void setTransactionItemData(
			TransactionItemData transactionItemData );

	public abstract void setTransactionItemTypeDataList(
			List<TransactionItemTypeData> transactionItemTypeDataList );

}
