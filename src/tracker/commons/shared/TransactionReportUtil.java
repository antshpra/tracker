package tracker.commons.shared;

@Deprecated
public class TransactionReportUtil {

	public static String getTransactionReportIndex( int year ) {
		return "YEAR_" + year;
	}
	
	public static String getTransactionReportIndex( int year, int month ) {
		if( month > 11 ) {
			year = year + month / 12;
			month = month % 12;
		} else if( month < 0 ) {
			year = year + month / 12 - 1;
			month = month % 12 + 12;
		}
		
		return "MONTH_" + year + ( month > 9 ? "_" : "_0" ) + month;
	}
	
	
	public static int getTransactionReportYear( String index ) {
		if( index.startsWith( "YEAR_" ) )
			return Integer.parseInt( index.substring( 5 ) );
			
		else if ( index.startsWith( "MONTH_" ) )
			return Integer.parseInt( index.substring( 6, 10 ) );

		return -1;
	}

	public static int getTransactionReportMonth( String index ) {
		if ( index.startsWith( "MONTH_" ) )
			return Integer.parseInt( index.substring( 11 ) );

		return -1;
	}

}
