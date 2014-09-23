package tracker.data.access;

import java.util.List;

import tracker.data.access.gae.TransactionEntity;
import tracker.data.access.gae.TransactionItemEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemQueryGAEImpl;
import tracker.datasource.TransactionItemTypeDB;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionItemTypeQueryGAEImpl;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionQueryGAEImpl;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.TransactionReportQueryGAEImpl;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionReportJDO;
import antshpra.gae.datasource.GAEJDODataSource;

import com.google.appengine.api.datastore.KeyFactory;

public class DataAccessorGaeImpl extends GAEJDODataSource implements DataAccessor {
	
	protected <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) super.getPersistenceManager().getObjectById( clazz, id );
		return super.getPersistenceManager().detachCopy( entity );
	}

	protected <T> T createOrUpdateEntity( T entity ) {
		entity = super.getPersistenceManager().makePersistent( entity );
		return super.getPersistenceManager().detachCopy( entity );
	}
	

	
	
	@Override
	public Transaction newTransaction() {
		return new TransactionEntity();
	}
	
	@Override
	public TransactionEntity getTransaction( String transactionId ) {
        return getEntity(
        		TransactionEntity.class,
        		KeyFactory.stringToKey( transactionId ) );
	}


	@Override
	public TransactionItem newTransactionItem() {
		return new TransactionItemEntity();
	}
	
	@Override
	public TransactionItem getTransactionItem( String transactionItemId ) {
		System.out.println(KeyFactory.stringToKey( transactionItemId ));
		return getEntity(
        		TransactionItemEntity.class,
        		KeyFactory.stringToKey( transactionItemId ) );
	}

	
	
	
	
	@Override
	public TransactionItemTypeJDO getTransactionItemType( String transactionItemTypeId ) {
		// TODO: Update this implementation once TransactionItemTypeDB is migrated to DataStore
		
		long decryptedTransactionItemTypeId = KeyFactory.stringToKey( transactionItemTypeId ).getId();
		for( TransactionItemTypeDB transactionItemTypeDB : TransactionItemTypeDB.values() )
			if( transactionItemTypeDB.getId() == decryptedTransactionItemTypeId )
				return transactionItemTypeDB.toTransactionItemTypeJDO();
		
		return null;
	}

	@Override
	public TransactionQuery newTransactionQuery() {
		return new TransactionQueryGAEImpl( this );
	}

	@Override
	public TransactionItemQuery newTransactionItemQuery() {
		return new TransactionItemQueryGAEImpl( this );
	}

	@Override
	public TransactionItemTypeQuery newTransactionItemTypeQuery() {
		return new TransactionItemTypeQueryGAEImpl( this );
	}
	
	@Override
	public TransactionReportQuery newTransactionReportQuery() {
		return new TransactionReportQueryGAEImpl( this );
	}
	
	@Override
	public Transaction persistTransaction( Transaction transaction ) {
		return super.getPersistenceManager().makePersistent( transaction );
	}
	
	@Override
	public TransactionItem persistTransactionItem( TransactionItem transactionItemJDO ) {
		return super.getPersistenceManager().makePersistent( transactionItemJDO );
	}
	
	@Override
	public List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemList ) {
		return (List<TransactionItem>) super.getPersistenceManager().makePersistentAll( transactionItemList );
	}

	@Override
	public TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO ) {
		return super.getPersistenceManager().makePersistent( transactionReportJDO );
	}
	
	@Override
	public List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList ) {
		return (List<TransactionReportJDO>) super.getPersistenceManager().makePersistentAll( transactionReportJDOList );
	}

}
