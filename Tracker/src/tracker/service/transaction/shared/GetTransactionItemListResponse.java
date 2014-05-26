package tracker.service.transaction.shared;

import java.util.List;

import antshpra.gwt.rpc.shared.Response;

public class GetTransactionItemListResponse extends Response {

	private static final long serialVersionUID = 8825530777185272937L;

	private List<TransactionItemData> transactionItemDataList;

	
	public List<TransactionItemData> getTransactionItemDataList() {
		return this.transactionItemDataList;
	}

	public void setTransactionItemDataList( List<TransactionItemData> transactionItemDataList ) {
		this.transactionItemDataList = transactionItemDataList;
	}
	
}
