package tracker.pagecontent.transactionlist.client;

import java.util.List;

import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.google.gwt.user.client.ui.Composite;

public abstract class ReportModule extends Composite {

	public abstract void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList );

}
