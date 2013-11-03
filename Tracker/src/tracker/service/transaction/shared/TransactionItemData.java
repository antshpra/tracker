package tracker.service.transaction.shared;

import java.io.Serializable;
import java.util.Date;

public class TransactionItemData implements Serializable {

	private static final long serialVersionUID = -4346143094216780110L;

	private String transactionItemId;

	private String transactionId;

	private Date transactionDate;
	
	private String description;
	
	private Date creationDate;
		
	private String createdBy;

	
	public String getId() { return this.transactionItemId; }

	public String getTransactionId() { return this.transactionId; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }


	public void setId( String id ) { this.transactionItemId = id; }
	
	public void setTransactionId( String transactionId ) { this.transactionId = transactionId; }

	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

}
