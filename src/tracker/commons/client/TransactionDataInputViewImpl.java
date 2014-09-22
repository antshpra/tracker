package tracker.commons.client;

import java.util.Date;

import tracker.service.shared.data.TransactionData;

import com.claymus.commons.client.ui.formfield.DateInputFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.claymus.commons.client.ui.formfield.TimeInputOptionalFormField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionDataInputViewImpl extends TransactionDataInputView {

	private final FlowPanel rowTransaction = new FlowPanel();

	private final Panel colDescription = new FlowPanel();
	private final Panel colDateTime = new FlowPanel();
	
	private final Panel nestedRowDateTime = new FlowPanel();
	private final Panel nestedColDate = new FlowPanel();
	private final Panel nestedColTime = new FlowPanel();
	
	private final TextInputFormField descriptionInput = new TextInputFormField();
	private final DateInputFormField dateInput = new DateInputFormField();
	private final TimeInputOptionalFormField timeInput = new TimeInputOptionalFormField();

	
	public TransactionDataInputViewImpl() {

		descriptionInput.setPlaceholder( "Description" );
		descriptionInput.setRequired( true );
		dateInput.setRequired( true );
		dateInput.setDate( new Date() );
		timeInput.setTime( new Date() );
		
		
		// Composing the widget
		rowTransaction.add( colDescription );
		rowTransaction.add( colDateTime );
		
		colDescription.add( descriptionInput );
		colDateTime.add( nestedRowDateTime );

		nestedRowDateTime.add( nestedColDate );
		nestedRowDateTime.add( nestedColTime );
		
		nestedColDate.add( dateInput );
		nestedColTime.add( timeInput );

		
		// Setting required style classes
		rowTransaction.setStyleName( "row" );
		
		colDescription.setStyleName( "col-md-6" );
		colDateTime.setStyleName( "col-md-6" );

		nestedRowDateTime.setStyleName( "row" );
		nestedColDate.setStyleName( "col-xs-7" );
		nestedColTime.setStyleName( "col-xs-5" );
		
		
		initWidget( rowTransaction );
	}


	@Override
	public void setEnabled( boolean enabled ) {
		descriptionInput.setEnabled( enabled );
		dateInput.setEnabled( enabled );
		timeInput.setEnabled( enabled );
	}

	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = descriptionInput.validate() && validated;
		validated = dateInput.validate() && validated;
		validated = timeInput.validate() && validated;
		return validated;
	}
	
	@Override
	public TransactionData getTransactionData() {
		TransactionData trData = new TransactionData();
		trData.setDescription( descriptionInput.getText() );
		trData.setTransactionDate( timeInput.getTime( dateInput.getDate() ) );
		return trData;
	}
	
	@Override
	public void setTransactionData( TransactionData trData ) {
		descriptionInput.setText( trData.getDescription() );
		dateInput.setDate( trData.getTransactionDate() );
	}
	
}
