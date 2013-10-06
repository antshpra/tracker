package antshpra.gwt.rpc.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Request implements Serializable {

	protected void assertNonNull( Object object ) {
		if( object == null )
			throw new NullPointerException();
	}
	
	protected void assertNonNegative( long number ) {
		if( number < 0 )
			throw new IllegalArgumentException();
	}
	
	protected void assertNonEmpty( String string ) {
		if( string == null || string.trim().isEmpty() )
			throw new IllegalArgumentException();
	}

}
