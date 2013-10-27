package antshpra.gwt.rpc.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServerException extends RuntimeException implements IsSerializable {
	
	private static final long serialVersionUID = -5404975321967336055L;

	public ServerException() {
		super( "Unexpected error occured at server !" );
	}
	
}
