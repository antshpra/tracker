package tracker.datasource;

import java.util.ArrayList;
import java.util.List;

import tracker.data.access.DataAccessor;
import tracker.data.access.gae.TransactionItemTypeEntity;

public class TransactionItemTypeQueryGAEImpl implements TransactionItemTypeQuery {

	public TransactionItemTypeQueryGAEImpl( DataAccessor gaeJDODataSource ) {}
	
	// TODO: Deprecate this implementation once TransactionItemTypeDB is migrated to DataStore
	@Override
	public List<TransactionItemTypeEntity> execute() {
		TransactionItemTypeDB transactionItemTypeDBValues[] = TransactionItemTypeDB.values();
		List<TransactionItemTypeEntity> transactionItemTypeJDOList = new ArrayList<>( transactionItemTypeDBValues.length );
		for( TransactionItemTypeDB transactionItemTypeDBValue : transactionItemTypeDBValues ) {
			transactionItemTypeJDOList.add( transactionItemTypeDBValue.toTransactionItemTypeJDO() );
		}
		return transactionItemTypeJDOList;
	}

}
