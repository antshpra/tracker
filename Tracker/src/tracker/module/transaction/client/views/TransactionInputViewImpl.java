package tracker.module.transaction.client.views;

import tracker.module.transaction.client.DateTimePicker;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class TransactionInputViewImpl extends TransactionInputView {

	private DateTimePicker dateTimePicker = new DateTimePicker();
	private Label descriptionLabel = new Label( "Description" );
	private TextBox descriptionInput = new TextBox();

	public TransactionInputViewImpl() {
		Panel panel = new HorizontalPanel();
		panel.add( dateTimePicker );
		panel.add( descriptionLabel );
		panel.add( descriptionInput );
		
		initWidget( panel );
	}

	@Override
	public boolean validateInputs() {
		// TODO: Implementation
		return true;
	}
	
	@Override
	public CreateTransactionRequest getCreateTransactionRequest() {
		CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
		createTransactionRequest.setTransactionDate( dateTimePicker.getDate() );
		createTransactionRequest.setDescription( descriptionInput.getText() );
		return createTransactionRequest;
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		// TODO: Implementation
	}
	
}
