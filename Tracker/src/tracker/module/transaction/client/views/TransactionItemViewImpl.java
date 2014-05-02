package tracker.module.transaction.client.views;

import tracker.module.transaction.client.DateUtil;
import tracker.module.transaction.client.resources.TransactionResources;
import tracker.service.transaction.shared.TransactionItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TransactionItemViewImpl extends Composite {

	public TransactionItemViewImpl( TransactionItemData transactionItemData ) {
		Label dateLabel = new Label();
		Label timeLabel = new Label();

		Label descriptionLabel = new Label();
		
		FlowPanel dateTimePanel = new FlowPanel();
		dateTimePanel.add( dateLabel );
		dateTimePanel.add( timeLabel );
		dateTimePanel.addStyleName( TransactionResources.INSTANCE.css().dateTime() );
		
		FlowPanel detailPanel = new FlowPanel();
		detailPanel.add( descriptionLabel );
		detailPanel.addStyleName( TransactionResources.INSTANCE.css().detail() );
		
		FlowPanel panel = new FlowPanel();
		panel.add( dateTimePanel );
		panel.add( detailPanel );

		initWidget( panel );
		setStyleName( TransactionResources.INSTANCE.css().transactionItem() );
		
		DateUtil dateUtil = new DateUtil( dateLabel, timeLabel );
		dateUtil.setDate( transactionItemData.getTransactionDate() );

		if( transactionItemData.getDescription() == null ) {
			descriptionLabel.setText(
					"Rs. " + transactionItemData.getAmount() +
					" #" + transactionItemData.getTransactionItemType().getTitle() );
		} else {
			descriptionLabel.setText(
					"Rs. " + transactionItemData.getAmount() +  " for " +
					transactionItemData.getDescription() + 
					" #" + transactionItemData.getTransactionItemType().getTitle() );
		}
	}

}
