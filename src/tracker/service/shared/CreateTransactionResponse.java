package tracker.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CreateTransactionResponse implements IsSerializable {

	private String transactionId;

	
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
}
