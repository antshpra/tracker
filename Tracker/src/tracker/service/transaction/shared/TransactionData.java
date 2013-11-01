package tracker.service.transaction.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TransactionData implements Serializable {

	private static final long serialVersionUID = 8877764477932050297L;

	private String transactionId;

	private Date transactionDate;
	
	private String description;
	
	private Date creationDate;
		
	private String createdBy;
	
	private List<TransactionItemData> transactionItemDataList;

	
	public String getId() { return this.transactionId; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	public String getDescription() { return this.description; }
	
	public Date getCreationDate() { return this.creationDate; }

	public String getCreatedBy() { return this.createdBy; }

	public List<TransactionItemData> getTransactionItemDataList() { return this.transactionItemDataList; }


	public void setId( String id ) { this.transactionId = id; }
	
	public void setTransactionDate( Date transactionDate ) { this.transactionDate = transactionDate; }
	
	public void setDescription( String description ) { this.description = description; }
	
	public void setCreationDate( Date creationDate ) { this.creationDate = creationDate; }
	
	public void setCreatedBy( String createdBy ) { this.createdBy = createdBy; }

	public void addTransactionItemData( TransactionItemData transactionItemData ) {
		if( this.transactionItemDataList == null )
			this.transactionItemDataList = new LinkedList<TransactionItemData>();
		this.transactionItemDataList.add( transactionItemData );
	}

}
