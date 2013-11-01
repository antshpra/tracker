package antshpra.gae.datasource.query;

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
	
	protected void addParameter( String param, Object value ) {
		this.paramNameValueMap.put( param, value );
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T execute() {
		return (T) query.executeWithMap( this.paramNameValueMap );
	}

}
