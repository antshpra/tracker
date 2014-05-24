package antshpra.gae.datasource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.Query;

public class GAEJDOQuery <T> {

	private static final Logger logger = Logger.getLogger( GAEJDOQuery.class.getName() );

	private final Query query;
	private final List<String> filters;
	private final List<String> parameteres;
	private final List<String> orderings;
	private final Map<String, Object> paramNameValueMap;
	
	public GAEJDOQuery( Class<T> clazz, GAEJDODataSource gaeJDODataSource ) {
		this.query = gaeJDODataSource.newQuery( clazz );
		this.filters = new LinkedList<>();
		this.parameteres = new LinkedList<>();
		this.orderings = new LinkedList<>();
		this.paramNameValueMap = new HashMap<>();
	}

	public void addFilter( String param, Object value) {
		addFilter( param, value, "==" );
	}
	
	public void addFilter( String param, Object value, String operator ) {
		String paramKey = param + "Param";
		for( int i = 0; ; i++ ) {
			if( paramNameValueMap.get( paramKey ) == null )
				break;
			paramKey = param + "Param_" + i;
		}
		
		filters.add( param + " " + operator + " " + paramKey );
		parameteres.add( value.getClass().getName() + " " + paramKey );
		paramNameValueMap.put( paramKey, value );
	}

	public void addOrdering( String param, boolean asc ) {
		orderings.add( asc ? param : param + " DESC" );
	}

	@SuppressWarnings("unchecked")
	public List<T> execute() {
		if( filters.size() != 0 ) {
			String filterStr = filters.get( 0 );
			for( int i = 1; i < filters.size(); i++ )
				filterStr = filterStr + " && " + filters.get( i );
			query.setFilter( filterStr );
		}
		
		if( parameteres.size() != 0 ) {
			String parameterStr = parameteres.get( 0 );
			for( int i = 1; i < parameteres.size(); i++ )
				parameterStr = parameterStr + ", " + parameteres.get( i );
			query.declareParameters( parameterStr );
		}

		if( orderings.size() != 0 ) {
			String orderingStr = orderings.get( 0 );
			for( int i = 1; i < orderings.size(); i++ )
				orderingStr = orderingStr + ", " + orderings.get( i );
			query.setOrdering( orderingStr );
		}

		logger.info( "Executing query - \n\"" + query.toString() + "\"" );
		
		return (List<T>) query.executeWithMap( paramNameValueMap );
	}
	
	public List<T> execute( int rangeFrom, int rangeTo ) {
		query.setRange( rangeFrom, rangeTo );
		return execute();
	}

}
