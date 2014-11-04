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

	private final InfiniteScrollPanel infiniteScrollPanel;
	private TransactionFilter trFilter = null;
	private String cursor = null;
	private int resultCount = 25;

	
	public TransactionList(
			final EditTransactionView editTrView,
			final TrackerServiceAsync transactionService ) {
		
		trFilter = new TransactionFilter();
		trFilter.setTransactionDateChronologicalOrder( false );
		trFilter.setCreationDateChronologicalOrder( false );


		infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				final GetTransactionListRequest request = new GetTransactionListRequest();
				request.setTransactionFilter( trFilter );
				request.setCursor( cursor );
				request.setResultCount( resultCount );
				transactionService.getTransactionList(
						request,
						new AsyncCallback<GetTransactionListResponse>() {

								@Override
								public void onSuccess( GetTransactionListResponse resonse ) {
									
									if( request.getTransactionFilter() != trFilter )
										return;
					
									for( TransactionData trData : resonse.getTransactionDataList() ) {
										TransactionView trView = new TransactionViewAccordionImpl();
										trView.setTransactionData( trData );
										trView.setEditTransactionView( editTrView );
										add( trView );
									}

									cursor = resonse.getCursor();
									if( resonse.getTransactionDataList().size() == 0 )
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
	
	
	public void setTransactionFilter( TransactionFilter trFilter ) {
		this.trFilter = trFilter;
		cursor = null;
		infiniteScrollPanel.reset();
	}
	
}
