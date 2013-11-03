package tracker.service.transaction.client;

import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {

	void getTransaction( GetTransactionRequest request, AsyncCallback<GetTransactionResponse> callback );

	void createTransaction( CreateTransactionRequest request, AsyncCallback<String> callback );

	void getTransactionList( GetTransactionListRequest request, AsyncCallback<GetTransactionListResponse> callback);

}
