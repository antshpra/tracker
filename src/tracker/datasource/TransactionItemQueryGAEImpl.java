package tracker.datasource;

import java.util.Date;
import java.util.List;

import tracker.data.access.gae.TransactionItemEntity;
import antshpra.gae.datasource.GAEJDODataSource;
import antshpra.gae.datasource.GAEJDOQuery;

public class TransactionItemQueryGAEImpl extends GAEJDOQuery<TransactionItemEntity> implements TransactionItemQuery {
	
	public TransactionItemQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		super( TransactionItemEntity.class, gaeJDODataSource );
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
	public void setTransactionItemTypeIdList( List<String> transactionItemTypeIdList ) {
		addFilter( "transactionItemTypeId", transactionItemTypeIdList );
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
	public void setLastupdationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			addFilter( "lastUpdationDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endDate != null )
			addFilter( "lastUpdationDate", endDate, endDateInclusive ? Operator.LESST_THAN_OR_EQUAL : Operator.LESS_THAN );
	}
	
	@Override
	public void orderByTransactionDate( boolean cronological ) {
		addOrdering( "transactionDate", cronological );
	}

	@Override
	public void orderByCreationDate( boolean cronological ) {
		addOrdering( "creationDate", cronological );
	}

	@Override
	public void orderByLastupdationDate( boolean cronological ) {
		addOrdering( "lastUpdationDate", cronological );
	}

}
