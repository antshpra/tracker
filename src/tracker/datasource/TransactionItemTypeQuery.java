package tracker.datasource;

import java.util.List;

import tracker.data.access.gae.TransactionItemTypeEntity;

public interface TransactionItemTypeQuery {

	List<TransactionItemTypeEntity> execute();

	String toString();

}
