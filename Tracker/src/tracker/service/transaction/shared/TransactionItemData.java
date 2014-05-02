package tracker.service.transaction.shared;

import java.io.Serializable;
import java.util.Date;

public class TransactionItemData implements Serializable {

	private static final long serialVersionUID = -4346143094216780110L;

	private String transactionItemId;

	private String transactionId;

	private TransactionItemTypeData transactionItemType;
	
	private Date transactionDate;
	
	private long amount;
	
	private String note;

	private Date creationDate;
		
	private String createdBy;

	
	public String getId() { return this.transactionItemId; }

	public String getTransactionId() { return this.transactionId; }
	
	public TransactionItemTypeData getTransactionItemType() { return this.transactionItemType; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public double getAmount() { return ( (double) this.amount ) / 100; }
	
	public String getNote() { return this.note; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }


	public void setId( String id ) { this.transactionItemId = id; }
	
	public void setTransactionId( String transactionId ) { this.transactionId = transactionId; }

	public void setTransactionItemType( TransactionItemTypeData transactionItemTypeData ) { this.transactionItemType = transactionItemTypeData; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setAmount( double amount ) { this.amount = (long) ( amount * 100 ); }
	
	public void setNote( String note ) { this.note = note; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

}
