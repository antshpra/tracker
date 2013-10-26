package tracker.service.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {

	void createTransaction( CreateTransactionRequest request, AsyncCallback<String> callback );

	void createTransactionItem( CreateTransactionItemRequest request, AsyncCallback<String> callback );

	void createTransactionItemList( List<CreateTransactionItemRequest> requestList, AsyncCallback<List<String>> callback );

	void getTransactions( Date olderThan, int count, AsyncCallback<List<GetTransactionsResponse>> callback );

}
