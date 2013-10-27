package antshpra.gwt.rpc.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InvalidRequestException extends RuntimeException implements IsSerializable {

	private static final long serialVersionUID = -6268836143146369405L;

	public InvalidRequestException( String message ) {
		super( message );
	}

}
