package tracker.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Query;

import tracker.datasource.jdo.TransactionItemTypeJDO;
import antshpra.gae.datasource.GAEJDODataSource;

public class TransactionItemTypeQueryGAEImpl implements TransactionItemTypeQuery {

	private final Query query;
	private final Map<String, Object> paramNameValueMap;

	public TransactionItemTypeQueryGAEImpl( GAEJDODataSource gaeJDODataSource ) {
		this.query = gaeJDODataSource.newQuery( TransactionItemTypeJDO.class );
		this.paramNameValueMap = new HashMap<String, Object>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItemTypeJDO> execute() {
		// TODO: Use following implementation once TransactionItemTypeDB is migrated to DataStore
		// return (List<TransactionItemTypeJDO>) query.executeWithMap( this.paramNameValueMap );
		
		// TODO: Deprecate following implementation once TransactionItemTypeDB is migrated to DataStore
		TransactionItemTypeDB transactionItemTypeDBValues[] = TransactionItemTypeDB.values();
		List<TransactionItemTypeJDO> transactionItemTypeList = new ArrayList<>( transactionItemTypeDBValues.length );
		for( TransactionItemTypeDB transactionItemTypeDBValue : transactionItemTypeDBValues ) {
			transactionItemTypeList.add( transactionItemTypeDBValue.toTransactionItemTypeJDO() );
		}
		return transactionItemTypeList;
	}

	@Override
	public String toString() {
		return query.toString();
	}
	
}
