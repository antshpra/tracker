package antshpra.gwt.rpc.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ServerException extends RuntimeException implements IsSerializable {
	
	public ServerException() {
		super( "Unexpected error occured at server !" );
	}
	
}
