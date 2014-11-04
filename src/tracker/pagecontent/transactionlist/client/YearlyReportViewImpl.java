package tracker.pagecontent.transactionlist.client;

import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class YearlyReportViewImpl extends YearlyReportView {

	private Panel titlePanel = new FlowPanel();
	
	private Panel valuePanel = new FlowPanel();
	
	
	public YearlyReportViewImpl() {
		Panel panel = new FlowPanel();
		panel.add( titlePanel );
		panel.add( valuePanel );
		initWidget( panel );
	}

	
	@Override
	public void setTransactionReportData( TransactionReportData transactionReportData ) {
		titlePanel.clear();
		valuePanel.clear();
		
		Label titleLabel = new Label( "Year" );
		Label valueLabel = new Label( transactionReportData.getIndex() + "" );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );
		
		for( TransactionReportData transactionReportDataChild : transactionReportData.getChildren() ) {
			titleLabel = new Label( transactionReportDataChild.getTitle() );
			valueLabel = new Label( transactionReportDataChild.getTotalAmount() + "" );
			titlePanel.add( titleLabel );
			valuePanel.add( valueLabel );
		}
		
		titleLabel = new Label( "Other" );
		valueLabel = new Label( transactionReportData.getAmount() + "" );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );

		titleLabel = new Label( "Total" );
		valueLabel = new Label( transactionReportData.getTotalAmount() + "" );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );
	}

	@Override
	public void setTitlesVisible(boolean visible) {
		this.titlePanel.setVisible( visible );
	}

}
