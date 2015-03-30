package tracker.service.shared.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionData implements IsSerializable {

	private String id;

	private Date transactionDate;
	
	private String description;
	
	private Date creationDate;
		
	private String createdBy;
	
	private List<TransactionItemData> transactionItemDataList;

	
	public TransactionData() {
		transactionItemDataList = new ArrayList<TransactionItemData>();
	}
	
	
	public String getId() {
		return id;
	}
	
	public void setId( String id ) {
		this.id = id;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate( Date transactionDate ) {
		this.transactionDate = transactionDate; 
	}
	
	public String getDescription() {
		return description; }
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return creationDate; 
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

	public List<TransactionItemData> getTransactionItemDataList() {
		return transactionItemDataList;
	}

	public void addTransactionItemData( TransactionItemData transactionItemData ) {
		transactionItemDataList.add( transactionItemData );
	}

}
