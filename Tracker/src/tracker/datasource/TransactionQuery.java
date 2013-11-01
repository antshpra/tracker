package tracker.datasource;

import java.util.Date;
import java.util.List;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.JDOQuery;

import com.google.appengine.api.datastore.Key;

public class TransactionQuery extends JDOQuery {
	
	private Query query;
	private TransactionDataSource transactionDataSource;
	
	public TransactionQuery( Query query, TransactionDataSource transactionDataSource ) {
		super( query );
		this.query = query;
		this.transactionDataSource = transactionDataSource;
	}

	public void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null ) {
			query.setFilter( "creationDate " + ( startDateInclusive ? ">=" : ">" ) + " creationDate_Start" );
			query.declareParameters( Date.class.getName() + " creationDate_Start" );
			super.addParameter( "creationDate_Start", startDate );
		}
		
		if( endDate != null ) {
			query.setFilter( "creationDate " + ( endDateInclusive ? "<=" : "<" ) + " creationDate_End" );
			query.declareParameters( Date.class.getName() + " creationDate_End" );
			super.addParameter( "creationDate_End", endDate );
		}
	}

	public void orderByCreationDate( boolean cronological ) {
		if( cronological )
			query.setOrdering( "creationDate" );
		else
			query.setOrdering( "creationDate DESC" );
	}

	public List<TransactionJDO> execute( int rangeFrom, int rangeTo, boolean loadTransactionItems ) {
		query.setResult( "transactionId" );
		query.setRange( rangeFrom, rangeTo );
		List<Key> keyList = super.execute();
		return transactionDataSource.getTransactionList( keyList, loadTransactionItems );
	}
	
	@Override
	public String toString() {
		return query.toString();
	}
	
}
