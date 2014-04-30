package tracker.page.home.client;

import tracker.module.transaction.client.EditTransactionModuleImpl;
import tracker.module.transaction.client.TransactionList;
import tracker.module.transaction.client.TransactionListLoader;
import tracker.module.transaction.client.resources.TransactionResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class HomePage implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add( new EditTransactionModuleImpl() );
		RootPanel.get().add( new TransactionListLoader( new TransactionList() ) );
		TransactionResources.INSTANCE.css().ensureInjected();
	}
		
}
