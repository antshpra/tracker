package tracker.commons.client;

import java.util.Date;
import java.util.List;

import tracker.service.shared.data.TransactionItemData;
import tracker.service.shared.data.TransactionItemTypeData;

import com.claymus.commons.client.ui.formfield.CurrencyInputFormField;
import com.claymus.commons.client.ui.formfield.DateInputOptionalFormField;
import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.NumberInputFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.claymus.commons.client.ui.formfield.TimeInputOptionalFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionItemDataInputViewImpl extends TransactionItemDataInputView {

	private final Panel rowTransactionItem = new FlowPanel();

	private final Panel colAmount = new FlowPanel();
	private final Panel colItemType = new FlowPanel();
	private final Panel colDateTime = new FlowPanel();
	private final Panel colNote = new FlowPanel();
	private final Panel colOrder = new FlowPanel();
	private final Panel colButtons = new FlowPanel();

	private final Panel nestedRowDateTime = new FlowPanel();

	private final Panel nestedColDate = new FlowPanel();
	private final Panel nestedColTime = new FlowPanel();
	
	private final CurrencyInputFormField amountInput = new CurrencyInputFormField();
	private final ListBoxFormField itemTypeList = new ListBoxFormField();
	private final DateInputOptionalFormField dateInput = new DateInputOptionalFormField();
	private final TimeInputOptionalFormField timeInput = new TimeInputOptionalFormField();
	private final TextInputFormField noteInput = new TextInputFormField();
	private final NumberInputFormField orderInput = new NumberInputFormField();
	private final Button deleteButton = new Button( "Delete" );

	private String transactionItemId;

	
	public TransactionItemDataInputViewImpl(
			List<TransactionItemTypeData> transactionItemTypeDataList ) {

		amountInput.setPlaceholder( "Amount" );
		amountInput.setRequired( true );
		itemTypeList.setPlaceholder( "-- Select Type --" );
		setTransactionItemTypeDataList( transactionItemTypeDataList );
		itemTypeList.setRequired( true );
		dateInput.setDate( new Date() );
		timeInput.setTime( new Date() );
		noteInput.setPlaceholder( "Note" );
		orderInput.setPlaceholder( "Order" );
		orderInput.setRequired( true );
		
		// Composing the widget
		rowTransactionItem.add( colAmount );
		rowTransactionItem.add( colItemType );
		rowTransactionItem.add( colDateTime );
		rowTransactionItem.add( colNote );
		rowTransactionItem.add( colOrder );
		rowTransactionItem.add( colButtons );
		
		colAmount.add( amountInput );
		colItemType.add( itemTypeList );
		colDateTime.add( nestedRowDateTime );
		colNote.add( noteInput );
		colOrder.add( orderInput );
		colButtons.add( deleteButton );

		nestedRowDateTime.add( nestedColDate );
		nestedRowDateTime.add( nestedColTime );
		
		nestedColDate.add( dateInput );
		nestedColTime.add( timeInput );

		
		// Setting required style classes
		rowTransactionItem.setStyleName( "row" );
		
		colAmount.setStyleName( "col-md-2 col-xs-4" );
		colItemType.setStyleName( "col-md-4 col-xs-8" );
		colDateTime.setStyleName( "col-md-6 col-xs-12" );
		colNote.setStyleName( "col-md-7 col-xs-6" );
		colOrder.setStyleName( "col-md-2 col-xs-2" );
		colButtons.setStyleName( "col-md-3 col-xs-4" );
		colButtons.getElement().setAttribute( "style", "text-align:right" );

		nestedRowDateTime.setStyleName( "row" );
		
		nestedColDate.setStyleName( "col-xs-7" );
		nestedColTime.setStyleName( "col-xs-5" );

		deleteButton.setStyleName( "btn btn-default" );


		initWidget( rowTransactionItem );
	}

	@Override
	public HandlerRegistration addDeleteButtonClickHandler(
			ClickHandler clickHandler ) {
		return deleteButton.addClickHandler( clickHandler );
	}

	public void setEnabled( boolean enabled ) {
		amountInput.setEnabled( enabled );
		itemTypeList.setEnabled( enabled );
		dateInput.setEnabled( enabled );
		timeInput.setEnabled( enabled );
		noteInput.setEnabled( enabled );
		orderInput.setEnabled( enabled );
		deleteButton.setEnabled( enabled );
	}
	
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = amountInput.validate() && validated;
		validated = itemTypeList.validate() && validated;
		validated = dateInput.validate() && validated;
		validated = timeInput.validate() && validated;
		validated = noteInput.validate() && validated;
		validated = orderInput.validate() && validated;
		return validated;
	}
	
	@Override
	public TransactionItemData getTransactionItemData() {
		TransactionItemData triData = new TransactionItemData();
		triData.setId( transactionItemId );
		triData.setAmount( amountInput.getAmount() );
		triData.setTransactionItemTypeId( itemTypeList.getValue() );
		triData.setTransactionDate( timeInput.getTime( dateInput.getDate() ) );
		triData.setNote( noteInput.getText() );
		triData.setOrder( orderInput.getValue() );
		return triData;
	}
	
	@Override
	public void setTransactionItemData( TransactionItemData triData ) {
		transactionItemId = triData.getId();
		amountInput.setAmount( triData.getAmount() );
		itemTypeList.setValue( triData.getTransactionItemTypeId() );
		dateInput.setDate( triData.getTransactionDate() );
		timeInput.setTime( triData.getTransactionDate() );
		noteInput.setText( triData.getNote() );
		orderInput.setValue( triData.getOrder() );
	}
	
	private void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		for( TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
			itemTypeList.addItem( transactionItemTypeData.getQualifiedTitle(), transactionItemTypeData.getId() );
			setTransactionItemTypeDataList( transactionItemTypeData.getChildren() );
		}
	}

}
