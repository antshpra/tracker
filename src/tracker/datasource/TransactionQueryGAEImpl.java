package tracker.datasource;

import java.util.Date;
import java.util.List;

import javax.jdo.Query;

import tracker.data.access.DataAccessor;
import tracker.data.access.gae.TransactionEntity;

import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;

@Deprecated
public class TransactionQueryGAEImpl implements TransactionQuery {
	
	private final GaeQueryBuilder gaeQueryBuilder;
	
	public TransactionQueryGAEImpl( DataAccessor gaeJDODataSource ) {
		gaeQueryBuilder = new GaeQueryBuilder( gaeJDODataSource.getPersistenceManager().newQuery( TransactionEntity.class ) );
	}

	@Override
	public void setTransactionDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			gaeQueryBuilder.addFilter( "transactionDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endDate != null )
			gaeQueryBuilder.addFilter( "transactionDate", endDate, endDateInclusive ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			gaeQueryBuilder.addFilter( "creationDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );

		if( endDate != null )
			gaeQueryBuilder.addFilter( "creationDate", endDate, endDateInclusive ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void orderByTransactionDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "transactionDate", cronological );
	}

	@Override
	public void orderByCreationDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "creationDate", cronological );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionEntity> execute( int rangeFrom, int rangeTo ) {
		Query query = gaeQueryBuilder.build();
		query.setRange( rangeFrom, rangeTo );
		return (List<TransactionEntity>) query.execute();
	}

}
