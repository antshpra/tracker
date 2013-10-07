package tracker.service.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("../_ah/transaction")
public interface TransactionService extends RemoteService {

	long createTransaction(CreateTransactionRequest request)
			throws IllegalArgumentException;

	long createTransactionItem(CreateTransactionItemRequest request)
			throws IllegalArgumentException;

	List<GetTransactionsResponse> getTransactions(Date olderThan, int count);

}
