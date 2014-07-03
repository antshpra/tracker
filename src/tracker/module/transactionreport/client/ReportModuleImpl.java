package tracker.module.transactionreport.client;

import java.util.List;

import tracker.module.transactionreport.client.views.MonthlyReportView;
import tracker.module.transactionreport.client.views.MonthlyReportViewImpl;
import tracker.service.transactionreport.shared.TransactionReportData;
import tracker.theme.client.ThemeFactory;
import tracker.theme.client.TransactionReportModuleStyle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class ReportModuleImpl extends ReportModule {

	private TransactionReportModuleStyle style = ThemeFactory.getTheme().getTransactionReportModuleStyle();

	private Panel panel = new FlowPanel();

	public ReportModuleImpl() {
		initWidget( panel );
	}
	
	@Override
	public void setTransactionReportDataList( List<TransactionReportData> transactionReportDataList ) {
		panel.clear();

		boolean showTitles = true;
		for( TransactionReportData transactionReportData : transactionReportDataList ) {
			MonthlyReportView monthlyReportView = new MonthlyReportViewImpl();
			monthlyReportView.setTransactionReportData( transactionReportData );
			monthlyReportView.setTitlesVisible( showTitles );
			showTitles = showTitles && false;
			panel.add( monthlyReportView );
		}
	}

}
