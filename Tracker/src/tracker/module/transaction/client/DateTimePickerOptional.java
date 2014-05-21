package tracker.module.transaction.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class DateTimePickerOptional extends Composite implements ClickHandler {

	private Anchor anchor = new Anchor();
	private DateTimePicker dateTimePicker = new DateTimePicker();
	
	public DateTimePickerOptional() {
		dateTimePicker.setVisible( false );
		anchor.setText( "Show" );
		anchor.addClickHandler( this );
		
		FlowPanel panel = new FlowPanel();
		panel.add( dateTimePicker );
		panel.add( anchor );
		initWidget( panel );
	}

	@Override
	public void onClick( ClickEvent event ) {
		if( event.getSource() == anchor ) {
			
			if( dateTimePicker.isVisible() ) {
				dateTimePicker.setVisible( false );
				anchor.setText( "Show" );
			} else {
				dateTimePicker.setVisible( true );
				anchor.setText( "Hide" );
			}
			
		}
	}

	public Date getDate() {
		return dateTimePicker.isVisible() ? dateTimePicker.getDate() : null;
	}

}
