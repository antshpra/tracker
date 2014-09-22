package tracker.datasource.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import tracker.commons.shared.TransactionReportType;

import com.claymus.commons.client.Amount;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable( table = "TRANSACTION_ITEM_TYPE" )
public class TransactionItemTypeJDO {
	
	@Persistent( column = "TRANSACTION_ITEM_TYPE_ID", primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionItemTypeId;

	@Persistent( column = "PARENT_ID" )
	private String parentId;
	
	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "INITIAL_AMOUNT" )
	private Long initialAmount;
	
	@Persistent( column = "REPORT_TYPE" )
	private TransactionReportType transactionReportType;
	
	
	public String getId() { return KeyFactory.keyToString( this.transactionItemTypeId ); }
	
	public String getParentId() { return this.parentId; }
	
	public String getTitle() { return this.title; }

	public Amount getInitialAmount() { return new Amount( this.initialAmount ); }

	public TransactionReportType getTransactionReportType() { return this.transactionReportType; }
	
	
	// TODO: Remove this member once TransactionItemTypeDB is migrated to DataStore
	public void setId( Key key ) { this.transactionItemTypeId = key; }
	
	public void setParentId( String parentId ) { this.parentId = parentId; }
	
	public void setTitle( String title ) { this.title = title; }
	
	public void setInitialAmount( Amount initialAmount ) { this.initialAmount = initialAmount.getValue(); }
	
	public void setTransactionReportType( TransactionReportType transactionReportType ) { this.transactionReportType = transactionReportType; }

}
