package tracker.module.pagecontent.transactionlist;

import com.claymus.module.pagecontent.PageContentProcessor;

public class TransactionListContentProcessor
		extends PageContentProcessor<TransactionListContent> {

	@Override
	protected String getTemplateName() {
		return "tracker/module/pagecontent/transactionlist/TransactionListContent.ftl";
	}
	
}
