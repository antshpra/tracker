package tracker.datasource;

import java.util.List;

import tracker.data.access.gae.TransactionItemTypeEntity;

@Deprecated
public interface TransactionItemTypeQuery {

	List<TransactionItemTypeEntity> execute();

	String toString();

}
