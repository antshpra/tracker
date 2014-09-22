package tracker.service.shared;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveTransactionRequest implements IsSerializable {

	private TransactionData transactionData;


	@SuppressWarnings("unused")
	private SaveTransactionRequest() {}
	
	public SaveTransactionRequest( TransactionData transactionData ) {
		this.transactionData = transactionData;
	}
	
	
	public TransactionData getTransactionData() {
		return transactionData;
	}

}
