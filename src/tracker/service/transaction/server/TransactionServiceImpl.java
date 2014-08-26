package tracker.service.transaction.server;

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
import tracker.commons.server.RequestToJDOConverter;
import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.CreateTransactionResponse;
import tracker.service.transaction.shared.GetTransactionItemListRequest;
import tracker.service.transaction.shared.GetTransactionItemListResponse;
import tracker.service.transaction.shared.GetTransactionItemTypeListRequest;
import tracker.service.transaction.shared.GetTransactionItemTypeListResponse;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;
import tracker.service.transaction.shared.data.TransactionData;
import tracker.service.transaction.shared.data.TransactionItemData;
import tracker.service.transaction.shared.data.TransactionItemTypeData;
import antshpra.gwt.rpc.server.RequestValidator;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements TransactionService {

	private static final Logger logger = Logger.getLogger( TransactionServiceImpl.class.getName() );
	private static final TransactionDataSourceFactory transactionDataSourceFactory = new TransactionDataSourceFactory();
	
	@Override
	public GetTransactionResponse getTransaction( GetTransactionRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		// Fetching TransactionJDO
		TransactionJDO transaction = transactionDataSource.getTransaction( request.getTransactionId() );
		
		// Fetching TransactionItemJDO list
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionId( request.getTransactionId() );
		transactionItemQuery.orderByTransactionDate( true );
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();
		logger.log( Level.INFO, transactionItemList.size() + " transaction items found for transaction id - " + request.getTransactionId() );

		// Creating TransactionData
		TransactionData transactionData = JDOToDataConverter.convert(
				transaction,
				transactionItemList,
				loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
				
		// Creating GetTransactionResponse
		GetTransactionResponse response = new GetTransactionResponse();
		response.setTransactionData( transactionData );
		
		transactionDataSource.close();

		return response;
	}
	
	@Override
	public CreateTransactionResponse createTransaction( CreateTransactionRequest request )
			throws InvalidRequestException, ServerException {

		RequestValidator.validate( request );

		if( request.getCreateTransactionItemRequestList() != null )
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() )
				RequestValidator.validate( itemRequest );

		TransactionJDO transaction = RequestToJDOConverter.convert( request );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		transaction = transactionDataSource.persistTransaction( transaction );
		
		if( request.getCreateTransactionItemRequestList() != null ) {
			List<TransactionItemJDO> transactionItemList = new ArrayList<>( request.getCreateTransactionItemRequestList().size() );
			long order = 0;
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() ) {
				itemRequest.setTransactionId( transaction.getId() );
				if( itemRequest.getTransactionDate() == null ) {
					itemRequest.setTransactionDate( request.getTransactionDate() );
					TransactionItemJDO transactionItem = RequestToJDOConverter.convert( itemRequest );
					transactionItem.setHasTransactionDate( false );
					transactionItem.setOrder( order++ );
					transactionItemList.add( transactionItem );
				} else {
					TransactionItemJDO transactionItem = RequestToJDOConverter.convert( itemRequest );
					transactionItem.setHasTransactionDate( true );
					transactionItem.setOrder( order++ );
					transactionItemList.add( transactionItem );
				}
				
			}
			transactionDataSource.persistTransactionItemList( transactionItemList );
		} else {
			logger.log( Level.INFO, "CreateTransactionItemRequestList is null." );
		}

		transactionDataSource.close();

		// Creating response
		CreateTransactionResponse response = new CreateTransactionResponse();
		response.setTransactionId( transaction.getId() );
		
		return response;
	}

	@Override
	public GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );
		
		Date transactionDateStart = request.getTransactionDateStart();
		Date transactionDateEnd = request.getTransactionDateEnd();

		Date creationDateStart = request.getCreationDateStart();
		Date creationDateEnd = request.getCreationDateEnd();

		// Creating GetTransactionResponse
		GetTransactionListResponse response = new GetTransactionListResponse();
		List<TransactionData> transactionDataList = new LinkedList<TransactionData>(); 
		response.setTransactionDataList( transactionDataList );
		
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
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

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
		
		List<TransactionJDO> transactionList = transactionQuery.execute( 0, request.getPageSize() );
		logger.log( Level.INFO, transactionList.size() + " transactions found.");

		for( TransactionJDO transaction : transactionList ) {
			// Fetching TransactionItemJDO list
			TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
			transactionItemQuery.setTransactionId( transaction.getId() );
			transactionItemQuery.orderByTransactionDate( true );
			
			List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();
			logger.log( Level.INFO, transactionItemList.size() + " transaction items found for transaction id - " + transaction.getId() );

			// Creating TransactionData
			TransactionData transactionData = JDOToDataConverter.convert( transaction, transactionItemList, loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
					
			transactionDataList.add( transactionData );
		}
		
		transactionDataSource.close();
		
		return response;		
	}

	@Override
	public GetTransactionItemListResponse getTransactionItemList( GetTransactionItemListRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );
		
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
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

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
		
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute( 0, request.getPageSize() );
		logger.log( Level.INFO, transactionItemList.size() + " transaction items found.");

		for( TransactionItemJDO transactionItem : transactionItemList ) {
			// Fetching TransactionJDO
			TransactionJDO transaction = transactionDataSource.getTransaction( transactionItem.getTransactionId() );

			// Creating TransactionData
			TransactionData transactionData = JDOToDataConverter.convert( transaction, null, null );
			
			// Creating TransactionItemData
			TransactionItemData transactionItemData = JDOToDataConverter.convert( transactionItem, transactionData, loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
					
			transactionItemDataList.add( transactionItemData );
		}
		
		transactionDataSource.close();
		
		return response;		
	}

	@Override
	public GetTransactionItemTypeListResponse getTransactionItemTypeList( GetTransactionItemTypeListRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

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
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

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
		
		transactionDataSource.close();
		
		return transactionItemTypeIdToTransactionItemTypeDataMap;
	}
	
}
