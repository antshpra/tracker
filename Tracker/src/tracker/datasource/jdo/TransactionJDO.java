package tracker.datasource.jdo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable( table = "TRANSACTION" )
public class TransactionJDO {

	@Persistent( column = "TRANSACTION_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionId;

	@Persistent( column = "TRANSACTION_DATE" )
	private Date transactionDate;
	
	@Persistent( column = "DESCRIPTION" )
	private String description;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
		
	@Persistent( column = "CREATED_BY" )
	private String createdBy;
	
	@NotPersistent
	private List<TransactionItemJDO> transactionItemJDOList;
	
	
	public String getId() { return KeyFactory.keyToString( this.transactionId ); }
	
	public Date getTransactionDate() { return new Date( this.transactionDate.getTime() ); }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return new Date( this.creationDate.getTime() ); }

	public String getCreatedBy() { return this.createdBy; }
	
	public List<TransactionItemJDO> getTransactionItemJDOList() { return this.transactionItemJDOList; }

	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }
	
	public void addTransactionItemJDO( TransactionItemJDO transactionItemJDO ) {
		if( this.transactionItemJDOList == null )
			this.transactionItemJDOList = new LinkedList<TransactionItemJDO>();
		this.transactionItemJDOList.add( transactionItemJDO );
	}

}
