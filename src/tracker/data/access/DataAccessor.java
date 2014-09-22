package tracker.data.access;

import java.util.List;

import tracker.data.access.gae.TransactionEntity;
import tracker.data.transfer.Transaction;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionReportJDO;

public interface DataAccessor {
	
	Transaction newTransaction();
	
	
	
	
	
	
	
	
	TransactionEntity getTransaction( String transactionId );

	TransactionItemTypeJDO getTransactionItemType( String transactionItemTypeId );
	
	TransactionQuery newTransactionQuery();

	TransactionItemQuery newTransactionItemQuery();

	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	TransactionReportQuery newTransactionReportQuery();

	TransactionEntity persistTransaction( TransactionEntity transactionJDO );
	
	TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO );
	
	List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList );
	
	TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO );

	List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList );

	void close();
	
}
