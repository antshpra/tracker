package tracker.module.transaction.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimePicker extends Composite implements ClickHandler, ValueChangeHandler<Date> {
	
	private Label dateLabel = new Label();
	private DatePicker datePicker = new DatePicker();
	private ListBox hourList = new ListBox();
	private ListBox minuteList = new ListBox();
	private ToggleButton amPmButton = new ToggleButton( "AM", "PM" );
	
	public DateTimePicker() {
		Date date = new Date();
		
		dateLabel.addClickHandler( this );
		datePicker.addValueChangeHandler( this );
		datePicker.setValue( date, true );
		
		for( int i=0; i<=11; i++ )
			hourList.addItem( Integer.toString( i ), Integer.toString( i ) );
		hourList.setSelectedIndex( date.getHours() % 12 );

		for( int i=0; i<=59; i++ )
			minuteList.addItem( Integer.toString( i ), Integer.toString( i ) );
		minuteList.setSelectedIndex( date.getMinutes() );

		amPmButton.setValue( date.getHours() >= 12 );

		FlowPanel panel = new FlowPanel();
		panel.add( dateLabel );
		panel.add( hourList );
		panel.add( minuteList );
		panel.add( amPmButton );
		panel.add( datePicker );
		
		initWidget( panel );
		setStyleName( "transaction-DateTimePicker" );
	}

	@Override
	public void onClick(ClickEvent event) {
		if( event.getSource() == dateLabel )
			datePicker.setVisible( !datePicker.isVisible() );
	}

	@Override
	public void onValueChange( ValueChangeEvent<Date> event )  {
		if( event.getSource() == datePicker ) {
			Date date = event.getValue();
			dateLabel.setText( date.getDate() + "/" + ( date.getMonth() + 1 ) + "/" + ( date.getYear() + 1900 ) );
			datePicker.setVisible( false );
		}
	}
	
	public Date getDate() {
		Date date = datePicker.getValue();
		date.setHours( amPmButton.isDown() ? 12 + hourList.getSelectedIndex() : hourList.getSelectedIndex() );;
		date.setMinutes( minuteList.getSelectedIndex() );
		return date;
	}
	
}
