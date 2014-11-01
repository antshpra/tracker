package tracker.service.client;

import tracker.service.shared.CreateTransactionResponse;
import tracker.service.shared.GetTransactionItemListRequest;
import tracker.service.shared.GetTransactionItemListResponse;
import tracker.service.shared.GetTransactionItemTypeListRequest;
import tracker.service.shared.GetTransactionItemTypeListResponse;
import tracker.service.shared.GetTransactionListRequest;
import tracker.service.shared.GetTransactionListResponse;
import tracker.service.shared.SaveTransactionRequest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrackerServiceAsync {

	void saveTransaction(
			SaveTransactionRequest request,
			AsyncCallback<CreateTransactionResponse> callback );

	void getTransactionList(
			GetTransactionListRequest request,
			AsyncCallback<GetTransactionListResponse> callback );

	void getTransactionItemList( GetTransactionItemListRequest request, AsyncCallback<GetTransactionItemListResponse> callback);

	void getTransactionItemTypeList( GetTransactionItemTypeListRequest request, AsyncCallback<GetTransactionItemTypeListResponse> callback );

}
