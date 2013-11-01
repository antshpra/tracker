package tracker.module.transaction.client;

import java.util.Date;

import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.TransactionData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Transaction {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private Panel dateTimePanel;
	private Panel detailPanel;
	private Panel buttonsPanel;

	// dateTimePanel items
	private DateUtil dateUtil;
	
	// detailPanel items
	private Label descriptionLabel;
	private TextBox descriptionInput;
	
	// buttonsPanel items
	private Button saveButton;
	private Button editButton;
	private Button updateButton;
	private Button cancelButton;
	
	public Transaction() {
		init();
		newMode();
	}
	
	public Transaction( TransactionData transactionData ) {
		init();
		viewMode();
		setData( transactionData );
	}
	
	
	private void init() {
		this.dateTimePanel = new VerticalPanel();
		this.detailPanel = new VerticalPanel();
		this.buttonsPanel = new HorizontalPanel();
		
		Label dateLabel = new Label();
		Label timeLabel = new Label();
		this.dateTimePanel.add( dateLabel );
		this.dateTimePanel.add( timeLabel );
		this.dateUtil = new DateUtil( dateLabel, timeLabel );
		
		this.descriptionLabel = new Label();
		this.descriptionInput = new TextBox();
		this.detailPanel.add( this.descriptionLabel );
		this.detailPanel.add( this.descriptionInput );

		this.saveButton = new Button( "Save" ); // I18n
		this.editButton = new Button( "Edit" ); // I18n
		this.updateButton = new Button( "Update" ); // I18n
		this.cancelButton = new Button( "Cancel" ); // I18n
		this.buttonsPanel.add( saveButton );
		this.buttonsPanel.add( editButton );
		this.buttonsPanel.add( updateButton );
		this.buttonsPanel.add( cancelButton );

		
		this.dateUtil.setDate( new Date() );
		
		this.saveButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Transaction.this.savingMode();
				Transaction.this.create();
			}
		});

		this.editButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Transaction.this.editMode();
			}
		});

		this.updateButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Transaction.this.savingMode();
				Transaction.this.update();
			}
		});
	
		this.cancelButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Transaction.this.viewMode();
			}
		});

	}

	private void setData( TransactionData transactionData ) {
		this.dateUtil.setDate( transactionData.getTransactionDate() );
		this.descriptionLabel.setText( transactionData.getDescription() );
		this.descriptionInput.setText( transactionData.getDescription() );
	}

	
	public Widget getDateTimeWidget() {
		return this.dateTimePanel;
	}
	
	public Widget getDetailWidget() {
		return this.detailPanel;
	}

	public Widget getButtonsWidget() {
		return this.buttonsPanel;
	}
	
	
	private void newMode() {
		this.descriptionLabel.setVisible( false );
		this.descriptionInput.setVisible( true );
		
		this.saveButton.setEnabled( true );
		this.saveButton.setVisible( true );

		this.editButton.setEnabled( false );
		this.editButton.setVisible( false );

		this.updateButton.setEnabled( false );
		this.updateButton.setVisible( false );

		this.cancelButton.setEnabled( false );
		this.cancelButton.setVisible( false );
	}
	
	private void viewMode() {
		this.descriptionLabel.setVisible( true );
		this.descriptionInput.setVisible( false );
		
		this.saveButton.setEnabled( false );
		this.saveButton.setVisible( false );

		this.editButton.setEnabled( true );
		this.editButton.setVisible( true );

		this.updateButton.setEnabled( false );
		this.updateButton.setVisible( false );

		this.cancelButton.setEnabled( false );
		this.cancelButton.setVisible( false );
	}
	
	private void editMode() {
		this.descriptionLabel.setVisible( false );
		this.descriptionInput.setEnabled( true );
		this.descriptionInput.setVisible( true );
		
		this.saveButton.setEnabled( false );
		this.saveButton.setVisible( false );

		this.editButton.setEnabled( false );
		this.editButton.setVisible( false );

		this.updateButton.setEnabled( true );
		this.updateButton.setVisible( true );

		this.cancelButton.setEnabled( true );
		this.cancelButton.setVisible( true );
	}
	
	private void savingMode() {
		this.descriptionInput.setEnabled( false );
		
		this.updateButton.setEnabled( false );

		this.cancelButton.setEnabled( false );
	}
	
	
	private void create() {
		// TODO: implementation
		CreateTransactionRequest request = new CreateTransactionRequest();
		transactionService.createTransaction( request, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void update() {
		// TODO: implementation
		CreateTransactionRequest request = new CreateTransactionRequest();
		transactionService.createTransaction( request, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
