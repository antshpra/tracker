package tracker.module.transaction.client;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;
import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class EditTransaction extends Composite implements ClickHandler {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private String transactionId = null;
	private List<CreateTransactionItemRequest> createTransactionItemRequestList = new LinkedList<CreateTransactionItemRequest>();
	
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd MMM yyyy"); // TODO: I18n
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy h:mm a"); // TODO: I18n
	
	private DateTimePicker dateTimePicker = new DateTimePicker();
	private Label descriptionLabel = new Label( "Description" ); // TODO: I18n
	private TextBox descriptionInput = new TextBox();

	private Panel itemsPanel = new FlowPanel();
	
	private DateTimePicker itemDateTimePicker = new DateTimePicker();
	private Label itemDescriptionLabel = new Label( "Description" ); // TODO: I18n
	private TextBox itemDescriptionInput = new TextBox();
	private Button addItemButton = new Button( "Add" );
	
	private Button saveButton = new Button( "Save" ); // TODO: I18n
	
	public EditTransaction() {
		addItemButton.addClickHandler( this );
		saveButton.addClickHandler( this );

		FlowPanel panel = new FlowPanel();
		panel.add( dateTimePicker );
		panel.add( descriptionLabel );
		panel.add( descriptionInput );
		itemsPanel.addStyleName( "transactionItemList" );
		panel.add( itemsPanel );
		panel.add( itemDateTimePicker );
		panel.add( itemDescriptionLabel );
		panel.add( itemDescriptionInput );
		panel.add( addItemButton );
		panel.add( saveButton );
		
		initWidget( panel );
		setStyleName( "transactionEdit" );
	}

	public EditTransaction( String transactionId ) {
		this();
		this.transactionId = transactionId;
		loadTransactionData( transactionId );
	}
	
	private void setEnabled( boolean enabled ) {
		descriptionInput.setEnabled( enabled );
		saveButton.setEnabled( enabled );
	}
	
	private void loadTransactionData( String transactionId ) {
		setEnabled( false );
		
		GetTransactionRequest request = new GetTransactionRequest();
		request.setTransactionId( transactionId );
		
		transactionService.getTransaction( request, new AsyncCallback<GetTransactionResponse>() {

			@Override
			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub
				Window.alert( caught.getClass().getName() );
			}

			@Override
			public void onSuccess( GetTransactionResponse result ) {
				TransactionData transactionData = result.getTransactionData();
				
				EditTransaction.this.descriptionInput.setText( transactionData.getDescription() );
				
				EditTransaction.this.setEnabled( true );
			}
			
		} );
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( event.getSource() == addItemButton ) {
			CreateTransactionItemRequest itemRequest = new CreateTransactionItemRequest();
			itemRequest.setTransactionDate( itemDateTimePicker.getDate() );
			itemRequest.setDescription( itemDescriptionInput.getText() );
			createTransactionItemRequestList.add( itemRequest );
			
			itemsPanel.add( 
					new Label(
							dateTimeFormat.format( itemDateTimePicker.getDate() )
							+ " # "
							+ itemDescriptionInput.getText() ) );
			itemDescriptionInput.setText( "" );
			
		} else if( event.getSource() == saveButton ) {
			setEnabled( false );
			
			CreateTransactionRequest request = new CreateTransactionRequest();
			request.setTransactionDate( dateTimePicker.getDate() );
			request.setDescription( descriptionInput.getText() );
			for( CreateTransactionItemRequest itemRequest : createTransactionItemRequestList )
				request.addCreateTransactionItemRequest( itemRequest );
			
			transactionService.createTransaction( request, new AsyncCallback<String>() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					Window.alert( "Transaction created with id " + result );
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert( caught.getClass().getName() );
				}
				
			});
		}
		
	}
	
}
