package tracker.pagecontent.transactions.api;

import java.util.ArrayList;
import java.util.List;

import tracker.data.transfer.shared.TransactionData;
import tracker.data.transfer.shared.TransactionItemData;
import tracker.pagecontent.transactions.TransactionsContentHelper;
import tracker.pagecontent.transactions.api.shared.PutTransactionItemRequest;
import tracker.pagecontent.transactions.api.shared.PutTransactionRequest;
import tracker.pagecontent.transactions.api.shared.PutTransactionResponse;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;

@SuppressWarnings("serial")
@Bind( uri = "/transaction" )
public class TransactionApi extends GenericApi {

	@Put
	public PutTransactionResponse putTransaction( PutTransactionRequest request ) {
		TransactionData trData = new TransactionData();
		trData.setId( request.getId() );
		trData.setTransactionDate( request.getTransactionDate() );
		trData.setDescription( request.getDescription() );
		List<PutTransactionItemRequest> putTransactionItemRequestList = request.getPutTransactionItemRequestList();
		List<TransactionItemData> triDataList = new ArrayList<>( putTransactionItemRequestList.size() );
		for( PutTransactionItemRequest req : putTransactionItemRequestList ) {
			TransactionItemData triData = new TransactionItemData();
			triData.setId( req.getId() );
			triData.setTransactionItemTypeId( req.getTransactionItemTypeId() );
			triData.setTransactionDate( req.getTransactionDate() );
			triData.setAmount( req.getAmount() );
			triData.setNote( req.getNote() );
			triData.setOrder( req.getOrder() );
			triDataList.add( triData );
		}
		trData.setTransactionItemList( triDataList );
		
		trData = TransactionsContentHelper.saveTransaction( trData, this.getThreadLocalRequest() );

		return new PutTransactionResponse( trData );
	}

}
