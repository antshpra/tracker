package tracker.service.shared.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionData implements IsSerializable {

	private String transactionId;

	private Date transactionDate;
	
	private String description;
	
	private Date creationDate;
		
	private String createdBy;
	
	private List<TransactionItemData> transactionItemDataList;

	
	public TransactionData() {
		this.transactionItemDataList = new LinkedList<TransactionItemData>();
	}
	
	
	public String getId() {
		return this.transactionId;
	}
	
	public void setId( String id ) {
		this.transactionId = id;
	}
	
	public Date getTransactionDate() {
		return this.transactionDate;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate; 
	}
	
	public String getDescription() {
		return this.description; }
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return this.creationDate; 
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

	public List<TransactionItemData> getTransactionItemDataList() {
		return this.transactionItemDataList;
	}

	public void addTransactionItemData( TransactionItemData transactionItemData ) {
		this.transactionItemDataList.add( transactionItemData );
	}

}
