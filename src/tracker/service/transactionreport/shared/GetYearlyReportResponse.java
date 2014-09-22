package tracker.service.transactionreport.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import tracker.service.transactionreport.shared.data.TransactionReportData;

public class GetYearlyReportResponse implements IsSerializable {
	
	private List<TransactionReportData> transactionReportDataList;
	
	
	public List<TransactionReportData> getTransactionReportDataList() { return this.transactionReportDataList; }
	
	
	public void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList ) { this.transactionReportDataList = transactionReportDataList; }

}
