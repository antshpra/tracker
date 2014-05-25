package tracker.module.transaction.client;

import java.util.Iterator;
import java.util.List;

import tracker.module.transaction.client.views.TransactionInputView;
import tracker.module.transaction.client.views.TransactionInputViewImpl;
import tracker.module.transaction.client.views.TransactionItemInputView;
import tracker.module.transaction.client.views.TransactionItemInputViewImpl;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.CreateTransactionResponse;
import tracker.service.transaction.shared.GetTransactionItemTypeListRequest;
import tracker.service.transaction.shared.GetTransactionItemTypeListResponse;
import tracker.service.transaction.shared.TransactionItemTypeData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class EditTransactionModuleImpl extends EditTransactionModule {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private List<TransactionItemTypeData> transactionItemTypeDataList;
	
	private TransactionInputView transactionInputView = new TransactionInputViewImpl(); 
	private Panel transactionItemInputViewsPanel = new FlowPanel();
	
	private Anchor addTransactionItemAnchor = new Anchor( "Add Item" );
	private Button saveTransactionButton = new Button( "Save Transaction" );

	public EditTransactionModuleImpl() {
		addTransactionItemAnchor.addClickHandler( this );
		saveTransactionButton.addClickHandler( this );

		transactionItemInputViewsPanel.add( new TransactionItemInputViewImpl() );
		
		Panel panel = new FlowPanel();
		panel.add( transactionInputView );
		panel.add( transactionItemInputViewsPanel );
		panel.add( addTransactionItemAnchor );
		panel.add( saveTransactionButton );
		
		initWidget( panel );
		
		GetTransactionItemTypeListRequest getTransactionItemTypeListRequest = new GetTransactionItemTypeListRequest();
		transactionService.getTransactionItemTypeList(
				getTransactionItemTypeListRequest,
				new AsyncCallback<GetTransactionItemTypeListResponse>() {

			@Override
			public void onFailure( Throwable caught ) {
				// TODO: Auto-generated method stub
				Window.alert( caught.getClass().getName() );
			}

			@Override
			public void onSuccess( GetTransactionItemTypeListResponse result ) {
				transactionItemTypeDataList = result.getTransactionItemTypeDataList();
				Iterator<Widget> transactionItemInputViewsIterator = transactionItemInputViewsPanel.iterator();
				while( transactionItemInputViewsIterator.hasNext() )
					( (TransactionItemInputView) transactionItemInputViewsIterator.next() )
							.setTransactionItemTypeDataList( transactionItemTypeDataList );
			}

		} );

	}
	
	@Override
	public void onClick(ClickEvent event) {
		if( event.getSource() == addTransactionItemAnchor ) {
			TransactionItemInputView transactionItemInputView = new TransactionItemInputViewImpl();
			transactionItemInputViewsPanel.add( transactionItemInputView );
			transactionItemInputView.setTransactionItemTypeDataList( transactionItemTypeDataList );			
			
		} else if( event.getSource() == saveTransactionButton ) {
			boolean validateInputs = transactionInputView.validateInputs();

			Iterator<Widget> transactionItemInputViewsIterator = transactionItemInputViewsPanel.iterator();
			while( transactionItemInputViewsIterator.hasNext() )
				validateInputs = 
						( (TransactionItemInputView) transactionItemInputViewsIterator.next() ).validateInputs()
						&& validateInputs;
			
			if( validateInputs == false ) 
				return;
			
			CreateTransactionRequest createTransactionRequest = transactionInputView.getCreateTransactionRequest();

			transactionItemInputViewsIterator = transactionItemInputViewsPanel.iterator();
			while( transactionItemInputViewsIterator.hasNext() )
				createTransactionRequest.addCreateTransactionItemRequest(
						( (TransactionItemInputView) transactionItemInputViewsIterator.next() )
								.getCreateTransactionItemRequest() );
			
			transactionService.createTransaction( createTransactionRequest, new AsyncCallback<CreateTransactionResponse>() {
				
				@Override
				public void onSuccess(CreateTransactionResponse result) {
					// TODO Auto-generated method stub
					Window.alert( "Transaction created with id " + result.getTransactionId() );
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert( caught.getClass().getName() );
				}
				
			});
			
		}
	}

}
