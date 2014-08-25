package tracker.service.transactionreport.shared;

import java.util.List;

import tracker.service.transactionreport.shared.data.TransactionReportData;
import antshpra.gwt.rpc.shared.Response;

public class GetMonthlyReportResponse extends Response {

	private List<TransactionReportData> transactionReportDataList;
	
	
	public List<TransactionReportData> getTransactionReportDataList() { return this.transactionReportDataList; }
	
	
	public void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList ) { this.transactionReportDataList = transactionReportDataList; }

}
