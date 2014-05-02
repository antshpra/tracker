package tracker.module.transaction.client.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tracker.module.transaction.client.DateTimePicker;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.TransactionItemData;
import tracker.service.transaction.shared.TransactionItemTypeData;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class TransactionItemInputViewImpl extends TransactionItemInputView {

	private DateTimePicker dateTimePicker = new DateTimePicker();
	private Label amountLabel = new Label( "Amount" ); // TODO: I18n
	private TextBox amountInput = new TextBox();
	private ListBox itemTypeList = new ListBox();
	private Label noteLabel = new Label( "Note" ); // TODO: I18n
	private TextBox noteInput = new TextBox();
	
	public TransactionItemInputViewImpl() {
		itemTypeList.addItem( "Loading ..." ); // I18n

		Panel panel = new HorizontalPanel();
		panel.add( dateTimePicker );
		panel.add( amountLabel );
		panel.add( amountInput );
		panel.add( itemTypeList );
		panel.add( noteLabel );
		panel.add( noteInput );

		initWidget( panel );
	}

	@Override
	public boolean validateInputs() {
		// TODO: Implementation
		return true;
	}
	
	@Override
	public CreateTransactionItemRequest getCreateTransactionItemRequest() {
		CreateTransactionItemRequest createTransactionItemRequest = new CreateTransactionItemRequest();
		createTransactionItemRequest.setTransactionItemTypeId( itemTypeList.getValue( itemTypeList.getSelectedIndex() ) );
		createTransactionItemRequest.setTransactionDate( dateTimePicker.getDate() );
		createTransactionItemRequest.setAmount( Double.parseDouble( amountInput.getText() ) );
		createTransactionItemRequest.setNote( noteInput.getText() );
		return createTransactionItemRequest;
	}
	
	@Override
	public void setTransactionItemData( TransactionItemData transactionItemData ) {
		// TODO: Implementation
	}
	
	@Override
	public void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = new HashMap<>();
		for( TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList )
			transactionItemTypeIdToTransactionItemTypeDataMap.put( transactionItemTypeData.getId(), transactionItemTypeData );

		setTransactionItemTypeList( transactionItemTypeDataList, transactionItemTypeIdToTransactionItemTypeDataMap );
	}
	
	private void setTransactionItemTypeList(
			List<TransactionItemTypeData> transactionItemTypeDataList,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		itemTypeList.clear();
		itemTypeList.addItem( "-- Select Transaction Item Type --" ); // I18n

		for( TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
			TransactionItemTypeData parentTransactionItemTypeData =
					transactionItemTypeIdToTransactionItemTypeDataMap.get( transactionItemTypeData.getParentId() );
			
			if( parentTransactionItemTypeData != null ) {
				itemTypeList.addItem( 
						parentTransactionItemTypeData.getTitle() + " : " + transactionItemTypeData.getTitle(),
						transactionItemTypeData.getId() );
			} else {
				itemTypeList.addItem(
						transactionItemTypeData.getTitle(),
						transactionItemTypeData.getId() );
			}
		}
	}

}
