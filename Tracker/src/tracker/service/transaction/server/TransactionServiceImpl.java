package tracker.service.transaction.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;
import tracker.service.transaction.shared.TransactionData;
import tracker.service.transaction.shared.TransactionItemData;
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
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();
        
		for( TransactionItemJDO transactionItem : transactionItemList )
			transaction.addTransactionItemJDO( transactionItem );
		
		transactionDataSource.close();

		GetTransactionResponse response = new GetTransactionResponse();
		response.setTransactionData( transactionJDOToTransactionData( transaction ) );
		
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

	private String createTransactionItem( CreateTransactionItemRequest itemRequest ) throws InvalidRequestException, ServerException {

		RequestValidator.validate( itemRequest );

		TransactionItemJDO transactionItem = new TransactionItemJDO();
		transactionItem.setTransactionId( itemRequest.getTransactionId() );
		transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
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
			transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
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
				transactionDataList.add( transactionJDOToTransactionData( transaction ) );

			transactionDataSource.close();
	} else {
			logger.log( Level.INFO, "Not enough data or time range. Returning empty transactionDataList." );
		}
		
		response.setTransactionDataList( transactionDataList );
		return response;		
	}

	private TransactionData transactionJDOToTransactionData( TransactionJDO transaction ) {
		TransactionData transactionData = new TransactionData();
		transactionData.setId( transaction.getId() );
		transactionData.setTransactionDate( transaction.getTransactionDate() );
		transactionData.setDescription( transaction.getDescription() );
		transactionData.setCreationDate( transaction.getCreationDate() );
		transactionData.setCreatedBy( transaction.getCreatedBy() );
		
		if( transaction.getTransactionItemJDOList() != null ) {
			for( TransactionItemJDO transactionItem : transaction.getTransactionItemJDOList() )
				transactionData.addTransactionItemData( transactionItemJDOToTransactionItemData( transactionItem ) );
		}
		
		return transactionData;
	}

	private TransactionItemData transactionItemJDOToTransactionItemData( TransactionItemJDO transactionItem ) {
		TransactionItemData transactionItemData = new TransactionItemData();
		transactionItemData.setId( transactionItem.getId() );
		transactionItemData.setTransactionId( transactionItem.getTransactionId() );
		transactionItemData.setTransactionDate( transactionItem.getTransactionDate() );
		transactionItemData.setDescription( transactionItem.getDescription() );
		transactionItemData.setCreationDate( transactionItem.getCreationDate() );
		transactionItemData.setCreatedBy( transactionItem.getCreatedBy() );
		return transactionItemData;
	}

}
