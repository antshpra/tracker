package tracker.service.transaction.shared.data;

import java.util.Date;

import tracker.commons.shared.Amount;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionItemData implements IsSerializable {

	private String transactionItemId;

	private String transactionId;

	private TransactionItemTypeData transactionItemType;
	
	private Date transactionDate;
	
	private Amount amount;
	
	private String note;

	private Date creationDate;
		
	private String createdBy;

	private TransactionData transactionData;
	
	
	public String getId() { return this.transactionItemId; }

	public String getTransactionId() { return this.transactionId; }
	
	public TransactionItemTypeData getTransactionItemType() { return this.transactionItemType; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public Amount getAmount() { return this.amount; }
	
	public String getNote() { return this.note; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }

	public TransactionData getTransactionData() { return this.transactionData; }


	public void setId( String id ) { this.transactionItemId = id; }
	
	public void setTransactionId( String transactionId ) { this.transactionId = transactionId; }

	public void setTransactionItemType( TransactionItemTypeData transactionItemTypeData ) { this.transactionItemType = transactionItemTypeData; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setAmount( Amount amount ) { this.amount = amount; }
	
	public void setNote( String note ) { this.note = note; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }
	
	public void setTransactionData( TransactionData transactionData ) { this.transactionData = transactionData; }

}
