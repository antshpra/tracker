package tracker.commons.client;

import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemData;

import com.claymus.commons.client.Amount;
import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class TransactionViewAccordionImpl extends TransactionView {

	private Accordion accordion = new Accordion();

	private final DateTimeFormat dateTimeFormat =
			DateTimeFormat.getFormat( "hh:mm aaa EEE d MMM yyyy" );
	
	
	public TransactionViewAccordionImpl() {
		
		accordion.getElement().setAttribute( "style", "margin-top: 5px; margin-bottom: 5px;" );
		
		initWidget( accordion );
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		
		accordion.setTitle(
				transactionData.getDescription()
				+ "<small style='margin-left:5px;'>"
				+ dateTimeFormat.format( transactionData.getTransactionDate() )
				+ "</small>");
		
		accordion.clear();
		
		Amount amount = new Amount( 0 );
		for( TransactionItemData transactionItemData
				: transactionData.getTransactionItemDataList() ) {

			amount = amount.add( transactionItemData.getAmount() );

			Panel row = new FlowPanel();
			
			Panel amountCol = new SimplePanel();
			Panel descCol = new FlowPanel();
			
			Label amountLabel = new Label();
			Label itemTypeLabel = new Label();
			Label itemNoteLabel = new Label();
			Element itemDateElement = Document.get().createElement( "small" );
			
			accordion.add( row );
			row.add( amountCol );
			row.add( descCol );
			amountCol.add( amountLabel );
			descCol.add( itemTypeLabel );
			descCol.add( itemNoteLabel );
			descCol.getElement().appendChild( itemDateElement );
			
			row.setStyleName( "row" );
			amountCol.setStyleName( "col-xs-2" );
			descCol.setStyleName( "col-xs-10" );
			amountLabel.setStyleName( "text-right" );
			itemTypeLabel.setStyleName( "label label-default" );
			itemNoteLabel.getElement().setAttribute( "style", "display:inline; margin-left:5px;");
			itemDateElement.setAttribute( "style", "margin-left:5px;");
			
			amountLabel.setText( transactionItemData.getAmount().toString() );
			itemTypeLabel.setText(
					transactionItemData.getTransactionItemType().getTitle() );
			itemNoteLabel.setText(
					transactionItemData.getNote() == null ?
							"" : transactionItemData.getNote() );
			
			if( transactionItemData.getTransactionDate() != null )
				itemDateElement.setInnerText(
						dateTimeFormat.format(
								transactionItemData.getTransactionDate() ) );
		}

		// TODO: remove this whenever possible
		if( amount.getValue() != 0 )
			Window.alert( "Amount = " + amount.getValue()
					+ " for Tr. " + transactionData.getDescription()
					+ " (" + transactionData.getTransactionDate() + ")" );

	}

}
