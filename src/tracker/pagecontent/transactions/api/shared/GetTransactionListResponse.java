package tracker.pagecontent.transactions.api.shared;

import java.util.List;

import tracker.data.transfer.shared.TransactionData;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetTransactionListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<TransactionData> transactionList;
	
	@SuppressWarnings("unused")
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetTransactionListResponse() {}
	
	public GetTransactionListResponse( List<TransactionData> transactionList, String cursor ) {
		this.transactionList = transactionList;
		this.cursor = cursor;
	}
	
}
