package tracker.datasource.jdo;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import tracker.commons.shared.TransactionState;

import com.claymus.commons.client.Amount;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable( table = "TRANSACTION_ITEM" )
public class TransactionItemJDO {

	@Persistent( column = "TRANSACTION_ITEM_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
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
	
	
	public String getId() { return KeyFactory.keyToString( this.transactionItemId ); }
	
	public String getTransactionId() { return this.transactionId; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public Date getTransactionDate() { return new Date( this.transactionDate.getTime() ); }

	public Amount getAmount() { return new Amount( this.amount ); }
	
	public String getNote() { return this.note; }
	
	public TransactionState getState() { return this.transactionState; }
	
	public Long getOrder() {
		return order;
	}
	
	public void setOrder( Long order ) {
		this.order = order;
	}
	
	public Date getCreationDate() { return new Date( this.creationDate.getTime() ); }

	public String getCreatedBy() { return this.createdBy; }

	public Date getLastUpdationDate() { return new Date( this.lastUpdationDate.getTime() ); }
	
	
	public void setTransactionId( String transactionId ) { this.transactionId = transactionId; }
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }

	public void setAmount( Amount amount ) { this.amount = amount.getValue(); }
	
	public void setNote( String note ) { this.note = note; }
	
	public void setState( TransactionState transactionState ) { this.transactionState = transactionState; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }
	
	public void setLastUpdationDate( Date lastUpdationDate ) { this.lastUpdationDate = lastUpdationDate; }

}
