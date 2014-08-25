package tracker.service.transaction.shared;

import java.util.List;

import tracker.service.transaction.shared.data.TransactionData;
import antshpra.gwt.rpc.shared.Response;

public class GetTransactionListResponse extends Response {

	private List<TransactionData> transactionDataList;

	
	public List<TransactionData> getTransactionDataList() {
		return this.transactionDataList;
	}

	public void setTransactionDataList( List<TransactionData> transactionDataList ) {
		this.transactionDataList = transactionDataList;
	}
	
}
