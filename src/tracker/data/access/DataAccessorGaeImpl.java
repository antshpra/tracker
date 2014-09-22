package tracker.data.access;

import java.util.List;

import tracker.data.access.gae.TransactionEntity;
import tracker.data.transfer.Transaction;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemQueryGAEImpl;
import tracker.datasource.TransactionItemTypeDB;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionItemTypeQueryGAEImpl;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionQueryGAEImpl;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.TransactionReportQueryGAEImpl;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionReportJDO;
import antshpra.gae.datasource.GAEJDODataSource;

import com.google.appengine.api.datastore.KeyFactory;

public class DataAccessorGaeImpl extends GAEJDODataSource implements DataAccessor {
	
	@Override
	public Transaction newTransaction() {
		return new TransactionEntity();
	}
	
	
	
	
	
	
	
	@Override
	public TransactionEntity getTransaction( String transactionId ) {
        return super.getPersistenceManager().getObjectById( TransactionEntity.class, KeyFactory.stringToKey( transactionId ) );
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
	public TransactionEntity persistTransaction( TransactionEntity transactionJDO ) {
		return super.getPersistenceManager().makePersistent( transactionJDO );
	}
	
	@Override
	public TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO ) {
		return super.getPersistenceManager().makePersistent( transactionItemJDO );
	}
	
	@Override
	public List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList ) {
		return (List<TransactionItemJDO>) super.getPersistenceManager().makePersistentAll( transactionItemJDOList );
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
