package tracker.datasource;

import java.util.ArrayList;
import java.util.List;

import tracker.datasource.jdo.TransactionItemTypeJDO;
import antshpra.gae.datasource.GAEJDODataSource;
import antshpra.gae.datasource.GAEJDOQuery;

public class TransactionItemTypeQueryGAEImpl extends GAEJDOQuery<TransactionItemTypeJDO> implements TransactionItemTypeQuery {

	public TransactionItemTypeQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		super( TransactionItemTypeJDO.class, gaeJDODataSource );
	}
	
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
