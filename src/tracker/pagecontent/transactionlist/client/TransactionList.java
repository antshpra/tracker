package tracker.pagecontent.transactionlist.client;

import tracker.commons.client.EditTransactionView;
import tracker.commons.client.TransactionView;
import tracker.commons.client.TransactionViewAccordionImpl;
import tracker.commons.shared.TransactionFilter;
import tracker.service.client.TrackerServiceAsync;
import tracker.service.shared.GetTransactionListRequest;
import tracker.service.shared.GetTransactionListResponse;
import tracker.service.shared.data.TransactionData;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

public class TransactionList extends Composite {

	private int resultCount = 25;
	private String cursor = null;

	
	public TransactionList(
			final EditTransactionView editTrView,
			final TrackerServiceAsync transactionService ) {
		
		TransactionFilter trFilter = new TransactionFilter();
		trFilter.setTransactionDateChronologicalOrder( false );
		trFilter.setCreationDateChronologicalOrder( false );
		
		final GetTransactionListRequest request = new GetTransactionListRequest();
		request.setTransactionFilter( trFilter );
		request.setResultCount( resultCount );
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				request.setCursor( cursor );
				transactionService.getTransactionList(
						request,
						new AsyncCallback<GetTransactionListResponse>() {

								@Override
								public void onSuccess( GetTransactionListResponse resonse ) {
					
									for( TransactionData trData : resonse.getTransactionDataList() ) {
										TransactionView trView = new TransactionViewAccordionImpl();
										trView.setTransactionData( trData );
										trView.setEditTransactionView( editTrView );
										add( trView );
									}

									cursor = resonse.getCursor();
									if( cursor == null )
										loadFinished();
									loadSuccessful();
						
								}
					
								@Override
								public void onFailure( Throwable caught ) {
									loadFailed();
								}

						});
			
			}
		};
		
		initWidget( infiniteScrollPanel );
	}
	
}
