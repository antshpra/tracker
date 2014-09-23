package tracker.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import tracker.commons.shared.TransactionState;
import tracker.data.transfer.TransactionItem;

import com.claymus.commons.client.Amount;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable( table = "TRANSACTION_ITEM" )
public class TransactionItemEntity implements TransactionItem {

	@PrimaryKey
	@Persistent( column = "TRANSACTION_ITEM_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionItemId;

	@Persistent( column = "TRANSACTION_ID" )
	@Extension( vendorName = "datanucleus", key = "gae.parent-pk", value = "true" )
	private String transactionId;

	@Persistent( column = "TRANSACTION_ITEM_TYPE_ID" )
	private String transactionItemTypeId;
	
	@Persistent( column = "TRANSACTION_DATE" )
	private Date transactionDate;
	
	@Persistent( column = "AMOUNT" )
	private Long amount;
	
	@Persistent( column = "NOTE" )
	private String note;

	@Persistent( column = "STATE" )
	private TransactionState transactionState;
	
	@Persistent( column = "ORDER" )
	private Long order;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	@Persistent( column = "LAST_UPDATION_DATE" )
	private Date lastUpdationDate;
	
	
	public String getId() {
		return KeyFactory.keyToString( transactionItemId );
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
	public String getTransactionItemTypeId() {
		return transactionItemTypeId;
	}
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public Date getTransactionDate() {
		return new Date( transactionDate.getTime() );
	}

	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate; 
	}

	public Amount getAmount() {
		return new Amount( amount );
	}
	
	public void setAmount( Amount amount ) {
		this.amount = amount.getValue(); 
	}
	
	public String getNote() {
		return note; 
	}
	
	public void setNote( String note ) {
		this.note = note; 
	}
	
	public TransactionState getState() {
		return transactionState; 
	}
	
	public void setState( TransactionState transactionState ) {
		this.transactionState = transactionState; 
	}

	public Long getOrder() {
		return order;
	}
	
	public void setOrder( Long order ) {
		this.order = order;
	}
	
	public Date getCreationDate() {
		return new Date( creationDate.getTime() ); 
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate; 
	}
	
	public String getCreatedBy() {
		return createdBy; 
	}

	public void setCreatedBy( String createdBy ) {
		this.createdBy = createdBy; 
	}
	
	public Date getLastUpdationDate() {
		return new Date( lastUpdationDate.getTime() ); 
	}
	
	public void setLastUpdationDate( Date lastUpdationDate ) {
		this.lastUpdationDate = lastUpdationDate;
	}

}
