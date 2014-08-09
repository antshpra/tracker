package tracker.module.transaction.client;

import tracker.module.transaction.client.views.TransactionItemViewImplDeprecated;
import tracker.service.transaction.shared.data.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionItemList extends Composite {

	private Panel panel = new FlowPanel();

	public TransactionItemList() {
		initWidget( panel );
	}
	
	public void add( TransactionItemData transactionItemData ) {
		this.panel.add( new  TransactionItemViewImplDeprecated( transactionItemData ) );
	}

}
