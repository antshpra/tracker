package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Response;

public class GetYearlyReportResponse extends Response {
	
	private static final long serialVersionUID = 1222227202755133541L;

	private TransactionReportData transactionReportData;
	
	
	public TransactionReportData getTransactionReportData() { return this.transactionReportData; }
	
	
	public void setTransactionReportData( TransactionReportData transactionReportData ) { this.transactionReportData = transactionReportData; }

}
