package tracker.pagecontent.transactionlist.client;

import tracker.commons.client.TransactionInputView;
import tracker.commons.client.TransactionInputViewModalImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class TransactionListContent implements EntryPoint, ClickHandler {

	private final TransactionList trList = new TransactionList();
	private final Button newTransactionButton = new Button();
	private final TransactionInputView trInputView = new TransactionInputViewModalImpl(); 
	

	public void onModuleLoad() {
		newTransactionButton.setText( "New Transaction" );
		newTransactionButton.setStyleName( "btn btn-primary" );
		newTransactionButton.addClickHandler( this );
		
		RootPanel rootPanel = RootPanel.get( "PageContent-TransactionList" );
		rootPanel.add( newTransactionButton );
		rootPanel.add( trInputView );
		rootPanel.add( trList );
	}
	
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == newTransactionButton ) {
			trInputView.setVisible( true );
		}
		
	}
		
}
