package tracker.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import tracker.commons.shared.TransactionReportType;
import tracker.data.transfer.TransactionReport;

import com.claymus.commons.client.Amount;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable( table = "TRANSACTION_REPORT" )
public class TransactionReportEntity implements TransactionReport {
	
	@PrimaryKey
	@Persistent( column = "TRANSACTION_REPORT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionReportId;

	@Persistent( column = "INDEX" )
	private String index;
	
	@Persistent( column = "TRANSACTION_ITEM_TYPE_ID" )
	private String transactionItemTypeId;

	@Persistent( column = "TYPE" )
	private TransactionReportType type;
	
	@Persistent( column = "AMOUNT" )
	private Long amount;

	@Persistent( column = "LAST_UPDATION_DATE" )
	private Date lastUpdationDate;
		
	
	public TransactionReportEntity() {}
	
	public TransactionReportEntity(
			String transactionReportIndex,
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			Amount amount ) {
		
		this.index = transactionReportIndex;
		this.transactionItemTypeId = transactionItemTypeId;
		this.type = transactionReportType;
		this.amount = amount.getValue();
		this.lastUpdationDate = new Date( 0 );
	}
	
	
	public String getIndex() {
		return this.index;
	}
	
	public void setIndex( String index ) {
		this.index = index;
	}
	
	public String getTransactionItemTypeId() {
		return this.transactionItemTypeId;
	}

	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public TransactionReportType getType() {
		return this.type;
	}
	
	public void setType( TransactionReportType type ) {
		this.type = type;
	}
	
	public Amount getAmount() {
		return new Amount( this.amount );
	}
	
	public void setAmount( Amount amount ) {
		this.amount = amount.getValue();
	}
	
	public Date getLastUpdationDate() {
		return this.lastUpdationDate == null ? null : new Date( this.lastUpdationDate.getTime() );
	}

	public void setLastUpdationDate( Date lastUpdationDate ) {
		this.lastUpdationDate = lastUpdationDate; 
	}

}
