package tracker.pagecontent.transactions.api;

import java.util.List;

import tracker.data.transfer.shared.TransactionItemTypeData;
import tracker.pagecontent.transactions.TransactionsContentHelper;
import tracker.pagecontent.transactions.api.shared.GetTransactionItemTypeListResponse;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
@Bind( uri = "/transaction/item/type/list" )
public class TransactionItemTypeListApi extends GenericApi {

	@Get
	public GetTransactionItemTypeListResponse getTransactionList( GenericRequest request ) {
		List<TransactionItemTypeData> triTypeList =
				TransactionsContentHelper.getTransactionItemTypeList( this.getThreadLocalRequest() );
		
		return new GetTransactionItemTypeListResponse( triTypeList );
	}

}
