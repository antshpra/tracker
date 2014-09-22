package tracker.commons.client;

import java.util.LinkedList;
import java.util.List;

import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemTypeData;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;

public class TransactionInputViewModalImpl extends TransactionInputView
		implements ClickHandler {

	private final List<TransactionItemTypeData> triTypeDataList;

	private final Modal modal = new Modal();

	private final TransactionDataInputView trDataInputView =
			new TransactionDataInputViewImpl();
	
	private final List<TransactionItemDataInputView> triDataInputViewList = 
			new LinkedList<>();
	
	private final Button addItemButton = new Button( "Add Item" );
	private final Button saveButton = new Button( "Save" );
	
	
	public TransactionInputViewModalImpl(
			List<TransactionItemTypeData> transactionItemTypeDataList ) {
		
		this.triTypeDataList = transactionItemTypeDataList;
		
		modal.setTitle( "Add Transaction" );
		addItemButton.addClickHandler( this );

		// Composing the widget
		modal.add( trDataInputView );

		modal.addButton( addItemButton );
		modal.addButton( saveButton );

		addItemButton.setStyleName( "btn btn-primary" );
		saveButton.setStyleName( "btn btn-primary" );
		
		
		initWidget( modal );
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == addItemButton ) {

			final TransactionItemDataInputView trItemDataInputView =
					new TransactionItemDataInputViewImpl( triTypeDataList );
			
			trItemDataInputView.addDeleteButtonClickHandler( new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					triDataInputViewList.remove( trItemDataInputView );
					trItemDataInputView.removeFromParent();
				}
				
			});
			
			triDataInputViewList.add( trItemDataInputView );
			modal.add( trItemDataInputView );
	
		}
	
	}

	@Override
	public void setEnabled( boolean enabled ) {
		modal.setEnabled( enabled );
		trDataInputView.setEnabled( enabled );
		for( TransactionItemDataInputView triDataInputView : triDataInputViewList )
			triDataInputView.setEnabled( enabled );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		if( visible )
			modal.show();
		else
			modal.hide();
	}
	
	@Override
	public HandlerRegistration addSaveButtonClickHandler(
			ClickHandler clickHandler ) {
		
		return saveButton.addClickHandler( clickHandler );
	}
	
	@Override
	public boolean validateInputs() {
		boolean validated = trDataInputView.validateInputs();
		for( TransactionItemDataInputView trItemDataInputView : triDataInputViewList )
			validated = trItemDataInputView.validateInputs() && validated;
		return validated;
	}
	
	@Override
	public TransactionData getTransactionData() {
		TransactionData trData = trDataInputView.getTransactionData();
		for( TransactionItemDataInputView triDataInputView : triDataInputViewList )
			trData.addTransactionItemData( triDataInputView.getTransactionItemData() );
		return trData;
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		// TODO: Implementation
	}

}
