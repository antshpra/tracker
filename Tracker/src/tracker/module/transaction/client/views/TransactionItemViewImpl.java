package tracker.module.transaction.client.views;

import tracker.module.transaction.client.DateUtil;
import tracker.service.transaction.shared.TransactionItemData;
import tracker.theme.client.ThemeFactory;
import tracker.theme.client.TransactionModuleStyle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TransactionItemViewImpl extends TransactionItemView {

	private TransactionModuleStyle style = ThemeFactory.getTheme().getTransactionModuleStyle();
	
	public TransactionItemViewImpl( TransactionItemData transactionItemData ) {
		Label dateLabel = new Label();
		Label timeLabel = new Label();

		Label descriptionLabel = new Label();
		
		FlowPanel dateTimePanel = new FlowPanel();
		dateTimePanel.add( dateLabel );
		dateTimePanel.add( timeLabel );
		dateTimePanel.addStyleName( style.dateTime() );
		
		FlowPanel detailPanel = new FlowPanel();
		detailPanel.add( descriptionLabel );
		detailPanel.addStyleName( style.detail() );
		
		FlowPanel panel = new FlowPanel();
		panel.add( dateTimePanel );
		panel.add( detailPanel );

		initWidget( panel );
		setStyleName( style.transactionItem() );
		
		DateUtil dateUtil = new DateUtil( dateLabel, timeLabel );
		dateUtil.setDate( transactionItemData.getTransactionDate() );

		descriptionLabel.setText(
				transactionItemData.getAmount().toString() +
				( transactionItemData.getNote() == null ? "" : " [" + transactionItemData.getNote() + "]") +
				" #" + transactionItemData.getTransactionItemType().getTitle() );
	}

	@Override
	public void setTransactionItemData(TransactionItemData transactionItemData) {
		// TODO Auto-generated method stub
	}

}
