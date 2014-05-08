package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Response;

public class CreateTransactionResponse extends Response {

	private static final long serialVersionUID = 6007868757106377727L;

	private String transactionId;

	
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
}
