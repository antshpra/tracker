package tracker.pagecontent.transactions.api;

import tracker.commons.shared.TransactionFilter;
import tracker.data.transfer.shared.TransactionData;
import tracker.pagecontent.transactions.TransactionsContentHelper;
import tracker.pagecontent.transactions.api.shared.GetTransactionListRequest;
import tracker.pagecontent.transactions.api.shared.GetTransactionListResponse;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;

@SuppressWarnings("serial")
@Bind( uri = "/transaction/list" )
public class TransactionListApi extends GenericApi {

	@Get
	public GetTransactionListResponse getTransactionList( GetTransactionListRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		TransactionFilter trFilter = new TransactionFilter();
		trFilter.setTransactionDateChronologicalOrder( request.getTrDateOrder() );
		trFilter.setCreationDateChronologicalOrder( request.getCreationDateOrder() );
		
		DataListCursorTuple<TransactionData> dataListCursorTuple
				= TransactionsContentHelper.getTransactionList(
						trFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount(),
						this.getThreadLocalRequest() );

		return new GetTransactionListResponse(
				dataListCursorTuple.getDataList(),
				dataListCursorTuple.getCursor() );

	}

}
