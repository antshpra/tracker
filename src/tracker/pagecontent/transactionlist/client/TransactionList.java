package tracker.pagecontent.transactionlist.client;

import java.util.Date;

import tracker.commons.client.TransactionView;
import tracker.commons.client.TransactionViewAccordionImpl;
import tracker.service.client.TrackerService;
import tracker.service.client.TrackerServiceAsync;
import tracker.service.shared.GetTransactionListRequest;
import tracker.service.shared.GetTransactionListResponse;
import tracker.service.shared.data.TransactionData;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TransactionList extends InfiniteScrollPanel {

	private static final TrackerServiceAsync transactionService =
			GWT.create( TrackerService.class );

	private int pageSize = 25;
	private Date cursor = new Date();


	@Override
	protected void loadItems() {
		
		GetTransactionListRequest request = new GetTransactionListRequest();
		request.setTransactionDateEnd( cursor, false );
		request.setTransactionDateChronologicalOrder( false );
		request.setCreationDateChronologicalOrder( false );
		request.setPageSize( pageSize );
		
		transactionService.getTransactionList( request, new AsyncCallback<GetTransactionListResponse>() {

			@Override
			public void onSuccess( GetTransactionListResponse resonse ) {

				for( TransactionData transactionData: resonse.getTransactionDataList() ) {
					TransactionView transactionView = new TransactionViewAccordionImpl();
					transactionView.setTransactionData( transactionData );
					add( transactionView );
					cursor = transactionData.getTransactionDate();
				}
				
				if( resonse.getTransactionDataList().size() < pageSize )
					loadFinished();
				loadSuccessful();
				
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.alert( caught.getClass().getName() + " : " + caught.getMessage() );
				loadFailed();
			}

		});
	
	}
		
}
