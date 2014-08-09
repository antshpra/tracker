package tracker.module.transactionreport.client.views;

import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.google.gwt.user.client.ui.Composite;

public abstract class MonthlyReportView extends Composite {

	public abstract void setTransactionReportData( TransactionReportData transactionReportData );

	public abstract void setTitlesVisible( boolean visible );
	
}
