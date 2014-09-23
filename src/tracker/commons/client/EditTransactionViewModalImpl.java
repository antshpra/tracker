package tracker.commons.client;

import java.util.LinkedList;
import java.util.List;

import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemData;
import tracker.service.shared.data.TransactionItemTypeData;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;

public class EditTransactionViewModalImpl extends EditTransactionView
		implements ClickHandler {

	private final List<TransactionItemTypeData> triTypeDataList;

	private final Modal modal = new Modal();

	private final TransactionDataInputView trDataInputView =
			new TransactionDataInputViewImpl();
	
	private final List<TransactionItemDataInputView> triDataInputViewList = 
			new LinkedList<>();
	
	private final Button addItemButton = new Button( "Add Item" );
	private final Button saveButton = new Button( "Save" );
	
	
	public EditTransactionViewModalImpl(
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
			addTransactionItemDataInputView( null );
		}
	
	}

	@Override
	public void setVisible( boolean visible ) {
		if( visible )
			modal.show();
		else
			modal.hide();
	}
	
	private void addTransactionItemDataInputView( TransactionItemData triData ) {

		final TransactionItemDataInputView triDataInputView =
				new TransactionItemDataInputViewImpl( triTypeDataList );

		if( triData != null )
			triDataInputView.setTransactionItemData( triData );
		
		triDataInputView.addDeleteButtonClickHandler( new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				removeTransactionItemDataInputView( triDataInputView );
			}
			
		});

		triDataInputViewList.add( triDataInputView );
		modal.add( triDataInputView );
	}
	
	private void removeTransactionItemDataInputView(
			TransactionItemDataInputView triDataInputView ) {
		
		triDataInputViewList.remove( triDataInputView );
		triDataInputView.removeFromParent();
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
	public void setEnabled( boolean enabled ) {
		modal.setEnabled( enabled );
		trDataInputView.setEnabled( enabled );
		for( TransactionItemDataInputView triDataInputView : triDataInputViewList )
			triDataInputView.setEnabled( enabled );
		addItemButton.setEnabled( enabled );
		saveButton.setEnabled( enabled );
	}
	
	@Override
	public TransactionData getTransactionData() {
		TransactionData trData = trDataInputView.getTransactionData();
		for( TransactionItemDataInputView triDataInputView : triDataInputViewList )
			trData.addTransactionItemData( triDataInputView.getTransactionItemData() );
		return trData;
	}
	
	@Override
	public void setTransactionData( TransactionData trData ) {
		for( TransactionItemDataInputView triDataInputView : triDataInputViewList )
			removeTransactionItemDataInputView( triDataInputView );

		trDataInputView.setTransactionData( trData );
		for( TransactionItemData triData : trData.getTransactionItemDataList() )
			addTransactionItemDataInputView( triData );
		
	}

	@Override
	public void reset() {
		// TODO: Implementation
	}
	
}
