package tracker.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import tracker.data.transfer.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable( table = "TRANSACTION" )
public class TransactionEntity implements Transaction {

	@PrimaryKey
	@Persistent( column = "TRANSACTION_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionId;

	@Persistent( column = "TRANSACTION_DATE" )
	private Date transactionDate;
	
	@Persistent( column = "DESCRIPTION" )
	private String description;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
		
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	
	public String getId() {
		return KeyFactory.keyToString( this.transactionId );
	}
	
	public Date getTransactionDate() {
		return new Date( this.transactionDate.getTime() );
	}
	
	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return new Date( this.creationDate.getTime() );
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
	public String getCreatedBy() {
		return this.createdBy;
	}
	
	public void setCreatedBy( String createdBy ) {
		this.createdBy = createdBy;
	}
	
}
