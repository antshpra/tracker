package tracker.service.transaction.shared;

import java.io.Serializable;

public class TransactionItemTypeData implements Serializable {

	private static final long serialVersionUID = 4200719521234528721L;

	private String transactionItemTypeId;

	private String parentId;

	private String title;
	
	
	public String getId() { return this.transactionItemTypeId; }

	public String getParentId() { return this.parentId; }
	
	public String getTitle() { return this.title; }
	

	public void setId( String id ) { this.transactionItemTypeId = id; }
	
	public void setParentId( String parentId ) { this.parentId = parentId; }

	public void setTitle( String title ) { this.title = title; }
	
}
