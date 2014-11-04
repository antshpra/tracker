package tracker.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import tracker.commons.shared.TransactionReportType;
import tracker.data.transfer.TransactionItemType;

import com.claymus.commons.client.Amount;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable( table = "TRANSACTION_ITEM_TYPE" )
public class TransactionItemTypeEntity implements TransactionItemType {
	
	@PrimaryKey
	@Persistent( column = "TRANSACTION_ITEM_TYPE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key transactionItemTypeId;

	@Persistent( column = "PARENT_ID" )
	private String parentId;
	
	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "INITIAL_AMOUNT" )
	private Long initialAmount;
	
	@Persistent( column = "REPORT_TYPE" )
	private TransactionReportType transactionReportType;
	
	
	public String getId() {
		return KeyFactory.keyToString( this.transactionItemTypeId );
	}
	
	// TODO: Remove this member once TransactionItemTypeDB is migrated to DataStore
	public void setId( Key key ) {
		this.transactionItemTypeId = key; 
	}
	
	public String getParentId() {
		return this.parentId;
	}
	
	public void setParentId( String parentId ) {
		this.parentId = parentId;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}
	
	public Amount getInitialAmount() {
		return new Amount( this.initialAmount );
	}

	public void setInitialAmount( Amount initialAmount ) {
		this.initialAmount = initialAmount.getValue();
	}
	
	public void setTransactionReportType( TransactionReportType transactionReportType ) {
		this.transactionReportType = transactionReportType;
	}
	
	public TransactionReportType getTransactionReportType() {
		return this.transactionReportType;
	}
	
}
