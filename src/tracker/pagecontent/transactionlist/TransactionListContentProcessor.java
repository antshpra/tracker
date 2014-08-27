package tracker.pagecontent.transactionlist;

import com.claymus.module.pagecontent.PageContentProcessor;

public class TransactionListContentProcessor
		extends PageContentProcessor<TransactionListContent> {

	@Override
	protected String getTemplateName() {
		return "tracker/pagecontent/transactionlist/TransactionListContent.ftl";
	}
	
}
