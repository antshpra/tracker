package tracker.module.transaction.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateUtil {

	private static final long MILLISECONDS_IN_A_SECOND = 1000;
	private static final long MILLISECONDS_IN_A_MINUTE = 60 * MILLISECONDS_IN_A_SECOND;
	private static final long MILLISECONDS_IN_A_HOUR   = 60 * MILLISECONDS_IN_A_MINUTE;
	private static final long MILLISECONDS_IN_A_DAY	   = 24 * MILLISECONDS_IN_A_HOUR;
	
	private static final long TZ_IST_MILLISECONDS = (long) (5.5 * 60 * 60 * 1000); 
	
	private static final String DAY_COMING = "Coming ";
	
	private static final String DAY_TOMORROW  = "Tomorrow";
	private static final String DAY_TODAY 	  = "Today";
	private static final String DAY_YESTERDAY = "Yesterday";
	
	private static final String TIME_FEW_MOMENTS_AGO	   = "Few Moments Ago";
	private static final String TIME_FEW_MINUTES_AGO	   = "Few Minutes Ago";
	private static final String TIME_lESS_THAN_AN_HOUR_AGO = "Less Than an Hour Ago";
	private static final String TIME_ABOUT_AN_HOUR_AGO	   = "Abour an Hour Ago";
	private static final String TIME_FEW_HOURS_AGO		   = "Few Hours Ago";
	
	private DateUtil() {}

	
	public static String getDay( Date date ) {
		
		long date_tz_day = ( date.getTime() + TZ_IST_MILLISECONDS ) / MILLISECONDS_IN_A_DAY;
		long today_tz_day = ( new Date().getTime() + TZ_IST_MILLISECONDS ) / MILLISECONDS_IN_A_DAY;
		
		DateTimeFormat dateTimeFormat;
		
		if( date_tz_day - today_tz_day >= 31 )
			return DateTimeFormat.getFormat( "d MMM yyyy" ).format( date );
		
		else if( date_tz_day - today_tz_day >= 5 )
			return DateTimeFormat.getFormat( "d MMMMMM" ).format( date );
		
		else if( date_tz_day - today_tz_day >= 2 )
			return DAY_COMING + DateTimeFormat.getFormat( "EEEE" ).format( date );
		
		else if( date_tz_day - today_tz_day == 1 )
			return DAY_TOMORROW;
			
		else if( date_tz_day - today_tz_day == 0 )
			return DAY_TODAY;
			
		else if( date_tz_day - today_tz_day == -1 )
			return DAY_YESTERDAY;
			
		else if( date_tz_day - today_tz_day >= -4 )
			return DateTimeFormat.getFormat( "EEEE" ).format( date );
			
		else if( date_tz_day - today_tz_day >= -30 )
			return DateTimeFormat.getFormat( "d MMMMMM" ).format( date );
			
		else
			return DateTimeFormat.getFormat( "d MMM yyyy" ).format( date );
		
	}
	
	public static String getTime( Date date ) {
		
		long date_tz_min = date.getTime() / MILLISECONDS_IN_A_MINUTE;
		long now_tz_min = new Date().getTime() / MILLISECONDS_IN_A_MINUTE;
		
		DateTimeFormat dateTimeFormat;
		
		if( date_tz_min - now_tz_min > 0 )
			return DateTimeFormat.getFormat( "h:mm a" ).format( date );
		
		else if( date_tz_min - now_tz_min >= -2 )
			return TIME_FEW_MOMENTS_AGO;
			
		else if( date_tz_min - now_tz_min >= -15 )
			return TIME_FEW_MINUTES_AGO;
		
		else if( date_tz_min - now_tz_min >= -45 )
			return TIME_lESS_THAN_AN_HOUR_AGO;
		
		else if( date_tz_min - now_tz_min >= -75 )
			return TIME_ABOUT_AN_HOUR_AGO;
		
		else if( date_tz_min - now_tz_min >= -240 )
			return TIME_FEW_HOURS_AGO;
				
		else
			return DateTimeFormat.getFormat( "h:mm a" ).format( date );
		
	}
	
}
