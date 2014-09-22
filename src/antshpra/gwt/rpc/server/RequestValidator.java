package antshpra.gwt.rpc.server;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

import com.claymus.commons.client.UnexpectedServerException;

public class RequestValidator {

	private static Logger logger = Logger.getLogger( RequestValidator.class.getName() );
	
	private RequestValidator() {}
	
	public static void validate( Request request ) throws InvalidRequestException, UnexpectedServerException {
		Field[] fields = request.getClass().getDeclaredFields();
		
		for( Field field : fields ) {
			if( field.getAnnotation( RequiredField.class ) != null ) {
				field.setAccessible( true );
				try {
					if( field.get( request ) == null ) {
						logger.log( Level.SEVERE, "Required field '" + field.getName() + "' is null." );
						throw new InvalidRequestException( "Required field '" + field.getName() + "' can not be null !" );
					}
				} catch( IllegalArgumentException e ) {
					logger.log( Level.SEVERE, "Unexpected exception occured !", e );
					throw new UnexpectedServerException();
				} catch( IllegalAccessException e ) {
					logger.log( Level.SEVERE, "Unexpected exception occured !", e );
					throw new UnexpectedServerException();
				}
			}
		}
		
	}
	
}
