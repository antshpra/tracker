package tracker.module.transactionreport.client;

import tracker.service.transactionreport.shared.TransactionReportData;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class MonthlyReportModuleImpl extends MonthlyReportModule {

	private Panel panel;
	
	public MonthlyReportModuleImpl() {
		panel = new SimplePanel();
		initWidget( panel );
	}
	
	@Override
	public void setTransactionReportData( TransactionReportData transactionReportData ) {
		panel.clear();

		Grid dataGrid = new Grid( 13, transactionReportData.getChildren().size() + 2 );
		
		int column = 0;
		
		for( TransactionReportData transactionReportDataChild : transactionReportData.getChildren() )
			dataGrid.setWidget( 0, column++, new Label( transactionReportDataChild.getTitle() ) );
		
		dataGrid.setWidget( 0, column++, new Label( "Other" ) );
		dataGrid.setWidget( 0, column++, new Label( "Total" ) );
		
		for( int row = 1; row < 13; row++ ) {
			column = 0;
			
			for( TransactionReportData transactionReportDataChild : transactionReportData.getChildren() )
				dataGrid.setWidget( row, column++, new Label( transactionReportDataChild.getTotalAmount( row - 1 ) + "" ) );
			
			dataGrid.setWidget( row, column++, new Label( transactionReportData.getAmount( row - 1 ) + "" ) );
			dataGrid.setWidget( row, column++, new Label( transactionReportData.getTotalAmount( row - 1 ) + "" ) );
		}
		
		panel.add( dataGrid );
	}

}
