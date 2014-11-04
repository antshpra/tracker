package tracker.pagecontent.transactionlist.client;

import java.util.List;

import tracker.commons.shared.TransactionFilter;
import tracker.service.shared.data.TransactionItemTypeData;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TransactionListFilter extends Composite implements HasValueChangeHandlers<TransactionFilter> {

	private final Panel panel = new FlowPanel();
	private final ListBoxFormField itemTypeList = new ListBoxFormField();

	
	public TransactionListFilter( List<TransactionItemTypeData> triTypeDataList ) {
		
		itemTypeList.setPlaceholder( "-- Select Transaction Item Type --" );
		setTransactionItemTypeDataList( triTypeDataList );
					
		itemTypeList.addValueChangeHandler( new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange( ValueChangeEvent<String> event ) {
				TransactionFilter trFilter = new TransactionFilter();
				trFilter.setTransactionItemTypeId( event.getValue() );
				trFilter.setTransactionDateChronologicalOrder( false );
				trFilter.setCreationDateChronologicalOrder( false );

				ValueChangeEvent.fire( TransactionListFilter.this, trFilter );
			}

		});
		
		
		panel.add( itemTypeList );
		
		initWidget( panel );
	}

	private void setTransactionItemTypeDataList( List<TransactionItemTypeData> triTypeDataList ) {
		for( TransactionItemTypeData triTypeData : triTypeDataList ) {
			itemTypeList.addItem( triTypeData.getQualifiedTitle(), triTypeData.getId() );
			setTransactionItemTypeDataList( triTypeData.getChildren() );
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<TransactionFilter> handler ) {
		return addHandler( handler, ValueChangeEvent.getType() );
	}

}
