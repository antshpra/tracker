package tracker.service.transactionreport.shared.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import tracker.commons.shared.Amount;

public class TransactionReportData implements Serializable {

	private static final long serialVersionUID = 4200719521234528721L;

	private String transactionItemTypeId;

	private String index;
	
	private String title;
	
	private Amount amount;
	
	private List<TransactionReportData> children;
	
	
	public TransactionReportData() {
		this.children = new LinkedList<>();
	}
	
	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }

	public String getIndex() { return this.index; }
	
	public String getTitle() { return this.title; }

	public Amount getAmount() { return this.amount; }
	
	public Amount getTotalAmount() {
		Amount totalAmount = getAmount();
		for( TransactionReportData transactionReportData : getChildren() )
			totalAmount = totalAmount.add( transactionReportData.getTotalAmount() );
		return totalAmount;
	}
	
	public List<TransactionReportData> getChildren() { return this.children; };
	

	public void setTransactionItemTypeId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }
	
	public void setIndex( String index ) { this.index = index; }
	
	public void setTitle( String title ) { this.title = title; }
	
	public void setAmount( Amount amount ) { this.amount = amount; }
	
	public void addChild( TransactionReportData child ) { this.children.add( child ); }
	
}
