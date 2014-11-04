package tracker.pagecontent.transactionlist.client;

import tracker.commons.shared.TransactionReportUtil;
import tracker.service.transactionreport.shared.data.TransactionReportData;
import tracker.theme.client.ThemeFactory;
import tracker.theme.client.TransactionReportModuleStyle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class MonthlyReportViewImpl extends MonthlyReportView {

	private TransactionReportModuleStyle style = ThemeFactory.getTheme().getTransactionReportModuleStyle();

	private Panel titlePanel = new FlowPanel();
	private Panel valuePanel = new FlowPanel();
	
	
	public MonthlyReportViewImpl() {
		Panel panel = new FlowPanel();
		panel.add( titlePanel );
		panel.add( valuePanel );

		panel.addStyleName( style.trmMonthlyReportView() );

		initWidget( panel );
	}

	
	@Override
	public void setTransactionReportData( TransactionReportData transactionReportData ) {
		titlePanel.clear();
		valuePanel.clear();
		
		Label titleLabel = new Label();
		Label valueLabel = new Label( getMonth( TransactionReportUtil.getTransactionReportMonth( transactionReportData.getIndex() ) ) );
		titleLabel.addStyleName( style.title() );
		valueLabel.addStyleName( style.index() );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );
		
		for( TransactionReportData transactionReportDataChild : transactionReportData.getChildren() ) {
			titleLabel = new Label( transactionReportDataChild.getTitle() );
			valueLabel = new Label( transactionReportDataChild.getTotalAmount() + "" );
			titleLabel.addStyleName( style.title() );
			valueLabel.addStyleName( style.value() );
			titlePanel.add( titleLabel );
			valuePanel.add( valueLabel );
		}
		
		titleLabel = new Label( "Other" );
		valueLabel = new Label( transactionReportData.getAmount() + "" );
		titleLabel.addStyleName( style.title() );
		valueLabel.addStyleName( style.value() );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );

		titleLabel = new Label( "Total" );
		valueLabel = new Label( transactionReportData.getTotalAmount() + "" );
		titleLabel.addStyleName( style.title() );
		valueLabel.addStyleName( style.value() );
		titlePanel.add( titleLabel );
		valuePanel.add( valueLabel );
	}

	private String getMonth( int index ) {
		switch( index ) {
			case 0: return "January";
			case 1: return "February";
			case 2: return "March";
			case 3: return "April";
			case 4: return "May";
			case 5: return "June";
			case 6: return "July";
			case 7: return "August";
			case 8: return "September";
			case 9: return "October";
			case 10: return "November";
			case 11: return "December";
			default: return "Unknown";
		}
	}
	
	@Override
	public void setTitlesVisible(boolean visible) {
		this.titlePanel.setVisible( visible );
	}

}
