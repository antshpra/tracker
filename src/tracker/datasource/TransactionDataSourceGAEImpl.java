package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.datasource.jdo.TransactionReportJDO;
import antshpra.gae.datasource.GAEJDODataSource;

import com.google.appengine.api.datastore.KeyFactory;

public class TransactionDataSourceGAEImpl extends GAEJDODataSource implements TransactionDataSource {
	
	@Override
	public TransactionJDO getTransaction( String transactionId ) {
        return super.getPersistenceManager().getObjectById( TransactionJDO.class, KeyFactory.stringToKey( transactionId ) );
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
	public TransactionJDO persistTransaction( TransactionJDO transactionJDO ) {
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
