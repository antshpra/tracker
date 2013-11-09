package tracker.module.transaction.client;

import java.util.Calendar;
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
	private ToggleButton amPmButton = new ToggleButton( "AM", "PM" ); // TODO: I18n
	
	public DateTimePicker() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		
		dateLabel.addClickHandler( this );
		datePicker.addValueChangeHandler( this );
		datePicker.setValue( date, true );
		
		for( int i=0; i<=11; i++ )
			hourList.addItem( Integer.toString( i ), Integer.toString( i ) );
		hourList.setSelectedIndex( calendar.get( Calendar.HOUR ) );

		for( int i=0; i<=59; i++ )
			minuteList.addItem( Integer.toString( i ), Integer.toString( i ) );
		minuteList.setSelectedIndex( calendar.get( Calendar.MINUTE ) );

		amPmButton.setValue( calendar.get( Calendar.AM_PM ) == Calendar.PM );

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
			Calendar calendar = Calendar.getInstance();
			calendar.setTime( event.getValue() );
			dateLabel.setText( calendar.get( Calendar.DAY_OF_MONTH ) + "/" + ( calendar.get( Calendar.MONTH ) + 1 ) + "/" + calendar.get( Calendar.YEAR ) ); // TODO: I18n
			datePicker.setVisible( false );
		}
	}
	
	public Date getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( datePicker.getValue() );
		calendar.set( Calendar.HOUR, hourList.getSelectedIndex() );
		calendar.set( Calendar.MINUTE, minuteList.getSelectedIndex() );
		calendar.set( Calendar.AM_PM, amPmButton.isDown() ? Calendar.PM : Calendar.AM );
		return calendar.getTime();
	}
	
}
