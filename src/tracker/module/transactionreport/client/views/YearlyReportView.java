package tracker.module.transactionreport.client.views;

import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.google.gwt.user.client.ui.Composite;

public abstract class YearlyReportView extends Composite {

	public abstract void setTransactionReportData( TransactionReportData transactionReportData );

	public abstract void setTitlesVisible( boolean visible );
	
}
