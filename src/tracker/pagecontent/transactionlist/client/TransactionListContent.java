package tracker.pagecontent.transactionlist.client;

import tracker.commons.client.TransactionInputView;
import tracker.commons.client.TransactionInputViewModalImpl;
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

	private final TransactionList trList = new TransactionList();
	private final Button newTransactionButton = new Button();
	private TransactionInputView trInputView;
	
	
	public void onModuleLoad() {
		
		newTransactionButton.setText( "New Transaction" );
		newTransactionButton.setStyleName( "btn btn-primary" );
		newTransactionButton.addClickHandler( this );
		newTransactionButton.setVisible( false );
		
		final RootPanel rootPanel = RootPanel.get( "PageContent-TransactionList" );
		rootPanel.add( newTransactionButton );
		rootPanel.add( trList );

		GetTransactionItemTypeListRequest getTransactionItemTypeListRequest = new GetTransactionItemTypeListRequest();
		transactionService.getTransactionItemTypeList(
				getTransactionItemTypeListRequest,
				new AsyncCallback<GetTransactionItemTypeListResponse>() {

			@Override
			public void onSuccess( GetTransactionItemTypeListResponse result ) {
				trInputView = new TransactionInputViewModalImpl( result.getTransactionItemTypeDataList() );
				trInputView.addSaveButtonClickHandler( TransactionListContent.this );
				rootPanel.add( trInputView );
				newTransactionButton.setVisible( true );
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
								Window.alert( "Tr. id - " + response.getTransactionId() );
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
