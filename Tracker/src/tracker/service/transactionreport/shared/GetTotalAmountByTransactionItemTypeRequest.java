package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetTotalAmountByTransactionItemTypeRequest extends Request {

	private static final long serialVersionUID = 3444249762736128186L;

	@RequiredField
	private String transactionItemTypeId;

	
	public String getTransactionItemTypeId() { return  this.transactionItemTypeId; }
	
	
	public void settransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}

}
