package tracker.datasource.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable( table = "TRANSACTION_ITEM" )
public class TransactionItemJDO {

	@Persistent( column = "TRANSACTION_ITEM_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key key;

	@Persistent( column = "TRANSACTION_ID" )
	private Long transactionId;
	
	@Persistent( column = "TRANSACTION_DATE" )
	private Date transactionDate;
	
	@Persistent( column = "DESCRIPTION" )
	private String description;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	
	public long getId() { return this.key.getId(); }
	
	public long getTransactionId() { return this.transactionId; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }

	
	public void setKey( Key key ) { this.key = key; }
	
	public void setTransactionId( long transactionId ) { this.transactionId = transactionId; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

}
