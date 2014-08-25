package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Response;

public class CreateTransactionResponse extends Response {

	private String transactionId;

	
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
}
