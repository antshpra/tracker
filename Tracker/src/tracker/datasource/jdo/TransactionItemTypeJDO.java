package tracker.datasource.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

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
	
	
	public String getId() { return KeyFactory.keyToString( this.transactionItemTypeId ); }
	
	public String getParentId() { return this.parentId; }
	
	public String getTitle() { return this.title; }

	public float getInitialAmount() {
		if( this.initialAmount == null || (long) this.initialAmount == 0L )
			return 0F;
		
		return ( (float) (long) this.initialAmount ) / 100;
	}
	
	// TODO: Remove this member once TransactionItemTypeDB is migrated to DataStore
	public void setId( Key key ) { this.transactionItemTypeId = key; }
	
	public void setParentId( String parentId ) { this.parentId = parentId; }
	
	public void setTitle( String title ) { this.title = title; }
	
	public void setInitialAmount( float initialAmount ) {
		this.initialAmount = (Long) (long) ( initialAmount * 100 );
	}

}
