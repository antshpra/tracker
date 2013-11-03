package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetTransactionRequest extends Request {

	private static final long serialVersionUID = -9191816072515304760L;

	@RequiredField
	private String transactionId;

	private boolean loadTransactionItemList = true;
	
	
	public String getTransactionId() { return  this.transactionId; }
	
	public boolean shouldLoadTransactionItemList() { return this.loadTransactionItemList; }
	
	
	public void setTransactionId( String transactionId ) {
		assertNonNull( transactionId );
		assertNonEmpty( transactionId );
		this.transactionId = transactionId;
	}
	
	public void setLoadTransactionItemList( boolean loadTransactionItemList ) {
		this.loadTransactionItemList = loadTransactionItemList;
	}
	
}
