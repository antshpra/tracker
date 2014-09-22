package tracker.service.shared;

import antshpra.gwt.rpc.shared.RequiredField;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionRequest implements IsSerializable {

	@RequiredField
	private String transactionId;

	
	public String getTransactionId() { return  this.transactionId; }
	
	
	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
}
