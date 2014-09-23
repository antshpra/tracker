package tracker.data.access;

import java.util.List;

import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionReportJDO;

public interface DataAccessor {
	
	Transaction newTransaction();
	
	Transaction getTransaction( String transactionId );


	TransactionItem newTransactionItem();
	
	TransactionItem getTransactionItem( String transactionItemId );
	
	
	
	
	
	
	TransactionItemTypeJDO getTransactionItemType( String transactionItemTypeId );
	
	TransactionQuery newTransactionQuery();

	TransactionItemQuery newTransactionItemQuery();

	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	TransactionReportQuery newTransactionReportQuery();

	Transaction persistTransaction( Transaction transactionJDO );
	
	TransactionItem persistTransactionItem( TransactionItem transactionItem );
	
	List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemJDOList );
	
	TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO );

	List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList );

	void close();
	
}
