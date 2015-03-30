package tracker.pagecontent.transactions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tracker.commons.shared.TransactionFilter;
import tracker.data.access.DataAccessor;
import tracker.data.access.DataAccessorFactory;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.data.transfer.TransactionItemType;
import tracker.data.transfer.shared.TransactionData;
import tracker.data.transfer.shared.TransactionItemData;
import tracker.data.transfer.shared.TransactionItemTypeData;
import tracker.pagecontent.transactionlist.shared.TransactionListContentData;
import tracker.pagecontent.transactions.gae.TransactionsContentEntity;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentHelper;


public class TransactionsContentHelper extends PageContentHelper<
		TransactionsContent,
		TransactionListContentData,
		TransactionsContentProcessor> {

	
	@Override
	public String getModuleName() {
		return "Transaction List";
	}

	@Override
	public Double getModuleVersion() {
		return 1.0;
	}

	
	public static TransactionsContent newTransactionsContent() {
		return new TransactionsContentEntity();
	}

	
	private static List<TransactionData> createTransactionDataList( List<Transaction> trList, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		List<TransactionItemType> triTypeList = dataAccessor.getTransactionItemTypeList();
		Map<String, TransactionItemTypeData> triTypeIdToTriTypeDataMap = new LinkedHashMap<>( triTypeList.size() );
		
		for( TransactionItemType triType : triTypeList ) {
			TransactionItemTypeData triTypeData = new TransactionItemTypeData();
			triTypeData.setId( triType.getId() );
			triTypeData.setTitle( triType.getTitle() );
			triTypeData.setInitialAmount( triType.getInitialAmount() );
			triTypeIdToTriTypeDataMap.put( triType.getId(), triTypeData );
		}

		List<TransactionData> trDataList = new ArrayList<>( trList.size() );
		for( Transaction tr : trList ) {

			TransactionData trData = new TransactionData();
			
			trData.setId( tr.getId() );
			trData.setTransactionDate( tr.getTransactionDate() );
			trData.setDescription( tr.getDescription() );
			trData.setCreationDate( tr.getCreationDate() );
			
			List<TransactionItem> triList = dataAccessor.getTransactionItemList( tr.getId() );
			List<TransactionItemData> triDataList = new ArrayList<>( triList.size() );
			for( TransactionItem tri : triList ) {
			
				TransactionItemData triData = new TransactionItemData();
				
				triData.setId( tri.getId() );
				triData.setTransactionId( tri.getTransactionId() );
				triData.setTransactionItemType( triTypeIdToTriTypeDataMap.get( tri.getTransactionItemTypeId() ) );
				triData.setTransactionDate( tri.getTransactionDate() );
				triData.setAmount( tri.getAmount() );
				triData.setNote( tri.getNote() );
				triData.setOrder( (int) (long) tri.getOrder() );
				triData.setCreationDate( tri.getCreationDate() );
				
				triDataList.add( triData );
			}

			trData.setTransactionItemList( triDataList );
			trDataList.add( trData );
		}
		
		return trDataList;
	}
	
	
	public static DataListCursorTuple<TransactionData> getTransactionList(
			TransactionFilter trFilter, String cursor, Integer resultCount,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<Transaction> trListCursorTuple = dataAccessor.getTransactionList( trFilter, cursor, resultCount );
		
		List<Transaction> trList = trListCursorTuple.getDataList();
		List<TransactionData> trDataList = createTransactionDataList( trList, request );

		return new DataListCursorTuple<TransactionData>( trDataList, trListCursorTuple.getCursor() );
	}

}
