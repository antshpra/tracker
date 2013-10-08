package tracker.datasource;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.GAEDataSource;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public class TransactionDataSource extends GAEDataSource {

	public TransactionJDO getTransaction( long transactionId ) throws EntityNotFoundException {
		return super.pm.getObjectById( TransactionJDO.class, transactionId );
	}
	
	public TransactionJDO getTransaction( String transactionName ) throws EntityNotFoundException {
		return super.pm.getObjectById( TransactionJDO.class, transactionName );
	}
	
	public TransactionJDO getTransaction( Key transactionKey ) throws EntityNotFoundException {
		return super.pm.getObjectById( TransactionJDO.class, transactionKey );
	}

	
	public TransactionJDO persistTransaction( TransactionJDO transactionJDO ) {
		return super.pm.makePersistent( transactionJDO );
	}
	

	public TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO ) {
		return super.pm.makePersistent( transactionItemJDO );
	}
	
}
