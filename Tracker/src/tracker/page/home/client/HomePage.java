package tracker.page.home.client;

import tracker.module.transaction.client.EditTransaction;
import tracker.module.transaction.client.TransactionList;
import tracker.module.transaction.client.TransactionListLoader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class HomePage implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add( new EditTransaction() );
		RootPanel.get().add( new TransactionListLoader( new TransactionList() ) );
	}

}
