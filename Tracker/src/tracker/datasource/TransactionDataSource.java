package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.datasource.jdo.TransactionReportJDO;

public interface TransactionDataSource {
	
	TransactionJDO getTransaction( String transactionId );

	TransactionItemTypeJDO getTransactionItemType( String transactionItemTypeId );
	
	TransactionQuery newTransactionQuery();

	TransactionItemQuery newTransactionItemQuery();

	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	TransactionReportQuery newTransactionReportQuery();

	TransactionJDO persistTransaction( TransactionJDO transactionJDO );
	
	TransactionItemJDO persistTransactionItem( TransactionItemJDO transactionItemJDO );
	
	List<TransactionItemJDO> persistTransactionItemList( List<TransactionItemJDO> transactionItemJDOList );
	
	TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO );

	List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList );

	void close();
	
}
