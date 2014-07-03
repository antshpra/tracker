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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class TransactionListLoader extends Composite {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private Panel panel = new FlowPanel();
	private TransactionList transactionList;
	private Panel subPanel = new FlowPanel();

	private int pageSize = 10;
	private int firstPageSize = 15;

	private Anchor anchor;
	private Label label;
	
	private Date transactionLoadedTillDate;
	
	public TransactionListLoader( TransactionList transactionList ) {
		this.transactionList = transactionList;
		
		this.anchor = new Anchor( "Load More Transactions" );
		this.anchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadTransactionList( TransactionListLoader.this.transactionLoadedTillDate, TransactionListLoader.this.pageSize );				
			}
			
		});
		this.label = new Label();
		this.subPanel.add( this.anchor );		
		this.subPanel.add( this.label );
		
		this.panel.add( this.transactionList );
		this.panel.add( this.subPanel );

		initWidget( panel );
		
		loadTransactionList( new Date(), this.firstPageSize );
	}
	
	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}
	
	public void setFirstPageSize( int pageSize ) {
		this.firstPageSize = pageSize;
	}
	
	private void loadTransactionList( Date startDate, final int pageSize ) {
		this.anchor.setEnabled( false );
		this.anchor.setVisible( false );
		
		this.label.setText( "Loading transactions ..." );
		this.label.setVisible( true );
		
		GetTransactionListRequest request = new GetTransactionListRequest();
		request.setTransactionDateEnd( startDate, false );
		request.setTransactionDateChronologicalOrder( false );
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
						TransactionListLoader.this.transactionList.add( transactionData );
					
					if( transactionDataList.size() != 0 )
						TransactionListLoader.this.transactionLoadedTillDate = transactionDataList.get( transactionDataList.size() - 1 ).getTransactionDate();
					
					if( transactionDataList.size() < pageSize ) {
						TransactionListLoader.this.label.setText( "No more transaction !" );
					} else {
						TransactionListLoader.this.label.setVisible( false );
						TransactionListLoader.this.anchor.setEnabled( true );
						TransactionListLoader.this.anchor.setVisible( true );
					}

				}
				
			}
			
		});
	}
	
}
