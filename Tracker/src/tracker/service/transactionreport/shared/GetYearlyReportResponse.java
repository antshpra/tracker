package tracker.service.transactionreport.shared;

import java.util.List;

import antshpra.gwt.rpc.shared.Response;

public class GetYearlyReportResponse extends Response {
	
	private static final long serialVersionUID = 1222227202755133541L;

	private List<TransactionReportData> transactionReportDataList;
	
	
	public List<TransactionReportData> getTransactionReportDataList() { return this.transactionReportDataList; }
	
	
	public void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList ) { this.transactionReportDataList = transactionReportDataList; }

}
