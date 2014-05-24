package tracker.datasource;

import java.util.Date;

import tracker.datasource.jdo.TransactionItemJDO;
import antshpra.gae.datasource.GAEJDODataSource;
import antshpra.gae.datasource.GAEJDOQuery;

public class TransactionItemQueryGAEImpl extends GAEJDOQuery<TransactionItemJDO> implements TransactionItemQuery {
	
	public TransactionItemQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		super( TransactionItemJDO.class, gaeJDODataSource );
	}

	@Override
	public void setTransactionId( String transactionId ) {
		addFilter( "transactionId", transactionId );
	}
	
	@Override
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		addFilter( "transactionItemTypeId", transactionItemTypeId );
	}

	@Override
	public void setTransactionDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			addFilter( "transactionDate", startDate, startDateInclusive ? ">=" : ">" );
		
		if( endDate != null )
			addFilter( "transactionDate", endDate, endDateInclusive ? "<=" : "<" );
	}

	@Override
	public void orderByTransactionDate( boolean cronological ) {
		addOrdering( "transactionDate", cronological );
	}

	@Override
	public void setLastupdationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			addFilter( "lastUpdationDate", startDate, startDateInclusive ? ">=" : ">" );
		
		if( endDate != null )
			addFilter( "lastUpdationDate", endDate, endDateInclusive ? "<=" : "<" );
	}
	
}
