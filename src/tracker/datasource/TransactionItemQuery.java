package tracker.datasource;

import java.util.Date;
import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;

public interface TransactionItemQuery {

	void setTransactionId( String transactionId );
	
	void setTransactionItemTypeId( String transactionItemTypeId );
	
	void setTransactionItemTypeIdList( List<String> transactionItemTypeIdList );
	
	void setTransactionDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive );

	void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive );

	void setLastupdationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive );

	void orderByTransactionDate( boolean cronological );

	void orderByCreationDate( boolean cronological );

	void orderByLastupdationDate( boolean cronological );

	List<TransactionItemJDO> execute();

	List<TransactionItemJDO> execute( int rangeFrom, int rangeTo );

	String toString();
	
}
