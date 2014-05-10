package tracker.service.transactionreport.client;

import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionReportServiceAsync {

	void getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request, AsyncCallback<GetTotalAmountByTransactionItemTypeResponse> callback );
	
	void getMonthlyReport( GetMonthlyReportRequest reqest, AsyncCallback<GetMonthlyReportResponse> callback ) throws InvalidRequestException, ServerException;
	
	void getYearlyReport( GetYearlyReportRequest reqest, AsyncCallback<GetYearlyReportResponse> callback ) throws InvalidRequestException, ServerException;

}
