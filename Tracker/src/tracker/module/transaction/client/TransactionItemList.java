package tracker.module.transaction.client;

import tracker.service.transaction.shared.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionItemList extends Composite {

	private Panel panel = new FlowPanel();

	public TransactionItemList() {
		initWidget( panel );
	}
	
	public void add( TransactionItemData transactionItemData ) {
		this.panel.add( new  TransactionItem( transactionItemData ) );
	}

}
