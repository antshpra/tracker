package tracker.service.transaction.shared;

import java.util.List;

import tracker.service.transaction.shared.data.TransactionItemData;
import antshpra.gwt.rpc.shared.Response;

public class GetTransactionItemListResponse extends Response {

	private List<TransactionItemData> transactionItemDataList;

	
	public List<TransactionItemData> getTransactionItemDataList() {
		return this.transactionItemDataList;
	}

	public void setTransactionItemDataList( List<TransactionItemData> transactionItemDataList ) {
		this.transactionItemDataList = transactionItemDataList;
	}
	
}
