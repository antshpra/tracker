package tracker.page.report.client;

import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.client.TransactionReportServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class ReportPage implements EntryPoint {

	private static final TransactionReportServiceAsync transactionReportService = GWT.create( TransactionReportService.class );

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		Window.alert( "Hello World !" );
	}


}
