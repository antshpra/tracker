package tracker.pagecontent.transactionlist.client;

import java.util.Date;

import tracker.commons.client.TransactionView;
import tracker.commons.client.TransactionViewAccordionImpl;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import tracker.service.transaction.shared.data.TransactionData;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TransactionList extends InfiniteScrollPanel {

	private static final TransactionServiceAsync transactionService =
			GWT.create( TransactionService.class );

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
				
				loadSuccessful();
				if( resonse.getTransactionDataList().size() < pageSize )
					loadFinished();
				
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.alert( caught.getClass().getName() + " : " + caught.getMessage() );
				loadFailed();
			}

		});
	
	}
		
}
