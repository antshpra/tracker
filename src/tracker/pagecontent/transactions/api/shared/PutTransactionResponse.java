package tracker.pagecontent.transactions.api.shared;

import tracker.data.transfer.shared.TransactionData;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PutTransactionResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private TransactionData transaction;
	

	@SuppressWarnings("unused")
	private PutTransactionResponse() {}
	
	public PutTransactionResponse( TransactionData transaction ) {
		this.transaction = transaction;
	}
	
}
