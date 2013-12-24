package tracker.module.transaction.client;

import tracker.service.transaction.shared.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TransactionItem extends Composite {

	public TransactionItem( TransactionItemData transactionItemData ) {
		Label dateLabel = new Label();
		Label timeLabel = new Label();

		Label descriptionLabel = new Label();
		
		FlowPanel dateTimePanel = new FlowPanel();
		dateTimePanel.add( dateLabel );
		dateTimePanel.add( timeLabel );
		dateTimePanel.addStyleName( "transactionItem_DateTime" );
		
		FlowPanel detailPanel = new FlowPanel();
		detailPanel.add( descriptionLabel );
		detailPanel.addStyleName( "transactionItem_Detail" );
		
		FlowPanel panel = new FlowPanel();
		panel.add( dateTimePanel );
		panel.add( detailPanel );

		initWidget( panel );
		setStyleName( "transactionItem" );
		
		DateUtil dateUtil = new DateUtil( dateLabel, timeLabel );
		dateUtil.setDate( transactionItemData.getTransactionDate() );

		descriptionLabel.setText( transactionItemData.getDescription() );
	}

}
