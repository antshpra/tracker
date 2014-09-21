package tracker.commons.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.shared.data.TransactionItemData;
import tracker.service.transaction.shared.data.TransactionItemTypeData;

import com.claymus.commons.client.ui.formfield.CurrencyInputFormField;
import com.claymus.commons.client.ui.formfield.DateInputFormField;
import com.claymus.commons.client.ui.formfield.ListBoxFormField;
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
	private final Panel colNoteAndButtons = new FlowPanel();

	private final Panel nestedRowDateTime = new FlowPanel();
	private final Panel nestedRowNoteAndButtons = new FlowPanel();

	private final Panel nestedColDate = new FlowPanel();
	private final Panel nestedColTime = new FlowPanel();
	private final Panel nestedColNote = new FlowPanel();
	private final Panel nestedColButtons = new FlowPanel();
	
	private final CurrencyInputFormField amountInput = new CurrencyInputFormField();
	private final ListBoxFormField itemTypeList = new ListBoxFormField();
	private final DateInputFormField dateInput = new DateInputFormField();
	private final TimeInputOptionalFormField timeInput = new TimeInputOptionalFormField();
	private final TextInputFormField noteInput = new TextInputFormField();
	private final Button deleteButton = new Button( "Delete" );

	
	public TransactionItemDataInputViewImpl() {

		amountInput.setPlaceholder( "Amount" );
		amountInput.setRequired( true );
		itemTypeList.setRequired( true );
		dateInput.setDate( new Date() );
		timeInput.setTime( new Date() );
		noteInput.setPlaceholder( "Note" );
		
		
		// Composing the widget
		rowTransactionItem.add( colAmount );
		rowTransactionItem.add( colItemType );
		rowTransactionItem.add( colDateTime );
		rowTransactionItem.add( colNoteAndButtons );

		colAmount.add( amountInput );
		colItemType.add( itemTypeList );
		colDateTime.add( nestedRowDateTime );
		colNoteAndButtons.add( nestedRowNoteAndButtons );
		
		nestedRowDateTime.add( nestedColDate );
		nestedRowDateTime.add( nestedColTime );
		
		nestedRowNoteAndButtons.add( nestedColNote );
		nestedRowNoteAndButtons.add( nestedColButtons );
		
		nestedColDate.add( dateInput );
		nestedColTime.add( timeInput );

		nestedColNote.add( noteInput );
		nestedColButtons.add( deleteButton );

		
		// Setting required style classes
		rowTransactionItem.setStyleName( "row" );
		
		colAmount.setStyleName( "col-md-3 col-xs-4" );
		colItemType.setStyleName( "col-md-4 col-xs-8" );
		colDateTime.setStyleName( "col-md-5 col-xs-12" );
		colNoteAndButtons.setStyleName( "col-md-12" );

		nestedRowDateTime.setStyleName( "row" );
		nestedRowNoteAndButtons.setStyleName( "row" );
		
		nestedColDate.setStyleName( "col-xs-6" );
		nestedColTime.setStyleName( "col-xs-6" );

		nestedColNote.setStyleName( "col-xs-7" );
		nestedColButtons.setStyleName( "col-xs-5" );
		nestedColButtons.getElement().setAttribute( "style", "text-align:right" );

		deleteButton.setStyleName( "btn btn-default" );


		initWidget( rowTransactionItem );
	}

	@Override
	public HandlerRegistration addDeleteButtonClickHandler(
			ClickHandler clickHandler ) {
		return deleteButton.addClickHandler( clickHandler );
	}

	@Override
	public boolean validateInputs() {
		return amountInput.validate()
				&& itemTypeList.validate()
				&& dateInput.validate()
				&& timeInput.validate()
				&& noteInput.validate();
	}
	
	@Override
	public TransactionItemData getTransactionItemData() {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public void setTransactionItemData( TransactionItemData transactionItemData ) {
		// TODO: Implementation
	}
	
	@Override
	public void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		// TODO: Implementation
	}
	
}
