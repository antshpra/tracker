package tracker.commons.client;

import tracker.commons.shared.Amount;
import tracker.service.transaction.shared.data.TransactionData;
import tracker.service.transaction.shared.data.TransactionItemData;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class TransactionViewImpl extends TransactionView {

	private Panel panel = new FlowPanel();
	
	private final Panel headingPanel = new SimplePanel();
	private final Panel collapsePanel = new SimplePanel();
	
	private final Anchor titleAnchor = new Anchor();
	private final HeadingElement titleElement = Document.get().createHElement( 4 );
	
	private final Panel bodyPanel = new FlowPanel();

	private final DateTimeFormat dateTimeFormat =
			DateTimeFormat.getFormat( "hh:mm aaa EEE d MMM yyyy" );
	
	
	public TransactionViewImpl() {
		
		panel.add( headingPanel );
		panel.add( collapsePanel );
	
		headingPanel.add( titleAnchor );
		titleAnchor.getElement().appendChild( titleElement );
		
		collapsePanel.add( bodyPanel );


		panel.setStyleName( "tracker-TransactionView" );
		panel.addStyleName( "panel panel-default" );
		
		headingPanel.setStyleName( "panel-heading" );
		collapsePanel.setStyleName( "panel-collapse collapse" );
		
		titleAnchor.getElement().setAttribute( "data-toggle", "collapse" );
		titleElement.setAttribute( "class", "panel-title" );
		
		bodyPanel.setStyleName( "panel-body" );

		
		initWidget( panel );
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		
		titleAnchor.setHref( "#Tr-" + transactionData.getId() );
		titleElement.setInnerHTML(
				transactionData.getDescription()
				+ "<small>"
				+ dateTimeFormat.format( transactionData.getTransactionDate() )
				+ "</small>");
		
		collapsePanel.getElement()
				.setAttribute( "id", "Tr-" + transactionData.getId() );

		bodyPanel.clear();
		
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
			
			bodyPanel.add( row );
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

			amountLabel.setText( transactionItemData.getAmount().toString() );
			itemTypeLabel.setText(
					transactionItemData.getTransactionItemType().getTitle() );
			itemNoteLabel.setText(
					transactionItemData.getNote() == null ?
							"" : transactionItemData.getNote() );
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
