package tracker.service.transactionreport.shared;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TransactionReportData implements Serializable {

	private static final long serialVersionUID = 4200719521234528721L;

	private String transactionItemTypeId;

	private String title;
	
	private long amount[];
	
	private List<TransactionReportData> children;
	
	
	public TransactionReportData() {
		this.children = new LinkedList<>();
	}
	
	
	public String getId() { return this.transactionItemTypeId; }

	public String getTitle() { return this.title; }

	public double getAmount( int index ) { return ( (double) this.amount[index] ) / 100; }
	
	public double getTotalAmount( int index ) {
		double totalAmount = getAmount( index );
		for( TransactionReportData transactionReportData : getChildren() )
			totalAmount = totalAmount + transactionReportData.getTotalAmount( index );
		return totalAmount;
	}
	
	public List<TransactionReportData> getChildren() { return this.children; };
	

	public void setId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }
	
	public void setTitle( String title ) { this.title = title; }
	
	public void setAmount( double amount ) { Math.round( amount * 100 ); }
	
	public void addChild( TransactionReportData child ) { this.children.add( child ); }
	
}
