package tracker.module.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Transaction implements EntryPoint {
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final TransactionServiceAsync transactionService = GWT.create(TransactionService.class);

	private FlexTable transactionsTable = new FlexTable();
	private Panel transactionsSubPanel = new SimplePanel();
	
	private Date lastTransactionDate = new Date();
	private int transactionsPerRequestCount = 10;

	private Label loadingTransactionsLabel = new Label( "Loading Transactions ..." );
	private Label noMoreTransactionsLabel = new Label( "No More Transactions !" );
	private Anchor loadMoreTransactionsAnchor = new Anchor( "Load More Transactions" ); 
	
	
	public void onModuleLoad() {
		
		final DatePicker datePicker = new DatePicker();
		
		ListBox yearList = new ListBox();
		for( int i = 2000; i <= 2025; i++ )
			yearList.addItem( Integer.toString( i ) );

		ListBox monthList = new ListBox();
		for( int i = 0; i <= 12; i++ )
			monthList.addItem( Integer.toString( i ) );
		
		
		
		final TextBox descriptionInput = new TextBox();
		Button saveButton = new Button( "Save" );
		
		saveButton.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				CreateTransactionRequest request = new CreateTransactionRequest();
				
				try {
					request.setDescription( descriptionInput.getText() );
					request.setTransactionDate( datePicker.getValue() );
				} catch( IllegalArgumentException ex ) {
					Window.alert( ex.getMessage() );
				}
				
				transactionService.createTransaction( request, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(String result) {
						Window.alert( result + "" );
					}
					
				});
				
			}
		});
		
		RootPanel.get().add( datePicker );		
		RootPanel.get().add( descriptionInput );
		RootPanel.get().add( saveButton );
		
		
		
		loadMoreTransactionsAnchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadTransactions( Transaction.this.lastTransactionDate, Transaction.this.transactionsPerRequestCount );				
			}
			
		});
		
		transactionsTable.addStyleName( "table-style-1" );
		
		RootPanel.get().add( this.transactionsTable );
		RootPanel.get().add( this.transactionsSubPanel );

		loadTransactions( new Date(), 15 );
		
	}
	
	private void loadTransactions( final Date olderThan, final int count ) {
		
		this.transactionsSubPanel.clear();
		this.transactionsSubPanel.add( this.loadingTransactionsLabel );
		
		transactionService.getTransactions( olderThan, count,
				
				new AsyncCallback<List<GetTransactionsResponse>>() {
			
					public void onFailure( Throwable caught ) {
						// Show the RPC error message to the user
//						dialogBox
//								.setText("Remote Procedure Call - Failure");
//						serverResponseLabel
//								.addStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(SERVER_ERROR);
//						dialogBox.center();
//						closeButton.setFocus(true);
//						
//						ERROR MESSAGE + RETRY MESAGE
					}
					
					public void onSuccess( List<GetTransactionsResponse> transactionDetailList ) {
						
						for( GetTransactionsResponse transactionDetail : transactionDetailList ) {
							int rowNum = Transaction.this.transactionsTable.getRowCount();
							
							final HTML dateTimeHTML = new HTML( DateUtil.getDay( transactionDetail.getCreationDate() ) + "<br/>" + DateUtil.getTime( transactionDetail.getCreationDate() ) );
							final HTML descriptionHTML = new HTML( transactionDetail.getDescription() + "<br/>added by " + transactionDetail.getCreatedBy() );
							
							MouseOverHandler mouseOverHandler = new MouseOverHandler() {
								@Override
								public void onMouseOver( MouseOverEvent event ) {
									dateTimeHTML.addStyleName( "highlighted" );
									descriptionHTML.addStyleName( "highlighted" );
								}
							};

							MouseOutHandler mouseOutHandler = new MouseOutHandler() {
								@Override
								public void onMouseOut( MouseOutEvent event ) {
									dateTimeHTML.removeStyleName( "highlighted" );
									descriptionHTML.removeStyleName( "highlighted" );
								}
							};
							
							final String transId = transactionDetail.getId();
							MouseUpHandler mouseUpHandler = new MouseUpHandler() {
								@Override
								public void onMouseUp( MouseUpEvent event ) {
									Window.alert( transId + "" );
								}
							};
							
							dateTimeHTML.addMouseOverHandler( mouseOverHandler );
							descriptionHTML.addMouseOverHandler( mouseOverHandler );
							
							dateTimeHTML.addMouseOutHandler( mouseOutHandler );
							descriptionHTML.addMouseOutHandler( mouseOutHandler );
							
							dateTimeHTML.addMouseUpHandler( mouseUpHandler );
							descriptionHTML.addMouseUpHandler( mouseUpHandler );
							
							Transaction.this.transactionsTable.setWidget( rowNum, 0, dateTimeHTML );
							Transaction.this.transactionsTable.setWidget( rowNum, 1, descriptionHTML );
						}
						
						if( transactionDetailList.size() != 0 ) {
							Transaction.this.lastTransactionDate = transactionDetailList.get( transactionDetailList.size() - 1 ).getCreationDate();
						}
						
						if( transactionDetailList.size() < count ) {
							Transaction.this.transactionsSubPanel.clear();
							Transaction.this.transactionsSubPanel.add( Transaction.this.noMoreTransactionsLabel );
						} else {
							Transaction.this.transactionsSubPanel.clear();
							Transaction.this.transactionsSubPanel.add( Transaction.this.loadMoreTransactionsAnchor );
						}
					}
					
				});

	}
	
}
