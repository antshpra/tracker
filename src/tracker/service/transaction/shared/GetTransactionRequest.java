package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetTransactionRequest extends Request {

	private static final long serialVersionUID = -9191816072515304760L;

	@RequiredField
	private String transactionId;

	
	public String getTransactionId() { return  this.transactionId; }
	
	
	public void setTransactionId( String transactionId ) {
		assertNonNull( transactionId );
		assertNonEmpty( transactionId );
		this.transactionId = transactionId;
	}
	
}
