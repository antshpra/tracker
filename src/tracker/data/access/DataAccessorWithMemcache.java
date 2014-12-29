package tracker.data.access;

import java.util.ArrayList;
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
import com.claymus.data.access.Memcache;

@SuppressWarnings("serial")
public class DataAccessorWithMemcache
		extends com.claymus.data.access.DataAccessorWithMemcache
		implements DataAccessor {
	
	private static final String PREFIX_TRANSACTION = "Transaction-";
	private static final String PREFIX_TRANSACTION_ITEM = "TransactionItem-";
	private static final String PREFIX_TRANSACTION_ITEM_LIST = "TransactionItemList-";

	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		super( dataAccessor, memcache );
		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}


	@Override
	public Transaction newTransaction() {
		return dataAccessor.newTransaction();
	}

	@Override
	public Transaction getTransaction( Long id ) {
		Transaction tr = memcache.get( PREFIX_TRANSACTION + id );
		if( tr == null ) {
			tr = dataAccessor.getTransaction( id );
			if( tr != null )
				memcache.put( PREFIX_TRANSACTION + id, tr );
		}
		return tr;
	}

	@Override
	public Transaction getTransaction( String id ) {
		Transaction tr = memcache.get( PREFIX_TRANSACTION + id );
		if( tr == null ) {
			tr = dataAccessor.getTransaction( id );
			if( tr != null )
				memcache.put( PREFIX_TRANSACTION + id, tr );
		}
		return tr;
	}

	@Override
	public DataListCursorTuple<Transaction> getTransactionList(
			TransactionFilter trFilter, String cursorStr, Integer resultCount ) {
		
		return dataAccessor.getTransactionList( trFilter, cursorStr, resultCount );
	}
	
	
	@Override
	public TransactionItem newTransactionItem() {
		return dataAccessor.newTransactionItem();
	}

	@Override
	public TransactionItem getTransactionItem( Long id ) {
		TransactionItem tri = memcache.get( PREFIX_TRANSACTION_ITEM + id );
		if( tri == null ) {
			tri = dataAccessor.getTransactionItem( id );
			if( tri != null )
				memcache.put( PREFIX_TRANSACTION_ITEM + id, tri );
		}
		return tri;
	}

	@Override
	public TransactionItem getTransactionItem( String id ) {
		TransactionItem tri = memcache.get( PREFIX_TRANSACTION_ITEM + id );
		if( tri == null ) {
			tri = dataAccessor.getTransactionItem( id );
			if( tri != null )
				memcache.put( PREFIX_TRANSACTION_ITEM + id, tri );
		}
		return tri;
	}

	@Override
	public List<TransactionItem> getTransactionItemList( Long trId ) {
		List<TransactionItem> triList = memcache.get( PREFIX_TRANSACTION_ITEM_LIST + trId );
		if( triList == null ) {
			triList = dataAccessor.getTransactionItemList( trId );
			if( triList != null )
				memcache.put( PREFIX_TRANSACTION_ITEM_LIST + trId, new ArrayList<>( triList ) );
		}
		return triList;
	}

	@Override
	public List<TransactionItem> getTransactionItemList( String encodedTrId ) {
		List<TransactionItem> triList = memcache.get( PREFIX_TRANSACTION_ITEM_LIST + encodedTrId );
		if( triList == null ) {
			triList = dataAccessor.getTransactionItemList( encodedTrId );
			if( triList != null )
				memcache.put( PREFIX_TRANSACTION_ITEM_LIST + encodedTrId, new ArrayList<>( triList ) );
		}
		return triList;
	}

	
	@Override
	public List<TransactionItemType> getTransactionItemTypeList() {
		return dataAccessor.getTransactionItemTypeList();
	}

	
	
	@Override
	public TransactionItemTypeEntity getTransactionItemType(
			String transactionItemTypeId) {
		// TODO Auto-generated method stub
		return dataAccessor.getTransactionItemType(transactionItemTypeId);
	}


	@Override
	public TransactionQuery newTransactionQuery() {
		return dataAccessor.newTransactionQuery();
	}


	@Override
	public TransactionItemQuery newTransactionItemQuery() {
		return dataAccessor.newTransactionItemQuery();
	}


	@Override
	public TransactionItemTypeQuery newTransactionItemTypeQuery() {
		return dataAccessor.newTransactionItemTypeQuery();
	}


	@Override
	public TransactionReportQuery newTransactionReportQuery() {
		return dataAccessor.newTransactionReportQuery();
	}


	@Override
	public Transaction persistTransaction(Transaction transactionJDO) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransaction(transactionJDO);
	}


	@Override
	public TransactionItem persistTransactionItem(
			TransactionItem transactionItem) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<TransactionItem> persistTransactionItemList(
			List<TransactionItem> transactionItemJDOList) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransactionItemList(transactionItemJDOList);
	}


	@Override
	public TransactionReportEntity persistTransactionReport(
			TransactionReportEntity transactionReportJDO) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransactionReport(transactionReportJDO);
	}


	@Override
	public List<TransactionReportEntity> persistTransactionReportList(
			List<TransactionReportEntity> transactionReportJDOList) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransactionReportList(transactionReportJDOList);
	}

}
