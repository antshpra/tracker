package tracker.datasource;

import java.util.List;

import tracker.commons.shared.TransactionReportType;
import tracker.datasource.jdo.TransactionReportJDO;

public interface TransactionReportQuery {

	void setIndex( String index );
	
	void setIndexRange( String startIndex, boolean startIndexInclusive, String endIndex, boolean endIndexInclusive );

	void setTransactionItemTypeId( String transactionItemTypeId );

	void setReportType( TransactionReportType type );

	void orderByIndex( boolean ascending );

	void orderByLastUpdationDate( boolean cronological );
		
	List<TransactionReportJDO> execute();

	List<TransactionReportJDO> execute( int rangeFrom, int rangeTo );
	
}
