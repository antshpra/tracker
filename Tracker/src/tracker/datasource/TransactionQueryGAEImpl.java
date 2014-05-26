package tracker.datasource;

import java.util.Date;

import tracker.datasource.jdo.TransactionJDO;
import antshpra.gae.datasource.GAEJDODataSource;
import antshpra.gae.datasource.GAEJDOQuery;

public class TransactionQueryGAEImpl extends GAEJDOQuery<TransactionJDO> implements TransactionQuery {
	
	public TransactionQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		super( TransactionJDO.class, gaeJDODataSource );
	}

	@Override
	public void setTransactionDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			addFilter( "transactionDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endDate != null )
			addFilter( "transactionDate", endDate, endDateInclusive ? Operator.LESST_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			addFilter( "creationDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );

		if( endDate != null )
			addFilter( "creationDate", endDate, endDateInclusive ? Operator.LESST_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void orderByTransactionDate( boolean cronological ) {
		addOrdering( "transactionDate", cronological );
	}

	@Override
	public void orderByCreationDate( boolean cronological ) {
		addOrdering( "creationDate", cronological );
	}

}
