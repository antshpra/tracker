package tracker.data.access;

import java.util.List;

import tracker.commons.shared.TransactionFilter;
import tracker.data.access.gae.TransactionItemTypeEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.data.transfer.TransactionItemType;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.jdo.TransactionReportJDO;

import com.claymus.data.access.DataListCursorTuple;

public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Transaction newTransaction();
	
	Transaction getTransaction( String transactionId );

	DataListCursorTuple<Transaction> getTransactionList(
			TransactionFilter trFilter, String cursorStr, Integer resultCount );

	
	TransactionItem newTransactionItem();
	
	TransactionItem getTransactionItem( String transactionItemId );
	
	List<TransactionItem> getTransactionItemList( String encodedTrId );
	
	
	List<TransactionItemType> getTransactionItemTypeList();

	
	
	TransactionItemTypeEntity getTransactionItemType( String transactionItemTypeId );
	
	TransactionQuery newTransactionQuery();

	TransactionItemQuery newTransactionItemQuery();

	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	TransactionReportQuery newTransactionReportQuery();

	Transaction persistTransaction( Transaction transactionJDO );
	
	TransactionItem persistTransactionItem( TransactionItem transactionItem );
	
	List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemJDOList );
	
	TransactionReportJDO persistTransactionReport( TransactionReportJDO transactionReportJDO );

	List<TransactionReportJDO> persistTransactionReportList( List<TransactionReportJDO> transactionReportJDOList );
	
}
