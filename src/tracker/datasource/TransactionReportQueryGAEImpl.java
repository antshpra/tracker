package tracker.datasource;

import java.util.List;

import javax.jdo.Query;

import tracker.commons.shared.TransactionReportType;
import tracker.data.access.DataAccessor;
import tracker.data.access.gae.TransactionReportEntity;

import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;

@Deprecated
public class TransactionReportQueryGAEImpl implements TransactionReportQuery {
	
	private final GaeQueryBuilder gaeQueryBuilder;
	
	public TransactionReportQueryGAEImpl( DataAccessor gaeJDODataSource ) {
		gaeQueryBuilder = new GaeQueryBuilder( gaeJDODataSource.getPersistenceManager().newQuery( TransactionReportEntity.class ) );
	}

	@Override
	public void setIndex( String index ) {
		gaeQueryBuilder.addFilter( "index", index );
	}

	@Override
	public void setIndexRange( String startIndex, boolean startIndexInclusive, String endIndex, boolean endIndexInclusive ) {
		if( startIndex != null )
			gaeQueryBuilder.addFilter( "index", startIndex, startIndexInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endIndex != null )
			gaeQueryBuilder.addFilter( "index", endIndex, endIndexInclusive ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		gaeQueryBuilder.addFilter( "transactionItemTypeId", transactionItemTypeId );
	}

	@Override
	public void setReportType( TransactionReportType type ) {
		gaeQueryBuilder.addFilter( "type", type );
	}

	@Override
	public void orderByIndex( boolean ascending ) {
		gaeQueryBuilder.addOrdering( "index", ascending );
	}
	
	@Override
	public void orderByLastUpdationDate( boolean cronological ) {
		gaeQueryBuilder.addOrdering( "lastUpdationDate", cronological );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionReportEntity> execute() {
		Query query = gaeQueryBuilder.build();
		return (List<TransactionReportEntity>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionReportEntity> execute( int rangeFrom, int rangeTo ) {
		Query query = gaeQueryBuilder.build();
		query.setRange( rangeFrom, rangeTo );
		return (List<TransactionReportEntity>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
	}

}
