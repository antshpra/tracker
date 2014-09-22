package tracker.service.transactionreport.client;

import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;
import antshpra.gwt.rpc.shared.InvalidRequestException;

import com.claymus.commons.client.UnexpectedServerException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../_ah/transactionreport")
public interface TransactionReportService extends RemoteService {

	GetYearlyReportResponse getYearlyReport( GetYearlyReportRequest request ) throws InvalidRequestException, UnexpectedServerException;
	
	GetMonthlyReportResponse getMonthlyReport( GetMonthlyReportRequest request ) throws InvalidRequestException, UnexpectedServerException;
	
}
