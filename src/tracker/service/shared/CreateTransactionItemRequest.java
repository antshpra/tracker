package tracker.service.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.RequiredField;

import com.claymus.commons.client.Amount;
import com.google.gwt.user.client.rpc.IsSerializable;

public class CreateTransactionItemRequest implements IsSerializable {

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
		this.transactionId = transactionId;
	}
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate;
	}
	
	public void setAmount( Amount amount ) {
		this.amount = amount;
	}

	public void setNote( String note ) {
		if( note != null && note.trim().length() != 0 )
			this.note = note.trim();
	}
	
}
