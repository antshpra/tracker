package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;

public interface TransactionDataSource {
	
	TransactionJDO getTransaction( String transactionId );
	    
	TransactionQuery newTransactionQuery();

	TransactionItemQuery newTransactionItemQuery();

	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	TransactionJDO persistTransaction( TransactionJDO transactionJDO );
	
	TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO );
	
	List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList );
	
	void close();
	
}
