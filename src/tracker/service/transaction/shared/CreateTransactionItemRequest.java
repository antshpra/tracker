package tracker.service.transaction.shared;

import java.util.Date;

import tracker.commons.shared.Amount;
import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class CreateTransactionItemRequest extends Request {

	@RequiredField
	private String transactionId;

	@RequiredField
	private String transactionItemTypeId;
	
	private Date transactionDate;
	
	@RequiredField
	private Amount amount;
	
	private String note;
	
	
	public String getTransactionId() { return this.transactionId; }
	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }

	public Date getTransactionDate() { return this.transactionDate; }

	public Amount getAmount() { return this.amount; }
	
	public String getNote() { return this.note; }

	
	public void setTransactionId( String transactionId ) {
		assertNonNull( transactionId );
		assertNonEmpty( transactionId );
		this.transactionId = transactionId;
	}
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate;
	}
	
	public void setAmount( Amount amount ) {
		assertNonNull( amount );
		this.amount = amount;
	}

	public void setNote( String note ) {
		if( note != null && note.trim().length() != 0 )
			this.note = note.trim();
	}
	
}
