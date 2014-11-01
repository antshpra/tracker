package tracker.service.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import tracker.commons.server.JDOToDataConverter;
import tracker.data.access.DataAccessor;
import tracker.data.access.DataAccessorFactory;
import tracker.data.access.gae.TransactionEntity;
import tracker.data.access.gae.TransactionItemEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.service.client.TrackerService;
import tracker.service.shared.CreateTransactionResponse;
import tracker.service.shared.GetTransactionItemListRequest;
import tracker.service.shared.GetTransactionItemListResponse;
import tracker.service.shared.GetTransactionItemTypeListRequest;
import tracker.service.shared.GetTransactionItemTypeListResponse;
import tracker.service.shared.GetTransactionListRequest;
import tracker.service.shared.GetTransactionListResponse;
import tracker.service.shared.GetTransactionRequest;
import tracker.service.shared.GetTransactionResponse;
import tracker.service.shared.SaveTransactionRequest;
import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemData;
import tracker.service.shared.data.TransactionItemTypeData;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TrackerServiceImpl extends RemoteServiceServlet implements TrackerService {

	private static final Logger logger =
			Logger.getLogger( TrackerServiceImpl.class.getName() );
	
	
	@Override
	public CreateTransactionResponse saveTransaction( SaveTransactionRequest request )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		// Creating/Updating Transaction
		TransactionData trData = request.getTransactionData();
		Transaction transaction;
		if( trData.getId() == null ) { // Create new Transaction
			transaction = dataAccessor.newTransaction();
			transaction.setTransactionDate( trData.getTransactionDate() );
			transaction.setDescription( trData.getDescription() );
			transaction.setCreationDate( new Date() );
			transaction.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id
			
		} else { // Update existing Transaction
			transaction = dataAccessor.getTransaction( trData.getId() );
			transaction.setDescription( trData.getDescription() );
		}
		transaction = dataAccessor.persistTransaction( transaction );
		
		
		// Creating/Updating Transaction Item(s)
		List<TransactionItem> transactionItemList =
				new ArrayList<>( trData.getTransactionItemDataList().size() );
		for( TransactionItemData triData : trData.getTransactionItemDataList() ) {
			TransactionItem transactionItem;
			if( triData.getId() == null ) { // Create new Transaction Item
				transactionItem = dataAccessor.newTransactionItem();
				transactionItem.setTransactionId( transaction.getId() );
				transactionItem.setTransactionItemTypeId( triData.getTransactionItemTypeId() );
				if( triData.getTransactionDate() == null )
					transactionItem.setTransactionDate( transaction.getTransactionDate() );
				else
					transactionItem.setTransactionDate( triData.getTransactionDate() );
				transactionItem.setAmount( triData.getAmount() );
				transactionItem.setNote( triData.getNote() );
				transactionItem.setOrder( triData.getOrder() );
				transactionItem.setCreationDate( new Date() );
				transactionItem.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id
				transactionItem.setLastUpdationDate( new Date() );
			
			} else { // Update existing Transaction Item
				transactionItem = dataAccessor.getTransactionItem( triData.getId() );
				transactionItem.setTransactionId( null );
				transactionItem.setAmount( triData.getAmount() );
				transactionItem.setNote( triData.getNote() );
				transactionItem.setOrder( triData.getOrder() );
				transactionItem.setLastUpdationDate( new Date() );
			}

			transactionItemList.add( transactionItem );
		}
		dataAccessor.persistTransactionItemList( transactionItemList );

		
		// Creating response
		CreateTransactionResponse response = new CreateTransactionResponse();
		response.setTransactionId( transaction.getId() );
		
		return response;
	}



	
	
	
	
	
	
	
	
	
	
	private static final DataAccessorFactory transactionDataSourceFactory = new DataAccessorFactory();
	

	@Override
	public GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) throws UnexpectedServerException {
		
		Date transactionDateStart = request.getTransactionDateStart();
		Date transactionDateEnd = request.getTransactionDateEnd();

		Date creationDateStart = request.getCreationDateStart();
		Date creationDateEnd = request.getCreationDateEnd();

		// Creating GetTransactionResponse
		List<TransactionData> transactionDataList = new LinkedList<TransactionData>(); 
		GetTransactionListResponse response = new GetTransactionListResponse( transactionDataList, null );
		
		// Checking for valid transactionDate range 
		if( transactionDateStart != null
				&& transactionDateEnd != null
				&& transactionDateStart.equals( transactionDateEnd )
				&& ( !request.isTransactionDateStartInclusive() || !request.isTransactionDateEndInclusive() ) ) {
			
			logger.log( Level.INFO, "Not enough time range for transactionDate. Returning empty transactionDataList." );
			return response;
		}
		
		// Checking for valid creationDate range 
		if( creationDateStart != null
				&& creationDateEnd != null
				&& creationDateStart.equals( creationDateEnd )
				&& ( !request.isCreationDateStartInclusive() || !request.isCreationDateEndInclusive() ) ) {
		
			logger.log( Level.INFO, "Not enough time range for creationDate. Returning empty transactionDataList." );
			return response;
		}		
		
		DataAccessor transactionDataSource = transactionDataSourceFactory.getDataAccessor();

		// Fetching TransactionJDO list
		TransactionQuery transactionQuery = transactionDataSource.newTransactionQuery();

		if( transactionDateStart != null || transactionDateEnd != null )
			transactionQuery.setTransactionDate(
				transactionDateStart, request.isTransactionDateStartInclusive(),
				transactionDateEnd, request.isTransactionDateEndInclusive() );
		
		if( creationDateStart != null || creationDateEnd != null )
			transactionQuery.setCreationDate(
				creationDateStart, request.isCreationDateStartInclusive(),
				creationDateEnd, request.isCreationDateEndInclusive() );

		if( request.getTransactionDateChronologicalOrder() != null )
			transactionQuery.orderByTransactionDate( request.getTransactionDateChronologicalOrder() );
			
		if( request.getCreationDateChronologicalOrder() != null )
			transactionQuery.orderByCreationDate( request.getCreationDateChronologicalOrder() );
		
		List<TransactionEntity> transactionList = transactionQuery.execute( 0, request.getPageSize() );
		logger.log( Level.INFO, transactionList.size() + " transactions found.");

		for( TransactionEntity transaction : transactionList ) {
			// Fetching TransactionItemJDO list
			TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
			transactionItemQuery.setTransactionId( transaction.getId() );
			transactionItemQuery.orderByTransactionDate( true );
			
			List<TransactionItemEntity> transactionItemList = transactionItemQuery.execute();
			logger.log( Level.INFO, transactionItemList.size() + " transaction items found for transaction id - " + transaction.getId() );

			// Creating TransactionData
			TransactionData transactionData = JDOToDataConverter.convert( transaction, transactionItemList, loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
					
			transactionDataList.add( transactionData );
		}
		
		return response;		
	}

	@Override
	public GetTransactionItemListResponse getTransactionItemList( GetTransactionItemListRequest request ) throws UnexpectedServerException {
		
		Date transactionDateStart = request.getTransactionDateStart();
		Date transactionDateEnd = request.getTransactionDateEnd();

		Date creationDateStart = request.getCreationDateStart();
		Date creationDateEnd = request.getCreationDateEnd();

		// Creating GetTransactionItemResponse
		GetTransactionItemListResponse response = new GetTransactionItemListResponse();
		List<TransactionItemData> transactionItemDataList = new LinkedList<TransactionItemData>(); 
		response.setTransactionItemDataList( transactionItemDataList );
		
		// Checking for valid transactionDate range 
		if( transactionDateStart != null
				&& transactionDateEnd != null
				&& transactionDateStart.equals( transactionDateEnd )
				&& ( !request.isTransactionDateStartInclusive() || !request.isTransactionDateEndInclusive() ) ) {
			
			logger.log( Level.INFO, "Not enough time range for transactionDate. Returning empty transactionDataList." );
			return response;
		}
		
		// Checking for valid creationDate range 
		if( creationDateStart != null
				&& creationDateEnd != null
				&& creationDateStart.equals( creationDateEnd )
				&& ( !request.isCreationDateStartInclusive() || !request.isCreationDateEndInclusive() ) ) {
		
			logger.log( Level.INFO, "Not enough time range for creationDate. Returning empty transactionDataList." );
			return response;
		}		
		
		DataAccessor transactionDataSource = transactionDataSourceFactory.getDataAccessor();

		// Fetching TransactionItemJDO list
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();

		if( request.getTransactionItemTypeId() != null ) {
			TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap().get( request.getTransactionItemTypeId() );
			transactionItemQuery.setTransactionItemTypeIdList( transactionItemTypeData.getIdList() );
		}
		
		if( transactionDateStart != null || transactionDateEnd != null )
			transactionItemQuery.setTransactionDate(
				transactionDateStart, request.isTransactionDateStartInclusive(),
				transactionDateEnd, request.isTransactionDateEndInclusive() );
		
		if( creationDateStart != null || creationDateEnd != null )
			transactionItemQuery.setCreationDate(
				creationDateStart, request.isCreationDateStartInclusive(),
				creationDateEnd, request.isCreationDateEndInclusive() );

		if( request.getTransactionDateChronologicalOrder() != null )
			transactionItemQuery.orderByTransactionDate( request.getTransactionDateChronologicalOrder() );
			
		if( request.getCreationDateChronologicalOrder() != null )
			transactionItemQuery.orderByCreationDate( request.getCreationDateChronologicalOrder() );
		
		List<TransactionItemEntity> transactionItemList = transactionItemQuery.execute( 0, request.getPageSize() );
		logger.log( Level.INFO, transactionItemList.size() + " transaction items found.");

		for( TransactionItemEntity transactionItem : transactionItemList ) {
			// Fetching TransactionJDO
			Transaction transaction = transactionDataSource.getTransaction( transactionItem.getTransactionId() );

			// Creating TransactionData
			TransactionData transactionData = JDOToDataConverter.convert( transaction, null, null );
			
			// Creating TransactionItemData
			TransactionItemData transactionItemData = JDOToDataConverter.convert( transactionItem, transactionData, loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
					
			transactionItemDataList.add( transactionItemData );
		}
		
		return response;		
	}

	@Override
	public GetTransactionItemTypeListResponse getTransactionItemTypeList( GetTransactionItemTypeListRequest request ) throws UnexpectedServerException {
		
		GetTransactionItemTypeListResponse response = new GetTransactionItemTypeListResponse();

		List<TransactionItemTypeData> transactionItemTypeDataParentList = new LinkedList<>();
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = loadTransactionItemTypeIdToTransactionItemTypeDataMap();
		Iterator<TransactionItemTypeData> iterator = transactionItemTypeIdToTransactionItemTypeDataMap.values().iterator();
		while( iterator.hasNext() ) {
			TransactionItemTypeData transactionItemTypeData = iterator.next();
			if( transactionItemTypeData.getParent() == null )
				transactionItemTypeDataParentList.add( transactionItemTypeData );
		}
		
		response.setTransactionItemTypeDataList( transactionItemTypeDataParentList );
		
		return response;
	}
	
	private Map<String, TransactionItemTypeData> loadTransactionItemTypeIdToTransactionItemTypeDataMap() {
		
		DataAccessor transactionDataSource = transactionDataSourceFactory.getDataAccessor();

		TransactionItemTypeQuery transactionItemTypeQuery = transactionDataSource.newTransactionItemTypeQuery();
		List<TransactionItemTypeJDO> transactionItemTypeList = transactionItemTypeQuery.execute();
		
		// TODO: Cache the map in MemCache
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = new LinkedHashMap<>( transactionItemTypeList.size() );
		
		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList ) {
			TransactionItemTypeData transactionItemTypeData = new TransactionItemTypeData();
			transactionItemTypeData.setId( transactionItemType.getId() );
			transactionItemTypeData.setTitle( transactionItemType.getTitle() );
			transactionItemTypeData.setInitialAmount( transactionItemType.getInitialAmount() );
			transactionItemTypeData.setTransactionReportType( transactionItemType.getTransactionReportType() );
			transactionItemTypeIdToTransactionItemTypeDataMap.put( transactionItemType.getId(), transactionItemTypeData );
		}

		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList ) {
			String id = transactionItemType.getId();
			String parentId = transactionItemType.getParentId();
			if( parentId != null ) {
				TransactionItemTypeData transactionItemTypeData = transactionItemTypeIdToTransactionItemTypeDataMap.get( id );
				TransactionItemTypeData parentTransactionItemTypeData = transactionItemTypeIdToTransactionItemTypeDataMap.get( parentId );
				transactionItemTypeData.setParent( parentTransactionItemTypeData );
				parentTransactionItemTypeData.addChild( transactionItemTypeData );
			}
		}
		
		return transactionItemTypeIdToTransactionItemTypeDataMap;
	}
	
}
