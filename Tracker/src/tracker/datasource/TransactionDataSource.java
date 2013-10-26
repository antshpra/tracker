package tracker.datasource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.JDODataSource;

import com.google.appengine.api.datastore.KeyFactory;

public class TransactionDataSource extends JDODataSource {
	
	private static Logger logger = Logger.getLogger( TransactionDataSource.class.getName() );
	
	
	@SuppressWarnings("unchecked")
	public List<TransactionItemJDO> getTransactionItemList( String transactionId ) {
		Query query = pm.newQuery( TransactionItemJDO.class );
		query.setFilter( "transactionId == keyString" );
		query.declareParameters( String.class.getName() + " keyString" );
		List<TransactionItemJDO> transactionItemJDOList = (List<TransactionItemJDO>) query.execute( transactionId );
		query.closeAll();
		logger.log( Level.INFO, "Found " + transactionItemJDOList.size() + " transaction items for " + KeyFactory.stringToKey( transactionId ) );
		return transactionItemJDOList;
	}
	
	
	public TransactionJDO persistTransaction( TransactionJDO transactionJDO ) {
		return super.pm.makePersistent( transactionJDO );
	}
	
	public TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO ) {
		return super.pm.makePersistent( transactionItemJDO );
	}
	
	public List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList ) {
		return (List<TransactionItemJDO>) super.pm.makePersistentAll( transactionItemJDOList );
	}
	
}
