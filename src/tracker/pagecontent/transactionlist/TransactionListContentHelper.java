package tracker.pagecontent.transactionlist;

import tracker.pagecontent.transactionlist.gae.TransactionListContentEntity;
import tracker.pagecontent.transactionlist.shared.TransactionListContentData;

import com.claymus.pagecontent.PageContentHelper;

public class TransactionListContentHelper extends PageContentHelper<
		TransactionListContent,
		TransactionListContentData,
		TransactionListContentProcessor> {

	@Override
	public String getModuleName() {
		return "Transaction List";
	}

	@Override
	public Double getModuleVersion() {
		return 1.0;
	}

	
	public static TransactionListContent newTransactionListContent() {
		return new TransactionListContentEntity();
	}

}
