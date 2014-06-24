package tracker.datasource.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import tracker.commons.shared.Amount;
import tracker.commons.shared.TransactionReportType;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable( table = "TRANSACTION_REPORT" )
public class TransactionReportJDO {
	
	@Persistent( column = "TRANSACTION_REPORT_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
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
		
	
	public TransactionReportJDO() {}
	
	public TransactionReportJDO(
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
	
	
	public String getIndex() { return this.index; }
	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }

	public TransactionReportType getType() { return this.type; }
	
	public Amount getAmount() { return new Amount( this.amount ); }
	
	public Date getLastUpdationDate() { return this.lastUpdationDate == null ? null : new Date( this.lastUpdationDate.getTime() ); }

	
	public void setIndex( String index ) { this.index = index; }

	public void setTransactionItemTypeId( String transactionItemTypeId ) { this.transactionItemTypeId = transactionItemTypeId; }

	public void setType( TransactionReportType type ) { this.type = type; }
	
	public void setAmount( Amount amount ) { this.amount = amount.getValue(); }

	public void setLastUpdationDate( Date lastUpdationDate ) { this.lastUpdationDate = lastUpdationDate; }

}
