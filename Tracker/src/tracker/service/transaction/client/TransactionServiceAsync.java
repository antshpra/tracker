package tracker.service.transaction.client;

import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transaction.shared.GetTotalAmountByTransactionItemTypeResponse;
import tracker.service.transaction.shared.GetTransactionItemTypeListRequest;
import tracker.service.transaction.shared.GetTransactionItemTypeListResponse;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {

	void getTransaction( GetTransactionRequest request, AsyncCallback<GetTransactionResponse> callback );

	void createTransaction( CreateTransactionRequest request, AsyncCallback<String> callback );

	void getTransactionList( GetTransactionListRequest request, AsyncCallback<GetTransactionListResponse> callback);

	void getTransactionItemTypeList( GetTransactionItemTypeListRequest request, AsyncCallback<GetTransactionItemTypeListResponse> callback );

	void getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request, AsyncCallback<GetTotalAmountByTransactionItemTypeResponse> callback );
	
}
