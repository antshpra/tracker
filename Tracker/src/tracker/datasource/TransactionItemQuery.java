package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;

public interface TransactionItemQuery {

	void setTransactionId( String transactionId );
	
	void setTransactionItemTypeId( String transactionItemTypeId );
	
	void orderByTransactionDate( boolean cronological );

	List<TransactionItemJDO> execute();

	List<TransactionItemJDO> execute( int rangeFrom, int rangeTo );

	String toString();
	
}
