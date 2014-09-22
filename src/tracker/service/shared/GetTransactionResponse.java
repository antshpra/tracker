package tracker.service.shared;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionResponse implements IsSerializable {

	private TransactionData transactionData;

	
	public TransactionData getTransactionData() {
		return this.transactionData;
	}

	public void setTransactionData( TransactionData transactionData ) {
		this.transactionData = transactionData;
	}
	
}
