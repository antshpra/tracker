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
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		addFilter( "transactionItemTypeId", transactionItemTypeId );
	}

	@Override
	public void setReportType( TransactionReportType type ) {
		addFilter( "type", type );
	}

	@Override
	public void orderByLastUpdationDate( boolean cronological ) {
		addOrdering( "lastUpdationDate", cronological );
	}
	
}
