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
	
	
	public TransactionJDO getTransaction( String transactionId ) {
		return (TransactionJDO) super.getPersistenceManager().getObjectById( TransactionJDO.class, KeyFactory.stringToKey( transactionId ) );
	}
	
	protected TransactionJDO getTransaction( Key transactionKey ) {
		return (TransactionJDO) super.getPersistenceManager().getObjectById( TransactionJDO.class, transactionKey );
	}

	protected List<TransactionJDO> getTransactionList( List<Key> keyList, boolean loadTransactionItems ) {
		List<TransactionJDO> transactionJDOList = new LinkedList<TransactionJDO>();
		for( Key key : keyList )
			transactionJDOList.add( getTransaction( key ) );
		return transactionJDOList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionItemJDO> getTransactionItemList( String transactionId ) {
		Query query = getPersistenceManager().newQuery( TransactionItemJDO.class );
		query.setFilter( "transactionId == keyString" );
		query.declareParameters( String.class.getName() + " keyString" );
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
