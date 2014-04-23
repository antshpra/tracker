package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemJDO;

public interface TransactionItemQuery {

	void setTransactionId( String transactionId );
	
	List<TransactionItemJDO> execute();

	List<TransactionItemJDO> execute( int rangeFrom, int rangeTo );

	String toString();
	
}
