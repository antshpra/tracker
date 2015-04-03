package tracker.pagecontent.transactions.api.shared;

import java.util.List;

import tracker.data.transfer.shared.TransactionItemTypeData;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetTransactionItemTypeListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<TransactionItemTypeData> transactionItemTypeList;
	

	@SuppressWarnings("unused")
	private GetTransactionItemTypeListResponse() {}
	
	public GetTransactionItemTypeListResponse( List<TransactionItemTypeData> transactionItemTypeList ) {
		this.transactionItemTypeList = transactionItemTypeList;
	}
	
}
