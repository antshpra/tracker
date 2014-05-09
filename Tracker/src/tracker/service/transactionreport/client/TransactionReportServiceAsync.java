package tracker.service.transactionreport.client;

import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionReportServiceAsync {

	void getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request, AsyncCallback<GetTotalAmountByTransactionItemTypeResponse> callback );
	
}
