package tracker.module.transaction.client;

import tracker.service.transaction.shared.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionItemList extends Composite {
	
	public TransactionItemList() {
		Panel panel = new FlowPanel();
		initWidget( panel );
	}
	
	public void add( TransactionItemData transactionItemData ) {
		
	}

}
