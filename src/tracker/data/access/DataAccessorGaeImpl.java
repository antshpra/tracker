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

import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {
	
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
		return createOrUpdateEntity( transaction );
	}
	
	@Override
	public TransactionItem persistTransactionItem( TransactionItem transactionItemJDO ) {
		return createOrUpdateEntity( transactionItemJDO );
	}
	
	@Override
	public List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemList ) {
		return (List<TransactionItem>) createOrUpdateEntityList( transactionItemList );
	}

	@Override
	public TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO ) {
		return createOrUpdateEntity( transactionReportJDO );
	}
	
	@Override
	public List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList ) {
		return (List<TransactionReportJDO>) createOrUpdateEntityList( transactionReportJDOList );
	}

}
