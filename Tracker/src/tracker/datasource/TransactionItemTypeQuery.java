package tracker.datasource;

import java.util.List;

import tracker.datasource.jdo.TransactionItemTypeJDO;

public interface TransactionItemTypeQuery {

	List<TransactionItemTypeJDO> execute();

	String toString();

}
