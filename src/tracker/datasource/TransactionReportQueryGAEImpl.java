package tracker.datasource;

import tracker.commons.shared.TransactionReportType;
import tracker.datasource.jdo.TransactionReportJDO;
import antshpra.gae.datasource.GAEJDODataSource;
import antshpra.gae.datasource.GAEJDOQuery;

public class TransactionReportQueryGAEImpl extends GAEJDOQuery<TransactionReportJDO> implements TransactionReportQuery {
	
	public TransactionReportQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		super( TransactionReportJDO.class, gaeJDODataSource );
	}

	@Override
	public void setIndex( String index ) {
		addFilter( "index", index );
	}

	@Override
	public void setIndexRange( String startIndex, boolean startIndexInclusive, String endIndex, boolean endIndexInclusive ) {
		if( startIndex != null )
			addFilter( "index", startIndex, startIndexInclusive ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( endIndex != null )
			addFilter( "index", endIndex, endIndexInclusive ? Operator.LESST_THAN_OR_EQUAL : Operator.LESS_THAN );
	}

	@Override
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		addFilter( "transactionItemTypeId", transactionItemTypeId );
	}

	@Override
	public void setReportType( TransactionReportType type ) {
		addFilter( "type", type );
	}

	@Override
	public void orderByIndex( boolean ascending ) {
		addOrdering( "index", ascending );
	}
	
	@Override
	public void orderByLastUpdationDate( boolean cronological ) {
		addOrdering( "lastUpdationDate", cronological );
	}
	
}
