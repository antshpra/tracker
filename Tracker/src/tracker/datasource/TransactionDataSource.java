package tracker.datasource;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.JDODataSource;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class TransactionDataSource extends JDODataSource {
	
	private static Logger logger = Logger.getLogger( TransactionDataSource.class.getName() );
	
	
	public TransactionJDO getTransaction( String transactionId, boolean loadTransactionItems ) {
		TransactionJDO transactionJDO = super.getPersistenceManager().getObjectById( TransactionJDO.class, KeyFactory.stringToKey( transactionId ) );
		if( loadTransactionItems )
			for( TransactionItemJDO transactionItemJDO : getTransactionItemList( transactionId ) )
				transactionJDO.addTransactionItemJDO( transactionItemJDO );
		return transactionJDO;
	}
	
	protected TransactionJDO getTransaction( Key transactionKey, boolean loadTransactionItems ) {
		return getTransaction( KeyFactory.keyToString( transactionKey ), loadTransactionItems );
	}

	protected List<TransactionJDO> getTransactionList( List<Key> keyList, boolean loadTransactionItems ) {
		List<TransactionJDO> transactionJDOList = new LinkedList<TransactionJDO>();
		for( Key key : keyList )
			transactionJDOList.add( getTransaction( key, loadTransactionItems ) );
		return transactionJDOList;
	}
	
	public List<TransactionItemJDO> getTransactionItemList( String transactionId ) {
		Query query = getPersistenceManager().newQuery( TransactionItemJDO.class );
		query.setFilter( "transactionId == keyString" );
		query.declareParameters( String.class.getName() + " keyString" );
		@SuppressWarnings("unchecked")
		List<TransactionItemJDO> transactionItemJDOList = (List<TransactionItemJDO>) query.execute( transactionId );
		query.closeAll();
		logger.log( Level.INFO, "Found " + transactionItemJDOList.size() + " transaction items for " + KeyFactory.stringToKey( transactionId ) );
		return transactionItemJDOList;
	}
	
	public TransactionQuery newTransactionQuery() {
		return new TransactionQuery( super.newQuery( TransactionJDO.class), this );
	}

	public TransactionJDO persistTransaction( TransactionJDO transactionJDO ) {
		return super.getPersistenceManager().makePersistent( transactionJDO );
	}
	
	public TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO ) {
		return super.getPersistenceManager().makePersistent( transactionItemJDO );
	}
	
	public List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList ) {
		return (List<TransactionItemJDO>) super.getPersistenceManager().makePersistentAll( transactionItemJDOList );
	}
	
}
