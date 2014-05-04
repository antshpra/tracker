package tracker.datasource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.GAEJDODataSource;

public class TransactionQueryGAEImpl implements TransactionQuery {
	
	private final Query query;
	private final Map<String, Object> paramNameValueMap;
	
	public TransactionQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		this.query = gaeJDODataSource.newQuery( TransactionJDO.class );
		this.paramNameValueMap = new HashMap<String, Object>();
	}

	@Override
	public void setTransactionDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null ) {
			query.setFilter( "transactionDate " + ( startDateInclusive ? ">=" : ">" ) + " transactionDate_Start" );
			query.declareParameters( Date.class.getName() + " transactionDate_Start" );
			paramNameValueMap.put( "transactionDate_Start", startDate );
		}
		
		if( endDate != null ) {
			query.setFilter( "transactionDate " + ( endDateInclusive ? "<=" : "<" ) + " transactionDate_End" );
			query.declareParameters( Date.class.getName() + " transactionDate_End" );
			paramNameValueMap.put( "transactionDate_End", endDate );
		}
	}

	@Override
	public void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null ) {
			query.setFilter( "creationDate " + ( startDateInclusive ? ">=" : ">" ) + " creationDate_Start" );
			query.declareParameters( Date.class.getName() + " creationDate_Start" );
			paramNameValueMap.put( "creationDate_Start", startDate );
		}
		
		if( endDate != null ) {
			query.setFilter( "creationDate " + ( endDateInclusive ? "<=" : "<" ) + " creationDate_End" );
			query.declareParameters( Date.class.getName() + " creationDate_End" );
			paramNameValueMap.put( "creationDate_End", endDate );
		}
	}

	@Override
	public void orderByTransactionDate( boolean cronological ) {
		if( cronological )
			query.setOrdering( "transactionDate" );
		else
			query.setOrdering( "transactionDate DESC" );
	}

	@Override
	public void orderByCreationDate( boolean cronological ) {
		if( cronological )
			query.setOrdering( "creationDate" );
		else
			query.setOrdering( "creationDate DESC" );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionJDO> execute( int rangeFrom, int rangeTo ) {
		query.setRange( rangeFrom, rangeTo );
		return (List<TransactionJDO>) query.executeWithMap( this.paramNameValueMap );
	}
	
	@Override
	public String toString() {
		return query.toString();
	}
	
}
