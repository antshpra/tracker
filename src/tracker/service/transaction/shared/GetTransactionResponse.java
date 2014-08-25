package tracker.service.transaction.shared;

import tracker.service.transaction.shared.data.TransactionData;
import antshpra.gwt.rpc.shared.Response;

public class GetTransactionResponse extends Response {

	private TransactionData transactionData;

	
	public TransactionData getTransactionData() {
		return this.transactionData;
	}

	public void setTransactionData( TransactionData transactionData ) {
		this.transactionData = transactionData;
	}
	
}
