package tracker.client;

import java.util.Date;
import java.util.List;

import tracker.shared.CreateTransactionItemRequest;
import tracker.shared.CreateTransactionRequest;
import tracker.shared.TransactionDetail;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("../_ah/gwtrpc")
public interface TransactionService extends RemoteService {

	long createTransaction(CreateTransactionRequest request)
			throws IllegalArgumentException;

	long createTransactionItem(CreateTransactionItemRequest request)
			throws IllegalArgumentException;

	List<TransactionDetail> getTransactions(Date olderThan, int count);

}
