package tracker.pagecontent.transactionlist;

import tracker.pagecontent.transactionlist.gae.TransactionListContentEntity;

import com.claymus.module.pagecontent.PageContentFactory;

public class TransactionListContentFactory
		implements PageContentFactory<TransactionListContent, TransactionListContentProcessor> {

	public static TransactionListContent newTransactionListContent() {

		return new TransactionListContentEntity();

	}

}
