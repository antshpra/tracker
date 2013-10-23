package antshpra.gwt.rpc.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException implements IsSerializable {

	public InvalidRequestException( String message ) {
		super( message );
	}

}
