package antshpra.gwt.rpc.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class Request implements IsSerializable {

	protected static void assertNonNull( Object object ) {
		if( object == null )
			throw new NullPointerException();
	}
	
	protected static void assertLessThanOrEqual( int numberSmall, int numberBig ) {
		if( numberSmall > numberBig )
			throw new IllegalArgumentException();
	}
	
	protected static void assertNonNegative( int number ) {
		if( number < 0 )
			throw new IllegalArgumentException();
	}
	
	protected static void assertNonNegative( long number ) {
		if( number < 0 )
			throw new IllegalArgumentException();
	}
	
	protected static void assertNonZero( int number ) {
		if( number == 0 )
			throw new IllegalArgumentException();
	}

	protected static void assertNonZero( double number ) {
		if( (long) (number * 100) == 0L )
			throw new IllegalArgumentException();
	}

	protected static void assertNonEmpty( String string ) {
		if( string == null || string.trim().isEmpty() )
			throw new IllegalArgumentException();
	}

	protected static void assertStartDateIsNotAfterEndDate( Date startDate, Date endDate ) {
		if( startDate.after( endDate ) )
			throw new IllegalArgumentException();
	}
	
}
