package tracker.module.transactionreport.client;

import tracker.service.transactionreport.shared.TransactionReportData;

import com.google.gwt.user.client.ui.Composite;

public abstract class MonthlyReportModule extends Composite {

	public abstract void setTransactionReportData( TransactionReportData transactionReportData );

}
