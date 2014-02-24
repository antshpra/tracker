package antshpra.gae.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.Query;

public class JDOQuery {

	private Query query;
	private final Map<String, Object> paramNameValueMap;

	protected JDOQuery( Query query ) {
		this.query = query;
		this.paramNameValueMap = new HashMap<String, Object>();
	}
	
	public void addParameter( String param, Object value ) {
		this.paramNameValueMap.put( param, value );
	}
	
	@SuppressWarnings("unchecked")
	public <T> T execute() {
		return (T) query.executeWithMap( this.paramNameValueMap );
	}

}
