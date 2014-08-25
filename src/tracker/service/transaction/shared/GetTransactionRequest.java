package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetTransactionRequest extends Request {

	@RequiredField
	private String transactionId;

	
	public String getTransactionId() { return  this.transactionId; }
	
	
	public void setTransactionId( String transactionId ) {
		assertNonNull( transactionId );
		assertNonEmpty( transactionId );
		this.transactionId = transactionId;
	}
	
}
