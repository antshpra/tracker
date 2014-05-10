package tracker.service.transaction.shared;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TransactionItemTypeData implements Serializable {

	private static final long serialVersionUID = 4200719521234528721L;

	private String transactionItemTypeId;

	private String title;
	
	private TransactionItemTypeData parent;
	
	private List<TransactionItemTypeData> children;
	
	
	public TransactionItemTypeData() {
		this.children = new LinkedList<>();
	}
	
	
	public String getId() { return this.transactionItemTypeId; }

	public String getTitle() { return this.title; }
	
	public String getQualifiedTitle() {
		String title = this.title;
		TransactionItemTypeData parent = this.parent;
	
		while( parent != null ) {
			title = parent.getTitle() + "::" + title;
			parent = parent.getParent();
		}
		
		return title;
	}

	public TransactionItemTypeData getParent() { return this.parent; };
	
	public List<TransactionItemTypeData> getChildren() { return this.children; };
	

	public void setId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }
	
	public void setTitle( String title ) { this.title = title; }
	
	public void setParent( TransactionItemTypeData parent ) { this.parent = parent; }
	
	public void addChild( TransactionItemTypeData child ) { this.children.add( child ); }
	
}
