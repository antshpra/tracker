package tracker.datasource;

import java.util.ArrayList;
import java.util.List;

import tracker.data.access.DataAccessor;
import tracker.datasource.jdo.TransactionItemTypeJDO;

public class TransactionItemTypeQueryGAEImpl implements TransactionItemTypeQuery {

	public TransactionItemTypeQueryGAEImpl( DataAccessor gaeJDODataSource ) {}
	
	// TODO: Deprecate this implementation once TransactionItemTypeDB is migrated to DataStore
	@Override
	public List<TransactionItemTypeJDO> execute() {
		TransactionItemTypeDB transactionItemTypeDBValues[] = TransactionItemTypeDB.values();
		List<TransactionItemTypeJDO> transactionItemTypeJDOList = new ArrayList<>( transactionItemTypeDBValues.length );
		for( TransactionItemTypeDB transactionItemTypeDBValue : transactionItemTypeDBValues ) {
			transactionItemTypeJDOList.add( transactionItemTypeDBValue.toTransactionItemTypeJDO() );
		}
		return transactionItemTypeJDOList;
	}

}
