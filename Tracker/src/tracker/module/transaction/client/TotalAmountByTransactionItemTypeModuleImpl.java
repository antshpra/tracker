package tracker.module.transaction.client;

import java.util.List;

import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionItemTypeListRequest;
import tracker.service.transaction.shared.GetTransactionItemTypeListResponse;
import tracker.service.transaction.shared.TransactionItemTypeData;
import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.client.TransactionReportServiceAsync;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class TotalAmountByTransactionItemTypeModuleImpl extends Composite {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );
	private static final TransactionReportServiceAsync transactionReportService = GWT.create( TransactionReportService.class );

	private Panel panel = new FlowPanel();

	public TotalAmountByTransactionItemTypeModuleImpl() {
		
		initWidget( panel );

		transactionService.getTransactionItemTypeList(
				new GetTransactionItemTypeListRequest(),
				new AsyncCallback<GetTransactionItemTypeListResponse>() {
			
			@Override
			public void onFailure( Throwable caught ) {
				// TODO: Auto-generated method stub
				Window.alert( caught.getClass().getName() );
			}

			@Override
			public void onSuccess( GetTransactionItemTypeListResponse result ) {
				proceed( result.getTransactionItemTypeDataList() );
			}
			
		});
		
	}
	
	private void proceed( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		
		for( final TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
			final Label label = new Label( transactionItemTypeData.getTitle() + " - Loading ..." );
			panel.add( label );
			
			GetTotalAmountByTransactionItemTypeRequest request = new GetTotalAmountByTransactionItemTypeRequest();
			request.settransactionItemTypeId( transactionItemTypeData.getId() );
			transactionReportService.getTotalAmountByTransactionItemType( request, new AsyncCallback<GetTotalAmountByTransactionItemTypeResponse>() {

				@Override
				public void onFailure( Throwable caught ) {
					// TODO: Auto-generated method stub
					Window.alert( caught.getClass().getName() );
				}

				@Override
				public void onSuccess( GetTotalAmountByTransactionItemTypeResponse result ) {
					label.setText( transactionItemTypeData.getTitle() + " : Rs. " + result.getAmount() );
				}
			
			});
			
			proceed( transactionItemTypeData.getTitle() + " : ", transactionItemTypeData.getChildren() );
		}
		
	}
	
	private void proceed( final String prefix, List<TransactionItemTypeData> transactionItemTypeDataList ) {

		for( final TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
			final Label label = new Label( prefix + transactionItemTypeData.getTitle() + " - Loading ..." );
			panel.add( label );
			
			GetTotalAmountByTransactionItemTypeRequest request = new GetTotalAmountByTransactionItemTypeRequest();
			request.settransactionItemTypeId( transactionItemTypeData.getId() );
			transactionReportService.getTotalAmountByTransactionItemType( request, new AsyncCallback<GetTotalAmountByTransactionItemTypeResponse>() {

				@Override
				public void onFailure( Throwable caught ) {
					// TODO: Auto-generated method stub
					Window.alert( caught.getClass().getName() );
				}

				@Override
				public void onSuccess( GetTotalAmountByTransactionItemTypeResponse result ) {
					label.setText( prefix + transactionItemTypeData.getTitle() + " - Rs. " + result.getAmount() );
				}
			
			});
			
			proceed( prefix + transactionItemTypeData.getTitle() + " : ", transactionItemTypeData.getChildren() );
		}

	}

}
