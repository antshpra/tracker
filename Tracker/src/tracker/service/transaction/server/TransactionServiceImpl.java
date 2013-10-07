package tracker.service.transaction.server;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import tracker.PMF;
import tracker.server.jdo.Transaction;
import tracker.server.jdo.TransactionItem;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;
import antshpra.gwt.rpc.server.RequestValidator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements TransactionService {

	public long createTransaction(CreateTransactionRequest request) throws IllegalArgumentException {

		RequestValidator.validate( request );

		Transaction transaction = new Transaction();
		transaction.setCreationDate( new Date() );
		transaction.setTransactionDate( new Date() );
		transaction.setDescription( request.getDescription() );
		transaction.setCreatedBy("prashant@claymus.com"); // TODO: Set user id instead of hard coded id

		transaction = PMF.get().makePersistent(transaction);
		
		if( request.getCreateTransactionItemRequestList() == null ) {
			// TODO: Print log "CreateTransactionItemRequestList is null."
			return transaction.getId();
		}
		
		for( CreateTransactionItemRequest itemRequest : request.getCreateTransactionItemRequestList() ) { // TODO: make a batch call instead
			itemRequest.setTransactionId( transaction.getId() );
			createTransactionItem( itemRequest );
		}

		return transaction.getId();
	}

	public long createTransactionItem(CreateTransactionItemRequest request) throws IllegalArgumentException {

		RequestValidator.validate( request );

		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setCreationDate(new Date());
		transactionItem.setDescription(request.getDescription());
		transactionItem.setCreatedBy("prashant@claymus.com");

		// TODO: set transactionItem.key, the parent-child relationship
		
		transactionItem = PMF.get().makePersistent( transactionItem );

		return transactionItem.getId();
	}

	@SuppressWarnings("unchecked")
	public List<GetTransactionsResponse> getTransactions(Date olderThan, int count) {

		List<GetTransactionsResponse> transactionDtailList = new LinkedList<GetTransactionsResponse>();
		Map<Long, GetTransactionsResponse> transactionDetailMap = new HashMap<Long, GetTransactionsResponse>();

		PersistenceManager pm = PMF.get();

		Query query = pm.newQuery(Transaction.class);
		query.setFilter("creationDate < olderThan");
		query.declareParameters(Date.class.getName() + " olderThan");
		query.setOrdering("creationDate DESC");
		query.setRange(0, count);

		List<Transaction> transactionList = (List<Transaction>) query.execute(olderThan);

		for (Transaction transaction : transactionList) {
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
