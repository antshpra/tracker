package tracker.pagecontent.transactionlist.client;

import tracker.commons.client.EditTransactionView;
import tracker.commons.client.EditTransactionViewModalImpl;
import tracker.service.client.TrackerService;
import tracker.service.client.TrackerServiceAsync;
import tracker.service.shared.SaveTransactionRequest;
import tracker.service.shared.CreateTransactionResponse;
import tracker.service.shared.GetTransactionItemTypeListRequest;
import tracker.service.shared.GetTransactionItemTypeListResponse;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class TransactionListContent implements EntryPoint, ClickHandler {

	private static final TrackerServiceAsync transactionService =
			GWT.create( TrackerService.class );

	private final Button newTransactionButton = new Button();
	private TransactionList trList;
	private EditTransactionView trInputView;
	
	
	public void onModuleLoad() {
		
		newTransactionButton.setText( "New Transaction" );
		newTransactionButton.setStyleName( "btn btn-primary" );
		newTransactionButton.addClickHandler( this );
		
		GetTransactionItemTypeListRequest getTransactionItemTypeListRequest = new GetTransactionItemTypeListRequest();
		transactionService.getTransactionItemTypeList(
				getTransactionItemTypeListRequest,
				new AsyncCallback<GetTransactionItemTypeListResponse>() {

			@Override
			public void onSuccess( GetTransactionItemTypeListResponse result ) {
				trInputView = new EditTransactionViewModalImpl( result.getTransactionItemTypeDataList() );
				trInputView.addSaveButtonClickHandler( TransactionListContent.this );
				trList = new TransactionList( trInputView );

				RootPanel rootPanel = RootPanel.get( "PageContent-TransactionList" );
				rootPanel.add( newTransactionButton );
				rootPanel.add( trList );
				rootPanel.add( trInputView );
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.alert( caught.getClass().getName() );
			}

		} );
		
	}
	
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == newTransactionButton ) {
			trInputView.setVisible( true );
			
		} else { // TransactionInputView.saveButton
			
			if( trInputView.validateInputs() ) {
				trInputView.setEnabled( false );
				transactionService.saveTransaction(
					new SaveTransactionRequest( trInputView.getTransactionData() ),
					new AsyncCallback<CreateTransactionResponse>() {

						@Override
						public void onSuccess( CreateTransactionResponse response ) {
							trInputView.setVisible( false );
							trInputView.setEnabled( true );
						}

						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
							trInputView.setEnabled( true );
						}

				});
			}
		}
		
	}
		
}
