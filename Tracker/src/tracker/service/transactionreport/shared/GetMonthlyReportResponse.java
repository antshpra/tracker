package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Response;

public class GetMonthlyReportResponse extends Response {

	private static final long serialVersionUID = 1422741293288981428L;

	private TransactionReportData transactionReportData;
	
	
	public TransactionReportData getTransactionReportData() { return this.transactionReportData; }
	
	
	public void setTransactionReportData( TransactionReportData transactionReportData ) { this.transactionReportData = transactionReportData; }

}
