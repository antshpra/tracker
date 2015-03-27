package tracker.data.transfer.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class TransactionData implements Serializable {

	private Long id;

	private Long trDate;
	
	private String description;
	
	private Long creationDate;
	
	private List<TransactionItemData> triList;


	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public Date getTransactionDate() {
		return new Date( trDate );
	}
	
	public void setTransactionDate( Date trDate ) {
		this.trDate = trDate.getTime(); 
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return new Date( creationDate ); 
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate.getTime();
	}
	
	public List<TransactionItemData> getTransactionItemList() {
		return triList == null ? new ArrayList<TransactionItemData>(0) : triList;
	}

	public void setTransactionItemList( List<TransactionItemData> triList ) {
		this.triList = triList;
	}

}
