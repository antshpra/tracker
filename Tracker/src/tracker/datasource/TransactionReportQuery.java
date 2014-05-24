package tracker.datasource;

import java.util.List;

import tracker.commons.shared.TransactionReportType;
import tracker.datasource.jdo.TransactionReportJDO;

public interface TransactionReportQuery {

	void setIndex( String index );
	
	void setTransactionItemTypeId( String transactionItemTypeId );

	void setReportType( TransactionReportType type );

	void orderByLastUpdationDate( boolean cronological );
		
	List<TransactionReportJDO> execute( int rangeFrom, int rangeTo );
	
}
