package tracker.commons.client;

import java.util.LinkedList;
import java.util.List;

import tracker.service.transaction.shared.data.TransactionData;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;

public class TransactionInputViewModalImpl extends TransactionInputView
		implements ClickHandler {

	private final Modal modal = new Modal();

	private final TransactionDataInputView trDataInputView =
			new TransactionDataInputViewImpl();
	
	private final List<TransactionItemDataInputView> trItemDataInputViewList = 
			new LinkedList<>();
	

	private final Button addItemButton = new Button( "Add Item" );
	private final Button saveButton = new Button( "Save" );
	
	
	public TransactionInputViewModalImpl() {
		
		modal.setTitle( "Add Transaction" );
		addItemButton.addClickHandler( this );

		// Composing the widget
		modal.add( trDataInputView );

		modal.addButton( addItemButton );
		modal.addButton( saveButton );

		addItemButton.setStyleName( "btn btn-primary" );
		saveButton.setStyleName( "btn btn-primary" );
		
		
		initWidget( modal );
		modal.show();
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == addItemButton ) {

			final TransactionItemDataInputView trItemDataInputView =
					new TransactionItemDataInputViewImpl();
			
			trItemDataInputView.addDeleteButtonClickHandler( new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					trItemDataInputViewList.remove( trItemDataInputView );
					trItemDataInputView.removeFromParent();
				}
				
			});
			
			trItemDataInputViewList.add( trItemDataInputView );
			modal.add( trItemDataInputView );
	
		}
	
	}

	@Override
	public HandlerRegistration addSaveButtonClickHandler(
			ClickHandler clickHandler ) {
		
		return saveButton.addClickHandler( clickHandler );
	}
	
	@Override
	public boolean validateInputs() {
		boolean validated = trDataInputView.validateInputs();
		for( TransactionItemDataInputView trItemDataInputView : trItemDataInputViewList )
			validated = trItemDataInputView.validateInputs() && validated;
		return validated;
	}
	
	@Override
	public TransactionData getTransactionData() {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		// TODO: Implementation
	}
	
}
