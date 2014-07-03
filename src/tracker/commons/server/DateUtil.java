package tracker.commons.server;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static Date getDateFor( int year, int month, int day ) {
		Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "IST" ) );
		calendar.setTimeInMillis( 0 );
		calendar.set( year, month, day, 0, 0, 0 );
		return calendar.getTime();
	}
	
	public static int getYear( Date date ) {
		Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "IST" ) );
		calendar.setTime( date );
		return calendar.get( Calendar.YEAR );
	}
	
	public static int getMonth( Date date ) {
		Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "IST" ) );
		calendar.setTime( date );
		return calendar.get( Calendar.MONTH );
	}
	
}
