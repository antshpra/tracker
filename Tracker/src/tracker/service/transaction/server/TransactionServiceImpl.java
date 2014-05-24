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
import tracker.service.transaction.shared.GetTransactionItemTypeListRequest;
import tracker.service.transaction.shared.GetTransactionItemTypeListResponse;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;
import tracker.service.transaction.shared.TransactionData;
import tracker.service.transaction.shared.TransactionItemData;
import tracker.service.transaction.shared.TransactionItemTypeData;
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
		TransactionData transactionData = transactionJDOToTransactionData(
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
	public CreateTransactionResponse createTransaction( CreateTransactionRequest request ) throws InvalidRequestException, ServerException {

		RequestValidator.validate( request );

		if( request.getCreateTransactionItemRequestList() != null )
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() )
				RequestValidator.validate( itemRequest );

		TransactionJDO transaction = createTransactionRequestToTransactionJDO( request );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		transaction = transactionDataSource.persistTransaction( transaction );
		
		if( request.getCreateTransactionItemRequestList() != null ) {
			List<TransactionItemJDO> transactionItemList = new ArrayList<>( request.getCreateTransactionItemRequestList().size() );
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() ) {
				itemRequest.setTransactionId( transaction.getId() );
				if( itemRequest.getTransactionDate() == null ) {
					itemRequest.setTransactionDate( request.getTransactionDate() );
				}
				
				TransactionItemJDO transactionItem = createTransactionItemRequestToTransactionItemJDO( itemRequest );
				transactionItemList.add( transactionItem );
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
			TransactionData transactionData = transactionJDOToTransactionData( transaction, transactionItemList, loadTransactionItemTypeIdToTransactionItemTypeDataMap() );
					
			transactionDataList.add( transactionData );
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
	
	private TransactionData transactionJDOToTransactionData(
			TransactionJDO transaction,
			List<TransactionItemJDO> transactionItemList, Map<String,
			TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionData transactionData = new TransactionData();
		transactionData.setId( transaction.getId() );
		transactionData.setTransactionDate( transaction.getTransactionDate() );
		transactionData.setDescription( transaction.getDescription() );
		transactionData.setCreationDate( transaction.getCreationDate() );
		transactionData.setCreatedBy( transaction.getCreatedBy() );
		
		if( transactionItemList != null ) {
			for( TransactionItemJDO transactionItem : transactionItemList ) {
				TransactionItemData transactionItemData = transactionItemJDOToTransactionItemData(
						transactionItem,
						transactionItemTypeIdToTransactionItemTypeDataMap );
				transactionData.addTransactionItemData( transactionItemData );
			}
		}
		
		return transactionData;
	}

	private TransactionItemData transactionItemJDOToTransactionItemData(
			TransactionItemJDO transactionItem,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionItemData transactionItemData = new TransactionItemData();
		transactionItemData.setId( transactionItem.getId() );
		transactionItemData.setTransactionId( transactionItem.getTransactionId() );
		transactionItemData.setTransactionItemType(
				transactionItemTypeIdToTransactionItemTypeDataMap.get(
						transactionItem.getTransactionItemTypeId() ) );
		transactionItemData.setTransactionDate( transactionItem.getTransactionDate() );
		transactionItemData.setAmount( transactionItem.getAmount() );
		transactionItemData.setNote( transactionItem.getNote() );
		transactionItemData.setCreationDate( transactionItem.getCreationDate() );
		transactionItemData.setCreatedBy( transactionItem.getCreatedBy() );
		
		return transactionItemData;
	}
	
	private TransactionJDO createTransactionRequestToTransactionJDO( CreateTransactionRequest request ) {
		
		TransactionJDO transaction = new TransactionJDO();
		transaction.setTransactionDate( request.getTransactionDate() == null ? new Date() : request.getTransactionDate() );
		transaction.setDescription( request.getDescription() );
		transaction.setCreationDate( new Date() );
		transaction.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id

		return transaction;
	}
	
	private TransactionItemJDO createTransactionItemRequestToTransactionItemJDO( CreateTransactionItemRequest itemRequest ) {
		
		TransactionItemJDO transactionItem = new TransactionItemJDO();
		transactionItem.setTransactionId( itemRequest.getTransactionId() );
		transactionItem.setTransactionItemTypeId( itemRequest.getTransactionItemTypeId() );
		transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
		transactionItem.setAmount( itemRequest.getAmount() );
		transactionItem.setNote( itemRequest.getNote() );
		transactionItem.setCreationDate( new Date() );
		transactionItem.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id

		return transactionItem;
	}
}
