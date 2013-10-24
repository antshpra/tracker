package tracker.service.transaction.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

@SuppressWarnings( "serial" )
public class CreateTransactionItemRequest extends Request {

	private Long transactionId;

	private Date transactionDate;
	
	@RequiredField
	private String description;
	
	
	public Long getTransactionId() { return this.transactionId; }
	
	public Date getTransactionDate() { return this.transactionDate; }

	public String getDescription() { return this.description; }
	
	
	public void setTransactionId( Long transactionId ) {
		assertNonNull( transactionId );
		assertNonNegative( transactionId.longValue() );
		this.transactionId = transactionId;
	}
	
	public void setDescription( String description ) {
		assertNonNull( description );
		assertNonEmpty( description );
		this.description = description;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		assertNonNull( transactionDate );
		this.transactionDate = transactionDate;
	}
	
}
