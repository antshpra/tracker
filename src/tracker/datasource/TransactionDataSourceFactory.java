package tracker.datasource;

public class TransactionDataSourceFactory {

	public TransactionDataSource getTransactionDataSource() {
		return new TransactionDataSourceGAEImpl();
	}
	
}
