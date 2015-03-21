package tracker.datasource;

import java.util.Date;
import java.util.List;

import javax.jdo.Query;

import tracker.data.access.DataAccessor;
import tracker.data.access.gae.TransactionItemEntity;

import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;

@Deprecated
public class TransactionItemQueryGAEImpl implements TransactionItemQuery {
	
	private final GaeQueryBuilder gaeQueryBuilder;
	
	public TransactionItemQueryGAEImpl( DataAccessor gaeJDODataSource ) {
		gaeQueryBuilder = new GaeQueryBuilder( gaeJDODataSource.getPersistenceManager().newQuery( TransactionItemEntity.class ) );
	}

	@Override
	public void setTransactionId( String transactionId ) {
		gaeQueryBuilder.addFilter( "transactionId", transactionId );
	}
	
	@Override
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		gaeQueryBuilder.addFilter( "transactionItemTypeId", transactionItemTypeId );
	}

	@Override
	public void setTransactionItemTypeIdList( List<String> transactionItemTypeIdList ) {
		gaeQueryBuilder.addFilter( "transactionItemTypeId", transactionItemTypeIdList );
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
	public void setLastupdationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive ) {
		if( startDate != null )
			gaeQueryBuilder.addFilter( "lastUpdationDate", startDate, startDateInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endDate != null )
			gaeQueryBuilder.addFilter( "lastUpdationDate", endDate, endDateInclusive ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );
	}
	
	@Override
	public void orderByTransactionDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "transactionDate", cronological );
	}

	@Override
	public void orderByCreationDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "creationDate", cronological );
	}

	@Override
	public void orderByLastupdationDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "lastUpdationDate", cronological );
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItemEntity> execute() {
		Query query = gaeQueryBuilder.build();
		return (List<TransactionItemEntity>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItemEntity> execute( int rangeFrom, int rangeTo ) {
		Query query = gaeQueryBuilder.build();
		query.setRange( rangeFrom, rangeTo );
		return (List<TransactionItemEntity>) query.execute();
	}

}
