package tracker.client;

import java.util.Date;
import java.util.List;

import tracker.shared.CreateTransactionItemRequest;
import tracker.shared.CreateTransactionRequest;
import tracker.shared.TransactionDetail;

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
			AsyncCallback<List<TransactionDetail>> callback);

}
