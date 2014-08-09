package tracker.module.transaction.client.views;

import java.util.Date;
import java.util.List;

import tracker.commons.shared.Amount;
import tracker.module.transaction.client.DateUtil;
import tracker.module.transaction.client.TransactionItemList;
import tracker.service.transaction.client.TransactionService;
import tracker.service.transaction.client.TransactionServiceAsync;
import tracker.service.transaction.shared.GetTransactionRequest;
import tracker.service.transaction.shared.GetTransactionResponse;
import tracker.service.transaction.shared.data.TransactionData;
import tracker.service.transaction.shared.data.TransactionItemData;
import tracker.theme.client.ThemeFactory;
import tracker.theme.client.TransactionModuleStyle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class TransactionViewImpl extends TransactionView {

	private static final TransactionServiceAsync transactionService = GWT.create( TransactionService.class );

	private TransactionModuleStyle style = ThemeFactory.getTheme().getTransactionModuleStyle();

	private String transactionId;
	private String transactionDesc;
	private Date transactionDate;
	
	private FocusPanel transactionDetailPanel = new FocusPanel();
	private TransactionItemList transactionItemList = new TransactionItemList();

	public TransactionViewImpl( TransactionData transactionData ) {
		this.transactionId = transactionData.getId();
		this.transactionDesc = transactionData.getDescription();
		this.transactionDate = transactionData.getTransactionDate();
		
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
		
		FlowPanel transactionDetailInnerPanel = new FlowPanel();
		transactionDetailInnerPanel.add( dateTimePanel );
		transactionDetailInnerPanel.add( detailPanel );

		transactionDetailPanel.addMouseUpHandler( this );
		transactionDetailPanel.add( transactionDetailInnerPanel );
		transactionDetailPanel.addStyleName( style.detailPanel() );
		
		Panel innerPanel = new FlowPanel();
		innerPanel.add( transactionDetailPanel );
		innerPanel.add( transactionItemList );
		
		FocusPanel panel = new FocusPanel();
		panel.addMouseOverHandler( this );
		panel.addMouseOutHandler( this );
		panel.add( innerPanel );
		
		initWidget( panel );
		setStyleName( style.transaction() );
		
		DateUtil dateUtil = new DateUtil( dateLabel, timeLabel );
		dateUtil.setDate( transactionData.getTransactionDate() );

		descriptionLabel.setText( transactionData.getDescription() );
	}
	
	@Override
	public void setTransactionData( TransactionData transactionData ) {
		// TODO: Implementation
	}

	@Override
	public void onMouseOver( MouseOverEvent event ) {
		transactionDetailPanel.addStyleName( style.highlighted() );
	}
	
	@Override
	public void onMouseOut( MouseOutEvent event ) {
		transactionDetailPanel.removeStyleName( style.highlighted() );
	}

	@Override
	public void onMouseUp( MouseUpEvent event ) {
		( (FocusPanel) event.getSource() ).setFocus( false );
		
		GetTransactionRequest request = new GetTransactionRequest();
		request.setTransactionId( transactionId );
		transactionService.getTransaction( request, new AsyncCallback<GetTransactionResponse>() {

			@Override
			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub
				Window.alert( caught.getClass().getName() );
			}

			@Override
			public void onSuccess( GetTransactionResponse response ) {
				List<TransactionItemData> transactionItemDataList = response.getTransactionData().getTransactionItemDataList();
				if( transactionItemDataList == null ) {
					Window.alert( "Nothing under this transaction !" );
				} else {
					Amount amount = new Amount( 0 );
					for( TransactionItemData transactionItemData : transactionItemDataList ) {
						TransactionViewImpl.this.transactionItemList.add( transactionItemData );
						amount = amount.add( transactionItemData.getAmount() );
					}
					if( amount.getValue() != 0 )
						Window.alert( "Amount = " + amount.getValue() + " for Tr. " + transactionDesc + " (" + transactionDate + ")" );
				}
			}
			
		});
	}

}
