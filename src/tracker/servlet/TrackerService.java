package tracker.servlet;

import tracker.pagecontent.transactions.api.TransactionApi;
import tracker.pagecontent.transactions.api.TransactionItemTypeListApi;
import tracker.pagecontent.transactions.api.TransactionListApi;

import com.claymus.api.ApiRegistry;
import com.claymus.servlet.ClaymusService;

@SuppressWarnings("serial")
public class TrackerService extends ClaymusService {
	
	static {
		ApiRegistry.register( TransactionApi.class );
		ApiRegistry.register( TransactionListApi.class );
		ApiRegistry.register( TransactionItemTypeListApi.class );
	}

}
