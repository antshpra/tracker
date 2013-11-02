package tracker.module.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TransactionList {
	
	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private Panel panel;
	private FlexTable table;
	private Panel subPanel;

	private int pageSize = 10;
	private int firstPageSize = 15;

	private Anchor anchor;
	private Label label;
	
	private Date transactionLoadedTillDate;
	
	public TransactionList() {
		this.panel = new VerticalPanel();
		
		this.table = new FlexTable();
		this.table.addStyleName( "table-style-1" );
		
		this.subPanel = new FlowPanel();
		this.anchor = new Anchor( "Load More Transactions" ); // TODO: I18n
		this.anchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadTransactionList( TransactionList.this.transactionLoadedTillDate, TransactionList.this.pageSize );				
			}
			
		});
		this.label = new Label();
		this.subPanel.add( this.anchor );		
		this.subPanel.add( this.label );
		
		this.panel.add( this.table );
		this.panel.add( this.subPanel );

		loadTransactionList( new Date(), this.firstPageSize );
	}
	
	public Widget getWidget() {
		return this.panel;
	}
	
	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}
	
	public void setFirstPageSize( int pageSize ) {
		this.firstPageSize = pageSize;
	}
	
	public void addTransaction( Transaction transaction ) {
		int rowNum = TransactionList.this.table.getRowCount();
		this.table.setWidget( rowNum, 0, transaction.getDateTimeWidget() );
		this.table.setWidget( rowNum, 1, transaction.getDetailWidget() );
		this.table.setWidget( rowNum, 2, transaction.getButtonsWidget() );
	}
	
	private void loadTransactionList( Date startDate, final int pageSize ) {
		this.anchor.setEnabled( false );
		this.anchor.setVisible( false );
		
		this.label.setText( "Loading transactions ..." );
		this.label.setVisible( true );
		
		GetTransactionListRequest request = new GetTransactionListRequest();
		request.setEndDate( startDate );
		request.setEndDateInclusive( false );
		request.setChronologicalOrder( false );
		request.setPageSize( pageSize );
		
		transactionService.getTransactionList( request, new AsyncCallback<GetTransactionListResponse>() {

			@Override
			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub
				Window.alert( caught.getClass().getName() + " : " + caught.getMessage() );
			}

			@Override
			public void onSuccess( GetTransactionListResponse result ) {
				if( result.getTransactionDataList() != null ) {
					List<TransactionData> transactionDataList = result.getTransactionDataList();
					
					for( TransactionData transactionData: transactionDataList )
						TransactionList.this.addTransaction( new Transaction( transactionData ) );
					
					if( transactionDataList.size() != 0 ) {
						TransactionList.this.transactionLoadedTillDate = transactionDataList.get( transactionDataList.size() - 1 ).getCreationDate();
					}
					
					if( transactionDataList.size() < pageSize ) {
						TransactionList.this.label.setText( "No more transaction !" );
					} else {
						TransactionList.this.label.setVisible( false );
						TransactionList.this.anchor.setEnabled( true );
						TransactionList.this.anchor.setVisible( true );
					}

				}
				
			}
			
		});
	}
	
}
