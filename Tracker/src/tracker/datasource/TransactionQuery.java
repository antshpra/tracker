package tracker.datasource;

import java.util.Date;
import java.util.List;

import tracker.datasource.jdo.TransactionJDO;

public interface TransactionQuery {

	void setCreationDate( Date startDate, boolean startDateInclusive, Date endDate, boolean endDateInclusive );
	
	void orderByCreationDate( boolean cronological );
		
	List<TransactionJDO> execute( int rangeFrom, int rangeTo );

	String toString();
	
}
