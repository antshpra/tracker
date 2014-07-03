package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Response;

public class GetTransactionResponse extends Response {

	private static final long serialVersionUID = 1046384014461894889L;

	private TransactionData transactionData;

	
	public TransactionData getTransactionData() {
		return this.transactionData;
	}

	public void setTransactionData( TransactionData transactionData ) {
		this.transactionData = transactionData;
	}
	
}
