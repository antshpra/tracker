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
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;
import antshpra.gwt.rpc.server.RequestValidator;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements TransactionService {

	private static Logger logger = Logger.getLogger( TransactionServiceImpl.class.getName() );
	
	
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
	
	@SuppressWarnings("unchecked")
	public List<GetTransactionsResponse> getTransactions(Date olderThan, int count) {

		List<GetTransactionsResponse> transactionDtailList = new LinkedList<GetTransactionsResponse>();
		Map<String, GetTransactionsResponse> transactionDetailMap = new HashMap<String, GetTransactionsResponse>();

		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory( "transactions-optional" ).getPersistenceManager();

		Query query = pm.newQuery(TransactionJDO.class);
		query.setFilter("creationDate < olderThan");
		query.declareParameters(Date.class.getName() + " olderThan");
		query.setOrdering("creationDate DESC");
		query.setRange(0, count);

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
