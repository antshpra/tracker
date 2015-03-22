package tracker.data.access;

import java.util.List;

import tracker.commons.shared.TransactionFilter;
import tracker.data.access.gae.TransactionItemTypeEntity;
import tracker.data.access.gae.TransactionReportEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.data.transfer.TransactionItemType;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionReportQuery;

import com.claymus.data.access.DataListCursorTuple;

public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Transaction newTransaction();
	
	Transaction getTransaction( Long id );

	@Deprecated
	Transaction getTransaction( String id );

	DataListCursorTuple<Transaction> getTransactionList(
			TransactionFilter trFilter, String cursorStr, Integer resultCount );

	
	TransactionItem newTransactionItem();
	
	TransactionItem getTransactionItem( Long id );

	@Deprecated
	TransactionItem getTransactionItem( String id );
	
	List<TransactionItem> getTransactionItemList( Long trId );

	@Deprecated
	List<TransactionItem> getTransactionItemList( String encodedTrId );
	
	
	List<TransactionItemType> getTransactionItemTypeList();

	
	
	
	
	@Deprecated
	TransactionItemTypeEntity getTransactionItemType( String transactionItemTypeId );
	
	@Deprecated
	TransactionQuery newTransactionQuery();

	@Deprecated
	TransactionItemQuery newTransactionItemQuery();

	@Deprecated
	TransactionItemTypeQuery newTransactionItemTypeQuery();
	
	@Deprecated
	TransactionReportQuery newTransactionReportQuery();

	@Deprecated
	Transaction persistTransaction( Transaction transactionJDO );
	
	@Deprecated
	TransactionItem persistTransactionItem( TransactionItem transactionItem );
	
	@Deprecated
	List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemJDOList );
	
	@Deprecated
	TransactionReportEntity persistTransactionReport( TransactionReportEntity transactionReportJDO );

	@Deprecated
	List<TransactionReportEntity> persistTransactionReportList( List<TransactionReportEntity> transactionReportJDOList );
	
}
