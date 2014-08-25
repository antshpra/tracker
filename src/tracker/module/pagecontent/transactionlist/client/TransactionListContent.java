package tracker.module.pagecontent.transactionlist.client;

import tracker.module.transaction.client.TransactionList;
import tracker.module.transaction.client.TransactionListLoader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class TransactionListContent implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get( "PageContent-TransactionList" )
				.add( new TransactionListLoader( new TransactionList() ) );
	}

}
