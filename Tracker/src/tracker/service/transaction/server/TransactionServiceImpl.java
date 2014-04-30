package tracker.service.transaction.server;

import java.util.Date;
import java.util.HashMap;
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
import tracker.service.transaction.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transaction.shared.GetTotalAmountByTransactionItemTypeResponse;
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

		TransactionJDO transaction = transactionDataSource.getTransaction( request.getTransactionId() );
		
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionId( request.getTransactionId() );
		transactionItemQuery.orderByTransactionDate( true );
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

		TransactionItemTypeQuery transactionItemTypeQuery = transactionDataSource.newTransactionItemTypeQuery();
		List<TransactionItemTypeJDO> transactionItemTypeList = transactionItemTypeQuery.execute();
		
		Map<String, TransactionItemTypeJDO> transactionItemTypeIdToTransactionItemTypeMap = new HashMap<>();
		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList )
			transactionItemTypeIdToTransactionItemTypeMap.put( transactionItemType.getId(), transactionItemType );
		
		GetTransactionResponse response = new GetTransactionResponse();
		response.setTransactionData( transactionJDOToTransactionData( transaction, transactionItemList, transactionItemTypeIdToTransactionItemTypeMap ) );
		
		transactionDataSource.close();

		return response;
	}
	
	@Override
	public String createTransaction( CreateTransactionRequest request ) throws InvalidRequestException, ServerException {

		RequestValidator.validate( request );

		if( request.getCreateTransactionItemRequestList() != null )
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() )
				RequestValidator.validate( itemRequest );

		TransactionJDO transaction = new TransactionJDO();
		transaction.setTransactionDate( request.getTransactionDate() == null ? new Date() : request.getTransactionDate() );
		transaction.setDescription( request.getDescription() );
		transaction.setCreationDate( new Date() );
		transaction.setCreatedBy( "prashant@claymus.com" ); // TODO: Fetch and set user id instead of hard coded id

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		transaction = transactionDataSource.persistTransaction( transaction );
		transactionDataSource.close();
		
		if( request.getCreateTransactionItemRequestList() != null ) {
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() )
				itemRequest.setTransactionId( transaction.getId() );
			createTransactionItemList( request.getCreateTransactionItemRequestList() );
		} else {
			logger.log( Level.INFO, "CreateTransactionItemRequestList is null." );
		}

		return transaction.getId();
	}

	@SuppressWarnings("unused")
	private String createTransactionItem( CreateTransactionItemRequest itemRequest ) throws InvalidRequestException, ServerException {

		RequestValidator.validate( itemRequest );

		TransactionItemJDO transactionItem = new TransactionItemJDO();
		transactionItem.setTransactionId( itemRequest.getTransactionId() );
		transactionItem.setTransactionItemTypeId( itemRequest.getTransactionItemTypeId() );
		transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
		transactionItem.setAmount( itemRequest.getAmount() );
		transactionItem.setDescription( itemRequest.getDescription() );
		transactionItem.setCreationDate( new Date() );
		transactionItem.setCreatedBy( "prashant@claymus.com" ); // TODO: Fetch and set user id instead of hard coded id
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		transactionItem = transactionDataSource.persistTransactionItem( transactionItem );
		transactionDataSource.close();
		
		return transactionItem.getId();
	}

	private List<String> createTransactionItemList( List<CreateTransactionItemRequest> itemRequestList ) throws InvalidRequestException, ServerException {

		for( CreateTransactionItemRequest itemRequest : itemRequestList )
			RequestValidator.validate( itemRequest );

		List<TransactionItemJDO> transactionItemList = new LinkedList<TransactionItemJDO>();
		for( CreateTransactionItemRequest itemRequest : itemRequestList ) {
			TransactionItemJDO transactionItem = new TransactionItemJDO();
			transactionItem.setTransactionId( itemRequest.getTransactionId() );
			transactionItem.setTransactionItemTypeId( itemRequest.getTransactionItemTypeId() );
			transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
			transactionItem.setAmount( itemRequest.getAmount() );
			transactionItem.setDescription( itemRequest.getDescription() );
			transactionItem.setCreationDate( new Date() );
			transactionItem.setCreatedBy( "prashant@claymus.com" ); // TODO: Fetch and set user id instead of hard coded id
			transactionItemList.add( transactionItem );
		}

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		transactionItemList = transactionDataSource.persistTransactionItemList( transactionItemList );
		transactionDataSource.close();

		List<String> transactionItemIdList = new LinkedList<String>();
		for( TransactionItemJDO transactionItem : transactionItemList )
			transactionItemIdList.add( transactionItem.getId() );
		
		return transactionItemIdList;
	}
	
	@Override
	public GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );
		
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		
		GetTransactionListResponse response = new GetTransactionListResponse();
		List<TransactionData> transactionDataList = new LinkedList<TransactionData>(); 

		if( ( startDate != null && endDate == null )
				|| ( startDate == null && endDate != null )
				|| ( startDate != null && endDate != null && !startDate.equals( endDate ) )
				|| ( startDate != null && endDate != null && startDate.equals( endDate ) && request.isStartDateInclusive() && request.isEndDateInclusive() ) ) {

			TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
			TransactionQuery transactionQuery = transactionDataSource.newTransactionQuery();
			transactionQuery.setCreationDate( startDate, request.isStartDateInclusive(), endDate, request.isEndDateInclusive() );
			transactionQuery.orderByCreationDate( request.isChronologicalOrder() );
			List<TransactionJDO> transactionList = transactionQuery.execute( 0, request.getPageSize() );
	
			logger.log( Level.INFO, transactionList.size() + " transactions found for query \"" + transactionQuery.toString() + "\"");
			
			for( TransactionJDO transaction : transactionList )
				transactionDataList.add( transactionJDOToTransactionData( transaction, null, null ) );

			transactionDataSource.close();
		} else {
			logger.log( Level.INFO, "Not enough data or time range. Returning empty transactionDataList." );
		}
		
		response.setTransactionDataList( transactionDataList );
		return response;		
	}

	@Override
	public GetTransactionItemTypeListResponse getTransactionItemTypeList( GetTransactionItemTypeListRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		GetTransactionItemTypeListResponse response = new GetTransactionItemTypeListResponse();
		List<TransactionItemTypeData> transactionItemTypeDataList = new LinkedList<TransactionItemTypeData>(); 

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		TransactionItemTypeQuery transactionItemTypeQuery = transactionDataSource.newTransactionItemTypeQuery();
		List<TransactionItemTypeJDO> transactionItemTypeList = transactionItemTypeQuery.execute();
		
		logger.log( Level.INFO, transactionItemTypeList.size() + " transaction item types found for query \"" + transactionItemTypeQuery.toString() + "\"");
		
		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList )
			transactionItemTypeDataList.add( transactionItemTypeJDOToTransactionItemTypeData( transactionItemType ) );
		
		transactionDataSource.close();
		
		response.setTransactionItemTypeDataList( transactionItemTypeDataList );
		
		return response;
	}
	
	@Override
	public GetTotalAmountByTransactionItemTypeResponse getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionItemTypeId( request.getTransactionItemTypeId() );
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

		double amount = transactionDataSource
				.getTransactionItemType( request.getTransactionItemTypeId() )
				.getInitialAmount();
		
		for( TransactionItemJDO transactionItem : transactionItemList )
			amount = amount + transactionItem.getAmount();
		
		transactionDataSource.close();
		
		GetTotalAmountByTransactionItemTypeResponse response = new GetTotalAmountByTransactionItemTypeResponse();
		response.setAmount( amount );
		
		return response;
	}

	private TransactionData transactionJDOToTransactionData( TransactionJDO transaction, List<TransactionItemJDO> transactionItemList, Map<String, TransactionItemTypeJDO> transactionItemTypeIdToTransactionItemTypeMap ) {
		TransactionData transactionData = new TransactionData();
		transactionData.setId( transaction.getId() );
		transactionData.setTransactionDate( transaction.getTransactionDate() );
		transactionData.setDescription( transaction.getDescription() );
		transactionData.setCreationDate( transaction.getCreationDate() );
		transactionData.setCreatedBy( transaction.getCreatedBy() );
		
		if( transactionItemList != null ) {
			for( TransactionItemJDO transactionItem : transactionItemList )
				transactionData.addTransactionItemData( transactionItemJDOToTransactionItemData( transactionItem, transactionItemTypeIdToTransactionItemTypeMap ) );
		}
		
		return transactionData;
	}

	private TransactionItemData transactionItemJDOToTransactionItemData( TransactionItemJDO transactionItem, Map<String, TransactionItemTypeJDO> transactionItemTypeIdToTransactionItemTypeMap ) {
		TransactionItemData transactionItemData = new TransactionItemData();
		transactionItemData.setId( transactionItem.getId() );
		transactionItemData.setTransactionId( transactionItem.getTransactionId() );
		transactionItemData.setTransactionItemType(
				transactionItemTypeJDOToTransactionItemTypeData(
						transactionItemTypeIdToTransactionItemTypeMap.get(
								transactionItem.getTransactionItemTypeId() ) ));
		transactionItemData.setTransactionDate( transactionItem.getTransactionDate() );
		transactionItemData.setAmount( transactionItem.getAmount() );
		transactionItemData.setDescription( transactionItem.getDescription() );
		transactionItemData.setCreationDate( transactionItem.getCreationDate() );
		transactionItemData.setCreatedBy( transactionItem.getCreatedBy() );
		return transactionItemData;
	}

	private TransactionItemTypeData transactionItemTypeJDOToTransactionItemTypeData( TransactionItemTypeJDO transactionItemType ) {
		TransactionItemTypeData transactionItemTypeData = new TransactionItemTypeData();
		transactionItemTypeData.setId( transactionItemType.getId() );
		transactionItemTypeData.setParentId( transactionItemType.getParentId() );
		transactionItemTypeData.setTitle( transactionItemType.getTitle() );
		return transactionItemTypeData;
	}

}
