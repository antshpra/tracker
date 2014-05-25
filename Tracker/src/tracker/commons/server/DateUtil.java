package tracker.commons.server;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getDateFor( int year, int month, int day ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set( year, month, day );
		return calendar.getTime();
	}
	
	public static int getYear( Date date ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		return calendar.get( Calendar.YEAR );
	}
	
}
