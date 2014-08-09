package tracker.module.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.module.transaction.client.views.TransactionItemView;
import tracker.module.transaction.client.views.TransactionItemViewImpl;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionItemListRequest;
import tracker.service.transaction.shared.GetTransactionItemListResponse;
import tracker.service.transaction.shared.data.TransactionItemData;

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

public class TransactionItemListModule extends Composite {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private Panel itemListPanel = new FlowPanel();
	private Panel footerPanel = new FlowPanel();

	private Anchor anchor = new Anchor( "Load More Transaction Items" );
	private Label label = new Label();
	
	private int pageSize = 10;
	private int firstPageSize = 15;
	private String transactionItemId = null;

	private Date transactionLoadedTillDate = null;
	
	public TransactionItemListModule() {
		Panel panel = new FlowPanel();

		anchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadTransactionItemList( transactionItemId, transactionLoadedTillDate, pageSize );				
			}
			
		});
		
		footerPanel.add( anchor );
		footerPanel.add( label );
		
		panel.add( itemListPanel );
		panel.add( footerPanel );

		initWidget( panel );
	}
	
	
	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}
	
	public void setFirstPageSize( int pageSize ) {
		this.firstPageSize = pageSize;
	}
	
	public void setTransactionItemId( String transactionItemId ) {
		this.transactionItemId = transactionItemId;
		this.itemListPanel.clear();
		loadTransactionItemList( this.transactionItemId, null, this.firstPageSize );
	}
	
	
	@Override
	protected void onLoad() {
		loadTransactionItemList( this.transactionItemId, null, this.firstPageSize );
	}
	
	private void loadTransactionItemList( String transactionItemId, Date startDate, final int pageSize ) {
		this.anchor.setEnabled( false );
		this.anchor.setVisible( false );
		
		this.label.setText( "Loading transaction items ..." );
		this.label.setVisible( true );
		
		GetTransactionItemListRequest request = new GetTransactionItemListRequest();
		if( transactionItemId != null )
			request.setTransactionItemTypeId( transactionItemId );
		if( startDate != null )
			request.setTransactionDateEnd( startDate, false );
		request.setTransactionDateChronologicalOrder( false );
		request.setPageSize( pageSize );
		
		transactionService.getTransactionItemList( request, new AsyncCallback<GetTransactionItemListResponse>() {

			@Override
			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub
				Window.alert( caught.getClass().getName() + " : " + caught.getMessage() );
			}

			@Override
			public void onSuccess( GetTransactionItemListResponse response ) {
				if( response.getTransactionItemDataList() != null ) {
					List<TransactionItemData> transactionItemDataList = response.getTransactionItemDataList();
					
					for( TransactionItemData transactionItemData: transactionItemDataList ) {
						TransactionItemView transactionItemView = new TransactionItemViewImpl();
						transactionItemView.setTransactionItemData( transactionItemData );
						itemListPanel.add( transactionItemView );
					}
					
					if( transactionItemDataList.size() != 0 )
						transactionLoadedTillDate = transactionItemDataList.get( transactionItemDataList.size() - 1 ).getTransactionDate();
					
					if( transactionItemDataList.size() < pageSize ) {
						label.setText( "No more transaction items !" );
					} else {
						label.setVisible( false );
						anchor.setEnabled( true );
						anchor.setVisible( true );
					}

				}
				
			}
			
		});
	}
	
}
