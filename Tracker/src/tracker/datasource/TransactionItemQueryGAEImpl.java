package tracker.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionItemJDO;
import antshpra.gae.datasource.GAEJDODataSource;

public class TransactionItemQueryGAEImpl implements TransactionItemQuery {
	
	private final Query query;
	private final Map<String, Object> paramNameValueMap;
	
	public TransactionItemQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		this.query = gaeJDODataSource.newQuery( TransactionItemJDO.class );
		this.paramNameValueMap = new HashMap<String, Object>();
	}

	@Override
	public void setTransactionId( String transactionId ) {
		query.setFilter( "transactionId == transactionIdParam" );
		query.declareParameters( String.class.getName() + " transactionIdParam" );
		paramNameValueMap.put( "transactionIdParam", transactionId );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItemJDO> execute() {
		return (List<TransactionItemJDO>) query.executeWithMap( this.paramNameValueMap );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItemJDO> execute( int rangeFrom, int rangeTo ) {
		query.setRange( rangeFrom, rangeTo );
		return (List<TransactionItemJDO>) query.executeWithMap( this.paramNameValueMap );
	}
	
	@Override
	public String toString() {
		return query.toString();
	}
	
}
