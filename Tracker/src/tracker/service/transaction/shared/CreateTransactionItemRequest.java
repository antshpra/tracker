package tracker.service.transaction.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class CreateTransactionItemRequest extends Request {

	private static final long serialVersionUID = -5056758107795072352L;

	@RequiredField
	private String transactionId;

	private Date transactionDate;
	
	@RequiredField
	private String description;
	
	
	public String getTransactionId() { return this.transactionId; }
	
	public Date getTransactionDate() { return this.transactionDate; }

	public String getDescription() { return this.description; }
	
	
	public void setTransactionId( String transactionId ) {
		assertNonNull( transactionId );
		assertNonEmpty( transactionId );
		this.transactionId = transactionId;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		assertNonNull( transactionDate );
		this.transactionDate = transactionDate;
	}

	public void setDescription( String description ) {
		assertNonNull( description );
		assertNonEmpty( description );
		this.description = description;
	}
	
}
