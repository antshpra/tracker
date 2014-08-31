package tracker.commons.client;

import java.util.Date;

import tracker.service.transaction.shared.data.TransactionData;

import com.claymus.commons.client.ui.formfield.DateInputFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.claymus.commons.client.ui.formfield.TimeInputOptionalFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionInputViewImpl extends TransactionInputView {

	private final FlowPanel panel = new FlowPanel();

	private final Panel rowTransaction = new FlowPanel();
	private final Panel rowButtons = new FlowPanel();
	
	private final Panel colDescription = new FlowPanel();
	private final Panel colDateTime = new FlowPanel();
	private final Panel colButtons = new FlowPanel();
	
	private final Panel nestedRowDateTime = new FlowPanel();
	private final Panel nestedColDate = new FlowPanel();
	private final Panel nestedColTime = new FlowPanel();
	
	private final TextInputFormField descriptionInput = new TextInputFormField();
	private final DateInputFormField dateInput = new DateInputFormField();
	private final TimeInputOptionalFormField timeInput = new TimeInputOptionalFormField();

	private final Button addItemButton = new Button( "Add Item" );
	private final Button saveButton = new Button( "Save" );
	
	
	public TransactionInputViewImpl() {

		descriptionInput.setPlaceholder( "Description" );
		descriptionInput.setRequired( true );
		dateInput.setRequired( true );
		dateInput.setDate( new Date() );
		timeInput.setTime( new Date() );
		
		
		// Composing the widget
		panel.add( rowTransaction );
		panel.add( rowButtons );
		
		rowTransaction.add( colDescription );
		rowTransaction.add( colDateTime );
		rowButtons.add( colButtons );
		
		colDescription.add( descriptionInput );
		colDateTime.add( nestedRowDateTime );
		colButtons.add( addItemButton );
		colButtons.add( saveButton );

		nestedRowDateTime.add( nestedColDate );
		nestedRowDateTime.add( nestedColTime );
		
		nestedColDate.add( dateInput );
		nestedColTime.add( timeInput );

		
		// Setting required style classes
		panel.setStyleName( "container-fluid" );
		
		rowTransaction.setStyleName( "row" );
		rowButtons.setStyleName( "row" );
		
		colDescription.setStyleName( "col-sm-7" );
		colDateTime.setStyleName( "col-sm-5" );
		colButtons.setStyleName( "col-sm-12" );
		colButtons.getElement().setAttribute( "style", "text-align:right" );

		nestedRowDateTime.setStyleName( "row" );
		nestedColDate.setStyleName( "col-xs-6" );
		nestedColTime.setStyleName( "col-xs-6" );
		addItemButton.setStyleName( "btn btn-primary" );
		saveButton.setStyleName( "btn btn-primary" );
		
		
		initWidget( panel );
	}

	@Override
	public void addTransactionItemInputView(
			TransactionItemInputView transactionItemInputView ) {
		panel.insert( transactionItemInputView, panel.getWidgetIndex( rowButtons ) );
	}
	
	@Override
	public HandlerRegistration addAddItemButtonClickHandler(
			ClickHandler clickHandler ) {
		return addItemButton.addClickHandler( clickHandler );
	}
	
	@Override
	public boolean validateInputs() {
		return descriptionInput.validate()
				&& dateInput.validate()
				&& timeInput.validate();
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
