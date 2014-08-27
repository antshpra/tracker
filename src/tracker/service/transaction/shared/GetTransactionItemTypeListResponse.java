package tracker.service.transaction.shared;

import java.util.List;

import tracker.service.transaction.shared.data.TransactionItemTypeData;
import antshpra.gwt.rpc.shared.Response;

public class GetTransactionItemTypeListResponse extends Response {

	private List<TransactionItemTypeData> transactionItemTypeDataList;

	
	public List<TransactionItemTypeData> getTransactionItemTypeDataList() {
		return this.transactionItemTypeDataList;
	}

	public void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		this.transactionItemTypeDataList = transactionItemTypeDataList;
	}
	
}