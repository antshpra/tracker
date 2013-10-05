/**
 * 
 */
package tracker.server.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable( table = "TRANSACTION" )
public class Transaction {

	@Persistent( column = "TRANSACTION_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "DESCRIPTION" )
	private String description;
	
	@Persistent( column = "TRANSACTION_DATE" )
	private Date transactionDate;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
		
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	
	public long getId() { return this.id; }
	
	public String getDescription() { return this.description; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }

	
	public void setDescription( String description ) { this.description = description; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

}
