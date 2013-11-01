package tracker.service.transaction.server;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionsResponse;
import tracker.service.transaction.shared.TransactionData;
import antshpra.gwt.rpc.server.RequestValidator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements TransactionService {

	private static Logger logger = Logger.getLogger( TransactionServiceImpl.class.getName() );
	
	
	@Override
	public String createTransaction( CreateTransactionRequest request ) {

		RequestValidator.validate( request );

		if( request.getCreateTransactionItemRequestList() != null )
			for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() )
				RequestValidator.validate( itemRequest );

		TransactionJDO transaction = new TransactionJDO();
		transaction.setTransactionDate( request.getTransactionDate() == null ? new Date() : request.getTransactionDate() );
		transaction.setDescription( request.getDescription() );
		transaction.setCreationDate( new Date() );
		transaction.setCreatedBy( "prashant@claymus.com" ); // TODO: Fetch and set user id instead of hard coded id

		TransactionDataSource transactionDataSource = new TransactionDataSource();
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

	@Override
	public String createTransactionItem( CreateTransactionItemRequest itemRequest ) {

		RequestValidator.validate( itemRequest );

		TransactionItemJDO transactionItem = new TransactionItemJDO();
		transactionItem.setTransactionId( itemRequest.getTransactionId() );
		transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
		transactionItem.setDescription( itemRequest.getDescription() );
		transactionItem.setCreationDate( new Date() );
		transactionItem.setCreatedBy( "prashant@claymus.com" ); // TODO: Fetch and set user id instead of hard coded id
		
		TransactionDataSource transactionDataSource = new TransactionDataSource();
		transactionItem = transactionDataSource.persistTransactionItem( transactionItem );
		transactionDataSource.close();
		
		return transactionItem.getId();
	}

	@Override
	public List<String> createTransactionItemList( List<CreateTransactionItemRequest> itemRequestList ) {

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

		TransactionDataSource transactionDataSource = new TransactionDataSource();
		transactionItemList = transactionDataSource.persistTransactionItemList( transactionItemList );
		transactionDataSource.close();

		List<String> transactionItemIdList = new LinkedList<String>();
		for( TransactionItemJDO transactionItem : transactionItemList )
			transactionItemIdList.add( transactionItem.getId() );
		
		return transactionItemIdList;
	}
	
	@Override
	public GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) {
		
		RequestValidator.validate( request );
		
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		
		GetTransactionListResponse response = new GetTransactionListResponse();
		List<TransactionData> transactionDataList = new LinkedList<TransactionData>(); 

		if( ( startDate != null && endDate == null )
				|| ( startDate == null && endDate != null )
				|| ( startDate != null && endDate != null && !startDate.equals( endDate ) )
				|| ( startDate != null && endDate != null && startDate.equals( endDate ) && request.isStartDateInclusive() && request.isEndDateInclusive() ) ) {

			TransactionDataSource transactionDataSource = new TransactionDataSource();	
			TransactionQuery transactionQuery = transactionDataSource.newTransactionQuery();
			transactionQuery.setCreationDate( startDate, request.isStartDateInclusive(), endDate, request.isEndDateInclusive() );
			transactionQuery.orderByCreationDate( request.isChronologicalOrder() );
			List<TransactionJDO> transactionList = transactionQuery.execute( 0, request.getPageSize(), false );
			transactionDataSource.close();

			logger.log( Level.INFO, transactionDataList.size() + " transactions found for query \"" + transactionQuery.toString() + "\"");
			
			for( TransactionJDO transaction : transactionList ) {
				TransactionData transactionData = new TransactionData();
				transactionData.setId( transaction.getId() );
				transactionData.setTransactionDate( transaction.getTransactionDate() );
				transactionData.setDescription( transaction.getDescription() );
				transactionData.setCreationDate( transaction.getCreationDate() );
				transactionData.setCreatedBy( transaction.getCreatedBy() );
				transactionDataList.add( transactionData );
			}
		} else {
			logger.log( Level.INFO, "Not enough data or time range. Returning empty transactionDataList." );
		}
		
		response.setTransactionDataList( transactionDataList );
		return response;		
	}

	@Override
	public List<GetTransactionsResponse> getTransactions(Date olderThan, int count) {

		List<GetTransactionsResponse> transactionDtailList = new LinkedList<GetTransactionsResponse>();
		Map<String, GetTransactionsResponse> transactionDetailMap = new HashMap<String, GetTransactionsResponse>();

		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory( "transactions-optional" ).getPersistenceManager();

		Query query = pm.newQuery(TransactionJDO.class);
		query.setFilter("creationDate < olderThan");
		query.declareParameters(Date.class.getName() + " olderThan");
		query.setOrdering("creationDate DESC");
		query.setRange(0, count);

		@SuppressWarnings("unchecked")
		List<TransactionJDO> transactionList = (List<TransactionJDO>) query.execute(olderThan);

		for (TransactionJDO transaction : transactionList) {
			GetTransactionsResponse transactionDetail = new GetTransactionsResponse();

			transactionDetail.setId(transaction.getId());
			transactionDetail.setDescription(transaction.getDescription());
			transactionDetail.setCreationDate(new Date(transaction.getCreationDate().getTime()));
			transactionDetail.setCreatedBy(transaction.getCreatedBy());

			transactionDtailList.add(transactionDetail);
			transactionDetailMap.put(transactionDetail.getId(),transactionDetail);
		}

		query.closeAll();

		pm.close();

		return transactionDtailList;
	}

}
