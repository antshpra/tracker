package tracker.module.transactionreport.client;

import java.util.List;

import tracker.service.transactionreport.shared.TransactionReportData;

import com.google.gwt.user.client.ui.Composite;

public abstract class ReportModule extends Composite {

	public abstract void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList );

}
