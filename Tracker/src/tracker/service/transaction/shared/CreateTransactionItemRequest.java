package tracker.service.transaction.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class CreateTransactionItemRequest extends Request {

	private static final long serialVersionUID = -5056758107795072352L;

	@RequiredField
	private String transactionId;

	@RequiredField
	private String transactionItemTypeId;
	
	private Date transactionDate;
	
	@RequiredField
	private Double amount;
	
	private String note;
	
	
	public String getTransactionId() { return this.transactionId; }
	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }

	public Date getTransactionDate() { return this.transactionDate; }

	public double getAmount() { return this.amount; }
	
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
		assertNonNull( transactionDate );
		this.transactionDate = transactionDate;
	}
	
	public void setAmount( double amount ) {
		assertNonZero( amount );
		this.amount = amount;
	}

	public void setNote( String note ) {
		if( note != null && note.trim().length() != 0 )
			this.note = note.trim();
	}
	
}
