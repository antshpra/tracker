package tracker.datasource.jdo;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

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
	
	@Persistent( column = "DESCRIPTION" )
	private String description;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	
	public String getId() { return KeyFactory.keyToString( this.transactionItemId ); }
	
	public String getTransactionId() { return this.transactionId; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public Date getTransactionDate() { return new Date( this.transactionDate.getTime() ); }
	
	public double getAmount() { return this.amount == null ? 0 : ( (double) this.amount ) / 100; }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return new Date( this.creationDate.getTime() ); }

	public String getCreatedBy() { return this.createdBy; }

	
	public void setTransactionId( String transactionId ) { this.transactionId = transactionId; }
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }

	public void setAmount( double amount ) { this.amount = (long) ( amount * 100 ); }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

}
