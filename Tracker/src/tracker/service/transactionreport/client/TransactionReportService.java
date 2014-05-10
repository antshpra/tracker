package tracker.service.transactionreport.client;

import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../_ah/transactionreport")
public interface TransactionReportService extends RemoteService {

	GetTotalAmountByTransactionItemTypeResponse getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request ) throws InvalidRequestException, ServerException;

	GetMonthlyReportResponse getMonthlyReport( GetMonthlyReportRequest reqest ) throws InvalidRequestException, ServerException;
	
	GetYearlyReportResponse getYearlyReport( GetYearlyReportRequest reqest ) throws InvalidRequestException, ServerException;
	
}
