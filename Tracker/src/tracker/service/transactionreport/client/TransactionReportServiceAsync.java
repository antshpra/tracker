package tracker.service.transactionreport.client;

import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionReportServiceAsync {

	void getMonthlyReport( GetMonthlyReportRequest request, AsyncCallback<GetMonthlyReportResponse> callback );
	
	void getYearlyReport( GetYearlyReportRequest request, AsyncCallback<GetYearlyReportResponse> callback );

}
