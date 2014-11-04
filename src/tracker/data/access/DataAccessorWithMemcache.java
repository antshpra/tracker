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
import com.claymus.data.access.Memcache;

@SuppressWarnings("serial")
public class DataAccessorWithMemcache
		extends com.claymus.data.access.DataAccessorWithMemcache
		implements DataAccessor {
	
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
	public Transaction getTransaction(String transactionId) {
		// TODO Auto-generated method stub
		return dataAccessor.getTransaction( transactionId );
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
	public TransactionItem getTransactionItem(String transactionItemId) {
		// TODO Auto-generated method stub
		return dataAccessor.getTransactionItem(transactionItemId);
	}

	@Override
	public List<TransactionItem> getTransactionItemList( String encodedTrId ) {
		return dataAccessor.getTransactionItemList( encodedTrId );
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
	public TransactionReportJDO persistTransactionReport(
			TransactionReportJDO transactionReportJDO) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransactionReport(transactionReportJDO);
	}


	@Override
	public List<TransactionReportJDO> persistTransactionReportList(
			List<TransactionReportJDO> transactionReportJDOList) {
		// TODO Auto-generated method stub
		return dataAccessor.persistTransactionReportList(transactionReportJDOList);
	}

}
