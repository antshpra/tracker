package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.GAEJDODataSource;

import com.google.appengine.api.datastore.KeyFactory;

public class TransactionDataSourceGAEImpl extends GAEJDODataSource implements TransactionDataSource {
	
	@Override
	public TransactionJDO getTransaction( String transactionId ) {
        TransactionJDO transactionJDO = super.getPersistenceManager().getObjectById( TransactionJDO.class, KeyFactory.stringToKey( transactionId ) );
        
        TransactionItemQuery transactionItemQuery = newTransactionItemQuery();
        transactionItemQuery.setTransactionId( transactionId );
        List<TransactionItemJDO> transactionItemJDOList = transactionItemQuery.execute();
        
        for( TransactionItemJDO transactionItemJDO : transactionItemJDOList )
            transactionJDO.addTransactionItemJDO( transactionItemJDO );

        return transactionJDO;
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
	
}
