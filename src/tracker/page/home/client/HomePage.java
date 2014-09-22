package tracker.page.home.client;

import java.util.List;

import tracker.commons.shared.YearType;
import tracker.module.transaction.client.TransactionItemListModule;
import tracker.module.transactionreport.client.ReportModule;
import tracker.module.transactionreport.client.ReportModuleImpl;
import tracker.service.client.TrackerService;
import tracker.service.client.TrackerServiceAsync;
import tracker.service.shared.GetTransactionItemTypeListRequest;
import tracker.service.shared.GetTransactionItemTypeListResponse;
import tracker.service.shared.data.TransactionItemTypeData;
import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.client.TransactionReportServiceAsync;
import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.theme.client.ThemeFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class HomePage implements EntryPoint {

	private static final TrackerServiceAsync transactionService = GWT.create( TrackerService.class );
	private static final TransactionReportServiceAsync transactionReportService = GWT.create( TransactionReportService.class );

	@Override
	public void onModuleLoad() {
		final ReportModule reportModule = new ReportModuleImpl();
		final TransactionItemListModule transactionItemListModule = new TransactionItemListModule();

		final ListBox itemTypeList = new ListBox();
		itemTypeList.addItem( "Loading ..." ); // I18n
		
		transactionService.getTransactionItemTypeList(
				new GetTransactionItemTypeListRequest(),
				new AsyncCallback<GetTransactionItemTypeListResponse>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert( caught.getClass().getName() );
			}

			@Override
			public void onSuccess( GetTransactionItemTypeListResponse response ) {
				itemTypeList.clear();
				itemTypeList.addItem( "-- Select Transaction Item Type --" ); // I18n
				setTransactionItemTypeDataList( response.getTransactionItemTypeDataList() );
			}
			
			private void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
				for( TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
					if( transactionItemTypeData.getParent() == null || transactionItemTypeData.getChildren().size() > 0)
						itemTypeList.addItem( transactionItemTypeData.getQualifiedTitle(), transactionItemTypeData.getId() );
					setTransactionItemTypeDataList( transactionItemTypeData.getChildren() );
				}
			}
			
		});
		
		itemTypeList.addChangeHandler( new ChangeHandler() {
			
			public void onChange( ChangeEvent event ) {
				String itemTypeListSelectedValue = itemTypeList.getValue( itemTypeList.getSelectedIndex() );
				if( itemTypeListSelectedValue != null ) {
					GetMonthlyReportRequest request = new GetMonthlyReportRequest();
					request.setTransactionItemTypeId( itemTypeListSelectedValue );
					request.setYear( 2014 );
					request.setYearType( YearType.FINANCIAL );
					transactionReportService.getMonthlyReport(request, new AsyncCallback<GetMonthlyReportResponse>() {
						
						@Override
						public void onFailure( Throwable caught ) {
							// TODO: Auto-generated method stub
							Window.alert( caught.getClass().getName() );
						}
			
						@Override
						public void onSuccess( GetMonthlyReportResponse response ) {
							reportModule.setTransactionReportDataList( response.getTransactionReportDataList() );
						}

					});
					
					transactionItemListModule.setTransactionItemId( itemTypeList.getValue( itemTypeList.getSelectedIndex() ) );;
				}
			}
		});
		
		RootPanel.get().add( itemTypeList );
		RootPanel.get().add( reportModule );
		RootPanel.get().add( transactionItemListModule );
		
		ThemeFactory.getTheme().getTransactionModuleStyle().ensureInjected();
		ThemeFactory.getTheme().getTransactionReportModuleStyle().ensureInjected();
	}
		
}
