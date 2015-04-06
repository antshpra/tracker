package tracker.pagecontent.transactions;

import java.util.ArrayList;
import java.util.Date;
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

	
	private static TransactionData createTransactionData( Transaction tr, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		return createTransactionData( tr, dataAccessor.getTransactionItemList( tr.getId() ), request );
	}
	
	private static TransactionData createTransactionData( Transaction tr, List<TransactionItem> triList, HttpServletRequest request ) {
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


		TransactionData trData = new TransactionData();
		
		trData.setId( tr.getId() );
		trData.setTransactionDate( tr.getTransactionDate() );
		trData.setDescription( tr.getDescription() );
		trData.setCreationDate( tr.getCreationDate() );
		
		List<TransactionItemData> triDataList = new ArrayList<>( triList.size() );
		for( TransactionItem tri : triList ) {
			
			if( tri.getAmount() == 0 )
				continue;
			
			TransactionItemData triData = new TransactionItemData();
			
			triData.setId( tri.getId() );
			triData.setTransactionId( tri.getTransactionId() );
			triData.setTransactionItemTypeId( tri.getTransactionItemTypeId() );
			triData.setTransactionItemType( triTypeIdToTriTypeDataMap.get( tri.getTransactionItemTypeId() ) );
			triData.setTransactionDate( tri.getTransactionDate() );
			triData.setAmount( tri.getAmount() );
			triData.setNote( tri.getNote() );
			triData.setOrder( tri.getOrder() == null ? null : (int) (long) tri.getOrder() );
			triData.setCreationDate( tri.getCreationDate() );
			
			int index = 0;
			for( ; index < triDataList.size(); index++ ) {
				if( triData.getOrder() != null && triDataList.get( index ).getOrder() != null ) {
					if( (int) triData.getOrder() < (int) triDataList.get( index ).getOrder() )
						break;
				} else if( triData.getTransactionDate().before( triDataList.get( index ).getTransactionDate() ) )
					break;
				else if( triData.getCreationDate().before( triDataList.get( index ).getCreationDate() ) )
					break;
				else if( triData.getAmount() > 0 && triDataList.get( index ).getAmount() < 0 )
					break;
			}
			
			triDataList.add( index, triData );
		}

		trData.setTransactionItemList( triDataList );
		
		return trData;
	}
	
	private static List<TransactionData> createTransactionDataList( List<Transaction> trList, HttpServletRequest request ) {
		List<TransactionData> trDataList = new ArrayList<>( trList.size() );
		for( Transaction tr : trList )
			trDataList.add( createTransactionData( tr , request ) );
		return trDataList;
	}

	
	public static DataListCursorTuple<TransactionData> getTransactionList(
			TransactionFilter trFilter, String cursor, Integer resultCount,
			HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<Transaction> trListCursorTuple = dataAccessor.getTransactionList( trFilter, cursor, resultCount );
		
		List<Transaction> trList = trListCursorTuple.getDataList();
		List<TransactionData> trDataList = createTransactionDataList( trList, request );

		return new DataListCursorTuple<TransactionData>( trDataList, trListCursorTuple.getCursor() );
	}

	public static TransactionData saveTransaction( TransactionData trData, HttpServletRequest request ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Transaction transaction;
		if( trData.getId() == null ) { // Create new Transaction
			transaction = dataAccessor.newTransaction();
			transaction.setTransactionDate( trData.getTransactionDate() );
			transaction.setDescription( trData.getDescription() );
			transaction.setCreationDate( new Date() );
			
		} else { // Update existing Transaction
			transaction = dataAccessor.getTransaction( trData.getId() );
			transaction.setTransactionDate( trData.getTransactionDate() );
			transaction.setDescription( trData.getDescription() );
		}
		transaction = dataAccessor.createOrUpdateTransaction( transaction );
		
		
		// Creating/Updating Transaction Item(s)
		List<TransactionItemData> triDataList = trData.getTransactionItemList();
		List<TransactionItem> transactionItemList = new ArrayList<>( triDataList.size() );
		for( TransactionItemData triData : triDataList ) {
			TransactionItem transactionItem;

			if( triData.getId() == null ) { // Create new Transaction Item
				if( triData.getAmount() == 0 )
					continue;
				
				transactionItem = dataAccessor.newTransactionItem();
				transactionItem.setTransactionId( transaction.getId() );
				transactionItem.setTransactionItemTypeId( triData.getTransactionItemTypeId() );
				if( triData.getTransactionDate() == null )
					transactionItem.setTransactionDate( transaction.getTransactionDate() );
				else
					transactionItem.setTransactionDate( triData.getTransactionDate() );
				transactionItem.setAmount( triData.getAmount() );
				transactionItem.setNote( triData.getNote() );
				transactionItem.setOrder( (long) (int) triData.getOrder() );
				transactionItem.setCreationDate( new Date() );
				transactionItem.setLastUpdationDate( new Date() );
			
			} else { // Update existing Transaction Item

				transactionItem = dataAccessor.getTransactionItem( triData.getId() );
				if( triData.getTransactionDate() == null )
					transactionItem.setTransactionDate( transaction.getTransactionDate() );
				else
					transactionItem.setTransactionDate( triData.getTransactionDate() );
				transactionItem.setAmount( triData.getAmount() );
				transactionItem.setNote( triData.getNote() );
				transactionItem.setOrder( (long) (int) triData.getOrder() );
				transactionItem.setLastUpdationDate( new Date() );
			
			}

			transactionItemList.add( transactionItem );
		}
		
		transactionItemList = dataAccessor.createOrUpdateTransactionItemList( transactionItemList );

		
		return createTransactionData( transaction, transactionItemList, request );
	}


	public static List<TransactionItemTypeData> getTransactionItemTypeList( HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		List<TransactionItemType> triTypeList = dataAccessor.getTransactionItemTypeList();
		List<TransactionItemTypeData> triTypeDataList = new ArrayList<>( triTypeList.size() );
		
		for( TransactionItemType triType : triTypeList ) {
			TransactionItemTypeData triTypeData = new TransactionItemTypeData();
			triTypeData.setId( triType.getId() );
			triTypeData.setTitle( triType.getTitle() );
			triTypeData.setInitialAmount( triType.getInitialAmount() );
			triTypeDataList.add( triTypeData );
		}
		
		return triTypeDataList;
	}

}
