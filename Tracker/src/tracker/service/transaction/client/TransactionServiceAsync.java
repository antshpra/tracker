package tracker.service.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>TransactionService</code>.
 */
public interface TransactionServiceAsync {

	void createTransaction(CreateTransactionRequest request,
			AsyncCallback<Long> callback) throws IllegalArgumentException;

	void createTransactionItem(CreateTransactionItemRequest request,
			AsyncCallback<Long> callback) throws IllegalArgumentException;

	void getTransactions(Date olderThan, int count,
			AsyncCallback<List<GetTransactionsResponse>> callback);

}
