package tracker.pagecontent.transactionlist.client;

import java.util.Date;

import tracker.commons.client.TransactionView;
import tracker.commons.client.TransactionViewImpl;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.data.TransactionData;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class TransactionListContent implements EntryPoint {

	private static final TransactionServiceAsync transactionService =
			GWT.create( TransactionService.class );

	private int pageSize = 25;
	private Date transactionLoadedTillDate = new Date();
	

	public void onModuleLoad() {

		RootPanel.get( "PageContent-TransactionList" ).add( new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				
				GetTransactionListRequest request = new GetTransactionListRequest();
				request.setTransactionDateEnd( transactionLoadedTillDate, false );
				request.setTransactionDateChronologicalOrder( false );
				request.setCreationDateChronologicalOrder( false );
				request.setPageSize( pageSize );
				
				transactionService.getTransactionList( request, new AsyncCallback<GetTransactionListResponse>() {

					@Override
					public void onFailure( Throwable caught ) {
						// TODO Auto-generated method stub
						Window.alert( caught.getClass().getName() + " : " + caught.getMessage() );
						
						loadFailed();
					}

					@Override
					public void onSuccess( GetTransactionListResponse result ) {

						for( TransactionData transactionData: result.getTransactionDataList() ) {
							TransactionView transactionView = new TransactionViewImpl();
							transactionView.setTransactionData( transactionData );
							add( transactionView );
							transactionLoadedTillDate = transactionData.getTransactionDate();
						}
						
						loadSuccessful();
					}
					
				});
			}
			
		} );
	
	}
	
}
