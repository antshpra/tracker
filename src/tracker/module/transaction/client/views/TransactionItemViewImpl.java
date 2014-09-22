package tracker.module.transaction.client.views;

import tracker.module.transaction.client.DateUtil;
import tracker.service.shared.data.TransactionItemData;
import tracker.theme.client.ThemeFactory;
import tracker.theme.client.TransactionModuleStyle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class TransactionItemViewImpl extends TransactionItemView {

	private TransactionModuleStyle style = ThemeFactory.getTheme().getTransactionModuleStyle();
	
	private Panel panel = new FlowPanel();

	
	public TransactionItemViewImpl() {
		initWidget( panel );
	}

	
	@Override
	public void setTransactionItemData( TransactionItemData transactionItemData ) {
		panel.clear();
		
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
		
		panel.add( dateTimePanel );
		panel.add( detailPanel );

		setStyleName( style.transactionItem() );
		
		DateUtil dateUtil = new DateUtil( dateLabel, timeLabel );
		dateUtil.setDate(
				transactionItemData.getTransactionDate() == null ?
						transactionItemData.getTransactionData().getTransactionDate() :
						transactionItemData.getTransactionDate() );

		descriptionLabel.setText(
				transactionItemData.getAmount().toString() +
				" " + transactionItemData.getTransactionData().getDescription() +
				( transactionItemData.getNote() == null ? "" : ", " + transactionItemData.getNote() ) +
				" #" + transactionItemData.getTransactionItemType().getTitle() );
	}

}
