package tracker.module.transaction.client;

import tracker.commons.client.TransactionView;
import tracker.commons.client.TransactionViewImpl;
import tracker.service.transaction.shared.data.TransactionData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionList extends Composite {

	private Panel panel = new FlowPanel();

	public TransactionList() {
		initWidget( panel );
	}
	
	public void add( TransactionData transactionData ) {
		TransactionView transactionView = new TransactionViewImpl();
		transactionView.setTransactionData( transactionData );
		this.panel.add( transactionView );
	}

}
